# Description
This is a simple library used to make configuring and using a Guava EventBus in your Spring projects easy. See [Guava EventBus Documentation](https://github.com/google/guava/wiki/EventBusExplained) for more specifics about the Guava EventBus.

# Getting Started

## Maven

```XML
<repositories>
    <repository>
        <id>jcenter</id>
        <url>http://jcenter.bintray.com/</url>
    </repository>
</repositories>

<dependency>
  <groupId>us.martinlab</groupId> 
  <artifactId>spring-guava-eventbus</artifactId> 
  <version>0.1.3</version> 
  <type>pom</type> 
</dependency>
```

# Example

Simply adding the `@EnableGuavaEventBus` annotation to any `@Config` class within your Spring application will integrate the event bus classes and allow you to inject an EventBus with a qualifier matching the module names provided to the "modules" value.

```Java
@Config
@EnableGuavaEventBus(modules = {"Module1", "Module2"}
public class MyEventBusConfig {
}

@Service
public class MyEventProducer {
  @Autowired
  @Qualifier("Module1")
  private EventBus eventBus;
  
  public void serviceMethod() {
    eventBus.post("Service Method Called");
  }
}
```

To register subscriber classes you would annotate the class with `@EventBusSubriber(module = "module_name")`. The library will automatically register these subscribers with the correct EventBus instance matching the modules described in the `@EnableGuavaEventBus` annotation.

```Java
@EventBusSubscriber(module = "Module1")
public class MySubscriber() {

  @Subscribe
  public void eventHandler(String message) {
    System.out.println(message);
  }
}
