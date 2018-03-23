package us.martinlab.eventbus;

import com.google.common.eventbus.Subscribe;

@EventBusSubscriber(module = "TEST_MODULE")
public class TestEventSubscriber<T> {

	private int handledEvents = 0;

	public TestEventSubscriber() {
	}

	@Subscribe
	public void handleEvent(T event) {
		handledEvents++;
	}

	public int getHandledEventsCount() { return handledEvents; }
}
