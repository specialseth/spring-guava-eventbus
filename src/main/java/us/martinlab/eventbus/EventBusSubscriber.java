package us.martinlab.eventbus;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Used to identify a class a spring bean as an event bus subscriber
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface EventBusSubscriber {
	String module();
}
