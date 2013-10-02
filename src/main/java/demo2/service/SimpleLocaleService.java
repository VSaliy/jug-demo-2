package demo2.service;

import java.util.Locale;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Quick and dirty implementation using the Java locale.
 * 
 * @author Paul Chapman
 */
@Service
@Profile("Quick")
public class SimpleLocaleService implements LocaleService {

	public SimpleLocaleService() {
		LoggerFactory.getLogger(getClass()).warn(
				"Created " + getClass().getSimpleName());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see demo2.service.LocaleService#getLocation()
	 */
	@Override
	public String getLocation() {
		// You will notice that this doesn't work as expected - a known
		// 'feature' of Java 7.
		return Locale.getDefault().getDisplayCountry();
	}

}
