package de.turing85;

import java.util.Random;
import java.util.UUID;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Value;

@RegisterForReflection
@Value
public class LargeObject {
  private static final Random RANDOM = new Random();

  byte[] data;
  String fieldOne;
  String fieldTwo;

  public LargeObject(int byteSize) {
    this.data = new byte[byteSize];
    RANDOM.nextBytes(data);
    fieldOne = UUID.randomUUID().toString();
    fieldTwo = UUID.randomUUID().toString();
  }
}
