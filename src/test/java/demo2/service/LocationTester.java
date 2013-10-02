package demo2.service;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.ResourceAccessException;

/**
 * Test the location service implementations.
 * 
 * @author Paul Chapman
 */
public class LocationTester {

	protected static final Logger logger = LoggerFactory
			.getLogger(LocationTester.class);

	@Test
	public void test1() {
		String location = new SimpleLocaleService().getLocation();
		Assert.assertEquals("Will always comes back as the US",
				"United States", location);
		logger.info(location);
	}

	@Test
	public void test2() {
		try {
			String location = new LocationViaREST().getLocation();
			System.out.println(location);
		} catch (ResourceAccessException e) {

			logger.error("Unable to connect to the Internet - test aborted.");
		}
	}

}
