package demo2.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * A Spring Bean Post-Processor to log all beans to the console.
 * 
 * @author Paul Chapman
 */
@Component
public class BeanLogger implements BeanPostProcessor {

	public static boolean enabled = false;

	protected static final Logger LOGGER = LoggerFactory.getLogger("bean-logger");

	@Override
	public Object postProcessAfterInitialization(Object bean, String name)
			throws BeansException {
		if (enabled) {
			if (name.equals(bean.getClass().getName()))
				name = bean.getClass().getSimpleName();

			LOGGER.info(name + ": " + bean.getClass().getName());
		}

		// Remember to return the original bean or we've lost it!
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String name)
			throws BeansException {
		// Remember to return the original bean or we've lost it!
		return bean;
	}

}
