package core;

import java.util.Optional;

public interface GameContext {

  Object loadImage(String path);

  long millis();

  void seedNoise(long seed);

  float noise(float x, float y);

  void seedRandom(long seed);

  float random();

  Optional<Character> keyPressed();
}
