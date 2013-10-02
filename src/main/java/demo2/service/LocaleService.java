package demo2.service;

/**
 * Generic service for finding the locale of the current process.
 * 
 * @author Paul Chapman
 */
public interface LocaleService {

	/**
	 * Get the location of the server.
	 * 
	 * @return The location as best we can work it out.
	 */
	public String getLocation();

}