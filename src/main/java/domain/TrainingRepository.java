package domain;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing Course data. Spring Data JPA will
 * automatically implement this at runtime. You must have Spring Data JPA, JPA
 * and a suitable JPA implementation on your class-path.
 * 
 * @author Paul Chapman
 */
@Repository
public interface TrainingRepository extends
		org.springframework.data.repository.Repository<Course, Long> {

	/**
	 * Find all known courses.
	 * 
	 * @return A non-null list of courses.
	 */
	List<Course> findAll();

	/**
	 * Find courses at a given location. If none are found, an empty list is
	 * returned.
	 * 
	 * @param location
	 *            An Australian capitol city (may not be null).
	 * @return A non-null but possibly empty list of courses.
	 */
	List<Course> findCourseByLocation(String location);

}
