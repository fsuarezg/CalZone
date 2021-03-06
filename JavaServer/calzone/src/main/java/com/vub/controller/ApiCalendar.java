package com.vub.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vub.exception.RoomNotFoundException;
import com.vub.exception.UserNotFoundException;
import com.vub.model.CalendarMove;
import com.vub.model.Course;
import com.vub.model.CourseComponent;
import com.vub.model.Entry;
import com.vub.model.JsonResponse;
import com.vub.model.Notification;
import com.vub.model.NotificationType;
import com.vub.model.Room;
import com.vub.model.Traject;
import com.vub.model.User;
import com.vub.model.UserRole;
import com.vub.service.EntryService;
import com.vub.service.NotificationService;
import com.vub.service.RoomService;
import com.vub.service.TrajectService;
import com.vub.service.UserService;
import com.vub.utility.Views;



@Controller
@RequestMapping("api/calendar")
public class ApiCalendar {


	@Autowired
	EntryService entryService;

	@Autowired
	TrajectService trajectService;

	@Autowired
	UserService userService;
	
	@Autowired
	RoomService roomService;
	
	@Autowired
	NotificationService notificationService;

	public final static int CAL_PERSON = 0;
	public final static int CAL_ROOM = 1;
	public final static int CAL_TRAJECT = 2;

	/**
	 * This function is used by the calendar to serve Json to be displayed. Only possible to fetch data for each week
	 * 
	 * @param type: Indicating the type of the request to the Api. Options are course,user,room
	 * @param id: id in the database of the type. Example: user with id 33
	 * @param week: indication witch week of the calendar year to server
	 * @return returns a list of {@link #<Entry>} back in Json format
	 * @throws ParseException 
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "{type}/{id}/{week}", method = RequestMethod.GET)
	@ResponseBody
	public String test(@PathVariable String type, @PathVariable int id, @PathVariable int week, Principal principal) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

		ArrayList<Entry> list = new ArrayList<Entry>();

		try {
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.getSerializationConfig().setSerializationView(Views.EntryFilter.class);
			objectMapper.configure(SerializationConfig.Feature.DEFAULT_VIEW_INCLUSION, false);

			if (type.equals("student")) {
				if (id == 0 ) {
					User user = userService.findUserByUsername(principal.getName());
					list.addAll(userService.getAllEntries(user));
				} else {
					User user = userService.findUserById(id);
					list.addAll(userService.getAllEntries(user));
				}
				
				
			} else if (type.equals("room")) {
				//TODO filter on week
				Room room  = roomService.findRoomById(id);
				System.out.println("All Room Entrys: " + roomService.findRoomById(id));
				list.addAll(room.getEntries());
			} else if (type.equals("traject")) {
				System.out.println("Traject Loading Entry's: " + id);
				Traject trajact = trajectService.findTrajectById(id); //Test 177
				System.out.println(trajact.toString());
				for (Course c: trajact.getCourses()) {
					for(CourseComponent cc: c.getCourseComponents()) {
						for (Entry e: cc.getEntries()) {
							list.add(e);
						}
					}
				}
			} else {
				return null;
			}

			String json =  objectMapper.writeValueAsString(list);
			System.out.println(json);
			
			return json;
		
		
		} catch ( IOException e ) {
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		} catch (RoomNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "move/time", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse testPost(@RequestBody CalendarMove calendarMove, Principal principal) {
		JsonResponse jsonResponse = new JsonResponse();
		
		try {
			Entry entry = entryService.findEntryById(calendarMove.getEntryId());
			User user = userService.findUserByNameInitializedEntrys(principal.getName());
			Date oldDate = entry.getStartingDate();
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			boolean movePrivilage;
			for (User u: entry.getCourseComponent().getTeachers()){
				if (u.getId() == user.getId() || user.getUserRole().getUserRole() == UserRole.UserRoleEnum.ROLE_ADMIN || true == true) { //TODO remove true == true
					Date date = entry.getStartingDate();
					System.out.println("Old entry: " + entry);
					entry.setStartingDate(calendarMove.getNewStartDate());
					entryService.updateEntry(entry);
					System.out.println("New entry: " + entry);
					//TODO generate notifactions for all users
					for (User student: entry.getCourseComponent().getCourse().getEnrolledStudents()) {
						//All Users that follow the coursecomponent that is being edited
						Notification notification = new Notification();
						notification.setUser(student);
						notification.setType(NotificationType.Time);
						notification.setDate(Calendar.getInstance().getTime());
						String[] array = {entry.getCourseComponent().getCourse().getCourseName(), df.format(oldDate), df.format(calendarMove.getNewStartDate())};
						notification.setMessage(array);
						notificationService.updateNotification(notification);
					}
				}
			}
		} catch (UserNotFoundException e) {
			jsonResponse.setStatus(JsonResponse.ERROR);
			jsonResponse.setMessage("User not found");
			e.printStackTrace();
		}

		return jsonResponse;
	}

	/**
	 * Will be deleted. No function for POST request
	 * @param room
	 * @return
	 */
	@RequestMapping(value = "{type}/{id}/{week}", method = RequestMethod.POST)
	@ResponseBody
	public Room testPost(@RequestBody Room room) {
		System.out.println(room);
		return room;
	}
}