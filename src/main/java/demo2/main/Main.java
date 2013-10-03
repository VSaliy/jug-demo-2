package demo2.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import demo2.service.LocaleService;

/**
 * Main entry point for our application using Spring Boot. It can be run as an
 * executable or as a standard war file.
 * <p>
 * Details of annotations used:
 * <ul>
 * <li><tt>@EnableAutoConfiguration</tt>: makes Spring Boot setup its defaults.
 * <li><tt>@ComponentScan</tt>: Scan for @Component classes, including @Configuration
 * classes.
 * <li><tt>@ImportResource</tt>: Import Spring XML configuration files.
 * </ul>
 * 
 * @author Paul Chapman
 */
@EnableAutoConfiguration
@ComponentScan({ "demo2.*", "utils", "domain" })
@ImportResource(value = "classpath:domain/db-config.xml")
@EnableJpaRepositories(basePackages = "domain")
@EnableTransactionManagement
public class Main extends SpringBootServletInitializer {

	// Quick profile - see SimpleLocaleService
	public static final String QUICK_PROFILE = "Quick";

	// REST profile - see LocationViaREST
	public static final String REST_PROFILE = "REST";

	/**
	 * We are using the constructor to perform some useful initialisations:
	 * <ol>
	 * <li>Set the Spring Profile to use ('Quick' or 'REST') which in turn
	 * selects the {@link LocaleService} to use. Profiles are a Spring feature
	 * from V3.1 onwards.
	 * <li>Enable (or not) the BeanLogger. When enabled we can see all the beans
	 * Spring is creating for us automatically.
	 * </ol>
	 */
	public Main() {
		// Set active profiles - in this case, just one.
		System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,
				REST_PROFILE);

		// Enable bean logging?
		BeanLogger.enabled = false;
	}

	/**
	 * When running as a war, this class is picked up as the Servlet
	 * Initialiser. You must override this abstract method to tell it what
	 * Spring Boot Configuration file to use. In this case, it is using itself,
	 * so it will pick up and use all the class annotations above to initialise
	 * the application.
	 * <p>
	 * You <i>must</i> have a container that supports the Servlet 3 spec.
	 * <p>
	 * This used the be the abstract method on SpringBootServletInitializer but
	 * it was changed to getConfigClass() in a later snapshot (after 02-Oct-13).
	 */
	@Override
	protected Class<?>[] getConfigClasses() {
		return new Class<?>[] { Main.class };
	}

	/**
	 * In later versions of Spring Data (after 02-Oct-2013) getConfigClasses()
	 * is replaced by getConfigClass().
	 */
	protected Class<?> getConfigClass() {
		return Main.class;
	}

	/**
	 * When running as a Java application, Spring Boot will automatically run up
	 * an embedded container (Tomcat by default). <tt>SpringApplication.run</tt>
	 * tells Spring Boot to use this class as the initialiser for the whole
	 * application (via the class annotations above).
	 * 
	 * @param args
	 *            Any command line arguments.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		new Main().run(args);
	}

	/**
	 * Run the application using Spring Boot.
	 * 
	 * @param args
	 *            Any command line arguments.
	 */
	protected void run(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
