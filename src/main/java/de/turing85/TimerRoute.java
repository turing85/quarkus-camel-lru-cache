package de.turing85;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.camel.builder.RouteBuilder;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.timer;

@SuppressWarnings("unused")
public class TimerRoute extends RouteBuilder {
  private final AtomicInteger calls = new AtomicInteger();

  @Override
  public void configure() {
    // @formatter:off
    from(
        timer("timer")
            .period(Duration.ofMillis(100).toMillis())
            .fixedRate(true))
        .process(exchange -> {
          log.info("calls: {}", calls.incrementAndGet());
          exchange.getIn().setBody(new LargeObject());
        })
        .log("${body.fieldTwo}");
    // @formatter:on
  }
}
