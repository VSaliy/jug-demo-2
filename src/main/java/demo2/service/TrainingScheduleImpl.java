package demo2.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import domain.Course;
import domain.TrainingRepository;

/**
 * Service for accessing the training-schedule (allows us to demonstrate the use
 * of a Spring Data JPA auto-generated repository).
 * 
 * @author Paul Chapman
 */
@Service
public class TrainingScheduleImpl implements TrainingSchedule {

	protected TrainingRepository courses;

	protected String[] STATES = { "Australian Capital Territory",
			"New South Wales", "Northern Territory", "Queensland",
			"South Australia", "Tasmania", "Victoria", "Western Australia" };

	protected String[] STATES3 = { "ACT", "NSW", "NT", "QLD", "SA", "TAS",
			"VIC", "WA" };

	/**
	 * Inject our repository as a dependency. Spring Data JPA will have
	 * automatically created the implementation.
	 * 
	 * @param courses
	 *            The training repository.
	 */
	@Autowired
	public TrainingScheduleImpl(TrainingRepository courses) {
		this.courses = courses;
	}

	/**
	 * Get a course for the given location. If the location is null, or no
	 * courses exist for that location, all courses are returned.
	 * 
	 * @param location
	 *            An Australian capital city and/or state (or null to fetch all
	 *            courses).
	 * @return A non-null list of courses.
	 */
	@Override
	@Transactional
	public List<Course> getCourses(String location) {
		Logger logger = LoggerFactory.getLogger(TrainingScheduleImpl.class);
		List<Course> courseList = null;
		logger.warn("Looking for courses in " + location);

		// Extract city and/or state from location. Convert state names
		// to their standard 2 or 3 character abbreviation.
		if (!StringUtils.isEmpty(location)) {
			String bits[] = location.split(",");

			// If two stings they should be city/suburb and state
			// If one string it could be the city or the state, we don't know
			String city = bits[0].trim();
			String state = bits.length > 1 ? bits[1].trim() : city;

			for (int i = 0; i < STATES.length; i++) {
				if (STATES[i].equalsIgnoreCase(state))
					state = STATES3[i];
			}

			logger.warn("Looking for courses in " + city + " or " + state);
			courseList = courses.findCourseByLocation(city);

			if (courseList.size() == 0)
				courseList = courses.findCourseByState(state);
		}

		// If not luck, return them all
		if (courseList == null || courseList.isEmpty())
			courseList = courses.findAll();

		return courseList;
	}
}
