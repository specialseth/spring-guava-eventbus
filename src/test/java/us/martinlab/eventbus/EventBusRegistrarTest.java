package us.martinlab.eventbus;

import com.google.common.eventbus.EventBus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {EventBusRegistrarTest.RegistrarConfig.class})
public class EventBusRegistrarTest {

	public final static String TEST_MODULE = "TEST_MODULE";

	@Autowired
	@Qualifier(TEST_MODULE)
	EventBus bus;

	@Autowired
	TestEventSubscriber<String> sub;

	@Test
	public void postEvent() {
		bus.post("My Event Happened!");
		assertThat(sub.getHandledEventsCount(), is(not(0)));
	}

	@EnableGuavaEventBus(modules = {TEST_MODULE, "another_module"})
	public static class RegistrarConfig {

		@Bean
		public TestEventSubscriber<String> subscriber() {
			return new TestEventSubscriber<>();
		}
	}
}
