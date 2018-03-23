package us.martinlab.eventbus;

import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;

import java.util.concurrent.ConcurrentMap;

/**
 * static provider used to retrieve instances of EventBus for a given module
 *
 *
 *
 */
public class EventBusProvider {

	private final ConcurrentMap<String, EventBus> eventbuses;

	private EventBusProvider() {
		eventbuses = Maps.newConcurrentMap();
	}

	public static EventBusProvider createProvider() { return new EventBusProvider(); }

	/**
	 * retrieve the event bus for the supplied module. Create a new EventBus if no bus exists for the module
	 * @param module
	 * @return
	 */
	public EventBus get(String module) {
		return eventbuses.computeIfAbsent(module, n -> new EventBus());
	}

}
