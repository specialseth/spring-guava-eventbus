package us.martinlab.eventbus;

import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * An example of the way you may want to configure the application to use this library
 */
@Configuration
public class TestConfiguration {

	public final static String TEST_MODULE = "test_module";

	@Bean
	@Qualifier(TEST_MODULE)
	public EventBus testEventBus() {
		return EventBusProvider.get(TEST_MODULE);
	}

	@Bean
	public TestEventSubscriber<String> subscriber() {
		return new TestEventSubscriber<>();
	}

}
