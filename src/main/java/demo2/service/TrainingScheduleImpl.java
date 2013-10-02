package demo2.service;

import java.util.List;

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
	 *            An Australian capital city (or null to fetch all courses).
	 * @return A non-null list of courses.
	 */
	@Override
	@Transactional
	public List<Course> getCourses(String location) {

		location = (location == null) ? null : location.split(",")[0];
		List<Course> courseList = null;

		LoggerFactory.getLogger(TrainingScheduleImpl.class).warn(
				"Looking for courses in " + location);

		if (!StringUtils.isEmpty(location))
			courseList = courses.findCourseByLocation(location);

		if (courseList == null || courseList.isEmpty())
			courseList = courses.findAll();

		return courseList;
	}
}
