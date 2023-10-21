package de.turing85;

import java.util.concurrent.atomic.AtomicInteger;

import jakarta.inject.Singleton;

import io.quarkus.runtime.configuration.MemorySize;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.io.FileUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.timer;

@Singleton
@Slf4j
@SuppressWarnings("unused")
public class TimerRoute extends RouteBuilder {
  private final AtomicInteger calls = new AtomicInteger();

  private final MemorySize objectSize;

  public TimerRoute(
      @ConfigProperty(name = "object.size", defaultValue = "100M") MemorySize objectSize) {
    this.objectSize = objectSize;
  }

  @Override
  public void configure() {
    if (log.isInfoEnabled()) {
      log.info("Object-size = {}", FileUtils.byteCountToDisplaySize(objectSize.asBigInteger()));
    }
    // @formatter:off
    from(timer("timer").delay(-1))
        .process(exchange -> {
          log.info("calls: {}", calls.incrementAndGet());
          exchange.getIn().setBody(new LargeObject(objectSize.asBigInteger().intValue()));
        })
        .log("${body.fieldTwo}");
    // @formatter:on
  }
}
