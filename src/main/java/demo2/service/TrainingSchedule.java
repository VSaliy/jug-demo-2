package demo2.service;

import java.util.List;

import domain.Course;

public interface TrainingSchedule {

	List<Course> getCourses(String location);
}
