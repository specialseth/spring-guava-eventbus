package us.martinlab.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;

import java.lang.reflect.Method;

/**
 * Bean Post Processor that will register {@link EventBusSubscriber}s with the correct EventBus
 */
public class BusSubscriberBeanPostProcessor implements BeanFactoryAware, DestructionAwareBeanPostProcessor {
	private static Logger logger = LoggerFactory.getLogger(BusSubscriberBeanPostProcessor.class);

	private BeanFactory beanFactory;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Class<?> clazz = bean.getClass();
		EventBusSubscriber eventBusSubscriberAnnotation = clazz.getAnnotation(EventBusSubscriber.class);

		// note that since we are using Guava's EventBus we don't need to be concerned about checking for the method
		// annotation @Subscribe but for completeness we will.
		if (null == eventBusSubscriberAnnotation || !hasSubscribingMethod(bean)) return bean;

		String module = eventBusSubscriberAnnotation.module();
		EventBusProvider eventBusProvider = beanFactory.getBean(EventBusProvider.class);
		eventBusProvider.get(module).register(bean);
		logger.info("Registered {} with EventBus:[{}].", clazz, module);

		return bean;
	}

	@Override
	public boolean requiresDestruction(Object o) {
		return isEventBusSubscriber(o);
	}

	@Override
	public void postProcessBeforeDestruction(Object bean, String s) throws BeansException {
		if (isEventBusSubscriber(bean)) {
			EventBusSubscriber annotation = bean.getClass().getAnnotation(EventBusSubscriber.class);
			String module = annotation.module();
			EventBusProvider eventBusProvider = beanFactory.getBean(EventBusProvider.class);
			EventBus eventBus = eventBusProvider.get(module);
			eventBus.unregister(bean);
		}
	}

	private boolean isEventBusSubscriber(Object bean) {
		return null != bean.getClass().getAnnotation(EventBusSubscriber.class);
	}

	private boolean hasSubscribingMethod(Object bean) {
		Class<?> clazz = bean.getClass();
		Subscribe annotation = getAnnotation(clazz);
		return null != annotation;
	}

	private Subscribe getAnnotation(Class<?> clazz) {
		if (null == clazz || Object.class.equals(clazz)) {
			return null;
		}

		for (Method m: clazz.getMethods()) {
			Subscribe subscribeAnnotation = m.getAnnotation(Subscribe.class);
			if (null != subscribeAnnotation) return subscribeAnnotation;
		}

		// support extending a class with a subscribing method
		return getAnnotation(clazz.getSuperclass());
	}


	private boolean isBusSubscriber(Class<?> clazz) {
		return null != clazz.getAnnotation(EventBusSubscriber.class);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory; 
	}
}
