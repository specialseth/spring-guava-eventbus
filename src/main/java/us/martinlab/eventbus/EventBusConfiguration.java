package us.martinlab.eventbus;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * This is a spring enabled framework that allows for event bus subscribers to automatically registered with the
 * appropriate EventBus module. This is completed by annotating the class with the @{@link EventBusSubscriber} annotation
 * and populating the module field with the appropriate module name (this is user defined).
 *
 * You class will also need a method that will handled the Events which is annotated with @{@link com.google.common.eventbus.Subscribe}
 *
 * @see com.google.common.eventbus.EventBus
 *
 */
@Configuration
public class EventBusConfiguration {

	/**
	 * register BeanPostProcessor to handle automatically registering subscribers with an event bus
	 * @return the bus subscriber bean post processor
	 */
	@Bean
	public BusSubscriberBeanPostProcessor autoSubscribeEventBusListeners() {
		return new BusSubscriberBeanPostProcessor();
	}

	@Bean
	@Qualifier("EVENT_BUS_PROVIDER")
	public EventBusProvider eventBusProvider() {
		return EventBusProvider.createProvider();
	}
}
