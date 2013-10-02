package domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:domain/test-db-config.xml")
public class TestDatabase {

	@Autowired
	DataSource dataSource;

	@Test
	public void checkDatabase() {
		JdbcTemplate template = new JdbcTemplate(dataSource);
		
		int nCourses = template.queryForObject("SELECT COUNT(*) FROM COURSE", Integer.class);
		Assert.assertEquals("Courses in database",5, nCourses);
	}
	
	@Test
	public void courseTest() {
		Course course = new Course();
		course.setDuration(4);
		course.setEarlyBirdDiscount(10);
		course.setId(0);
		course.setLocation("Melbourne");
		course.setName("Core Spring");
		course.setNumber("12345");
		course.setPrice(new BigDecimal(3070.0));
		course.setStart(new Date(113, 9, 14));

		System.out.println(course);
	}
}
