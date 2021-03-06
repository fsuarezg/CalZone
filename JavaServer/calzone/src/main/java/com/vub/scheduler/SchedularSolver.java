package com.vub.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.config.solver.XmlSolverFactory;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vub.model.Course;
import com.vub.model.CourseComponent;
import com.vub.model.Entry;
import com.vub.model.Room;
import com.vub.model.Traject;

/**
 * Class which acts as an extra abstraction layer above the 'Solver' class from
 * the optaplanner API.
 * 
 * <p>
 * This class also acts as a service to the drools rule engine to get the
 * collection of trajects.
 * </p>
 * 
 * @author pieter
 * 
 */
public class SchedularSolver {
	final Logger logger = LoggerFactory.getLogger(getClass());

	private List<Date> startDateList;
	private List<Room> roomList;
	private List<Entry> entryList;
	private static Set<Traject> trajectList = null;
	
	private Solver solver;
	private Schedular bestSolution;

	/**
	 * Constructs a SchedulerSolver instance. This object is automaticcaly added
	 * to the working memory, so that it can be used by the drools rule engine.
	 * 
	 * @param startDateList
	 *            The available date slots that can be used for scheduling.
	 * @param roomList
	 *            The available rooms that can be used for scheduling.
	 * @param trajectList
	 *            The trajects that needs to be scheduled.
	 */
	public SchedularSolver(List<Date> startDateList, List<Room> roomList,
			Set<Traject> trajectList) {
		this.startDateList = startDateList;
		this.roomList = roomList;
		SchedularSolver.trajectList = trajectList;
		this.entryList = createEntryList(trajectList);
		
		this.solver = createSolverByXML();
		this.solver.setPlanningProblem(createSchedular());
		this.bestSolution = null;
	}
	/**
	 * This method should be invoked to start a scheduling. The method blocks
	 * until an optimal solution is found.
	 * 
	 * @return the best solution generated by the solver of optaplanner.
	 */
	public Schedular run() {
		solver.solve();
		this.bestSolution = (Schedular) solver.getBestSolution();
		return bestSolution;
	}
	
	public ScoreDirector getScoreDirector() {
		logger.info("Analyzing violations.");
		
		ScoreDirector scoreDirector = solver.getScoreDirectorFactory().buildScoreDirector();
		scoreDirector.setWorkingSolution(bestSolution);
		
		return scoreDirector;
		
	}

	/**
	 * @return the trajectList
	 */
	public static Set<Traject> getTrajectList() {
		return trajectList;
	}

	/**
	 * Constructs the solver by use of the solver configuration written in the
	 * XML file.
	 * 
	 * This is the recommended way above the creation of the solver by use of
	 * the API.
	 * 
	 * @return a solver instance.
	 */
	private Solver createSolverByXML() {
		XmlSolverFactory solverFactory = new XmlSolverFactory(
				"/com/vub/scheduler/SchedulerSolverConfig.xml");
		return solverFactory.buildSolver();
	}

	public Schedular createSchedular() {
		Schedular schedular = new Schedular();
		schedular.setStartDateList(startDateList);
		schedular.setRoomList(roomList);
		schedular.setEntryList(entryList);
		return schedular;
	}

	public List<Entry> createEntryList(Set<Traject> trajectList) {
		List<Entry> entryList = new ArrayList<Entry>();
		for (Traject traject : trajectList) {
			for (Course c : traject.getCourses()) {
				for (CourseComponent cc : c.getCourseComponents()) {
					int duration = cc.getDuration();
					int contactHours = cc.getContactHours();

					int indexInCourseComponent = 0;
					for (int currentTotalContactHours = 0; currentTotalContactHours < contactHours; currentTotalContactHours += duration) {
						Entry entry = new Entry();
						entry.setStartingDate(startDateList.get(0));
						entry.setRoom(roomList.get(0));
						entry.setCourseComponent(cc);
						entry.setFrozen(false);
						entry.setIndexInCourseComponent(indexInCourseComponent);
						entryList.add(entry);

						indexInCourseComponent++;
					}
				}
			}
		}

		return entryList;
	}
}
