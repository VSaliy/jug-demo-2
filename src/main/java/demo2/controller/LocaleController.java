package demo2.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo2.service.LocaleService;
import demo2.service.TrainingSchedule;
import domain.Course;

/**
 * The only controller for this application. Returns a greeting based on this
 * server's location, if possible.
 * <p>
 * To keep it simple, this controller just returns HTML (As a REST style
 * response body). No templates or JSP. For a real example, consider using
 * Thymeleaf, which Spring Boot can configure automatically for you (and is a
 * simple alternative to JSP, SiteMesh or Tiles).
 * 
 * @author Paul Chapman
 * @see http://www.thymeleaf.org
 */
@Controller
public class LocaleController {

	protected LocaleService localeService;

	protected TrainingSchedule trainingSchedule;

	/**
	 * Setup dependencies. Autowiring data-members directly is quick and easy,
	 * but can be less flexible, especially for testing.
	 * 
	 * @param localeService
	 *            Used to work out where this process is running.
	 * @param trainingSchedule
	 *            Provides access to Australian training schedule.
	 */
	@Autowired
	public LocaleController(LocaleService localeService,
			TrainingSchedule trainingSchedule) {
		this.localeService = localeService;
		this.trainingSchedule = trainingSchedule;

		Logger.getLogger("LocaleController")
				.warning("Created LocaleController");
	}

	/**
	 * Home page tries to work out where this process is running and generates a
	 * suitable greeting and a list of courses offered in this region. The
	 * course listing allows us to demo the JPA functionality.
	 * 
	 * @return The content to display in the browser - raw HTML.
	 */
	@RequestMapping("/")
	@ResponseBody
	String home() {
		String location = localeService.getLocation();
		return "<html><body><h1>Hello "
				+ location
				+ "!</h1><p>One instant web-app.<p>"
				+ "<p>Courses in "
				+ location
				+ " this year are:"
				+ getCourses(location)
				+ "<p>All courses: <a href='/courses'>here</a></p></body></html>";
	}

	/**
	 * Generate a full list of courses - pulls in all the courses in the
	 * underlying database.
	 * 
	 * @return The content to display in the browser - raw HTML.
	 */
	@RequestMapping("/courses")
	@ResponseBody
	String courses() {
		String content = "<html><body><h1>Courses</h1><p>Courses available this year are:</p><pre>";

		for (Course course : trainingSchedule.getCourses(null))
			content += "<li>" + course + "</li>";

		content += "</pre></body></html>";
		return content;
	}

	/**
	 * Get the courses as a predefined text block - but only because we are not
	 * using an HTML template technology like JSP or Thymeleaf.
	 * 
	 * @param location
	 * @return
	 */
	String getCourses(String location) {
		String content = "<p><pre>";

		for (Course course : trainingSchedule.getCourses(location))
			content += "<li>" + course + "</li>";

		content += "</pre></p>";
		return content;
	}

}
