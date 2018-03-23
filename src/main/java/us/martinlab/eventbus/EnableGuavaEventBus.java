package us.martinlab.eventbus;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Add this to your spring application configuration to import the EventBusConfiguration
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(EventBusConfiguration.class)
@Documented
public @interface EnableGuavaEventBus {
}
