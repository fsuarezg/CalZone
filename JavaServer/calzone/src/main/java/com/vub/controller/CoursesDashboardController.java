package com.vub.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vub.model.Course;
import com.vub.model.CourseComponent;
import com.vub.model.CourseComponent.CourseComponentType;
import com.vub.service.CourseService;

//@RequestMapping("/CourseInformation")
@Controller
public class CoursesDashboardController {

	// Serving Enroll Courses Page
	@RequestMapping(value = "/coursesdashboard", method = RequestMethod.GET)
	public String courseDachbaord(ModelMap model) {
		
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		CourseService courseService = (CourseService) context.getBean("courseService");
		
		
		Course course1 = new Course();
		course1.setCourseName("Course Name 1");
		CourseComponent cc1 = new CourseComponent();
		cc1.setType(CourseComponentType.HOC);
		cc1.setContactHours(40);
		cc1.setCourse(course1);
		CourseComponent cc2 = new CourseComponent();
		cc2.setType(CourseComponentType.WPO);
		cc2.setContactHours(80);
		cc2.setCourse(course1);
		ArrayList<CourseComponent> ccl1 = new ArrayList<CourseComponent>();
		ccl1.add(cc1);
		ccl1.add(cc2);
		course1.setCourseComponents(ccl1);
		List<Course> cl = new ArrayList<Course>();
		
		cl = courseService.getCourses();
		
		model.addAttribute("courseList", cl);
		
		return "CourseDashboardTable";
	}
}
