package processing;

import java.util.Optional;

import core.GameContext;
import processing.core.PApplet;
import processing.core.PConstants;

public class PGameContext implements GameContext {
  private PApplet sketch;

  public PGameContext(PApplet sketch) {
    this.sketch = sketch;
  }

  @Override
  public Object loadImage(String path) {
    return sketch.loadImage(path);
  }

  @Override
  public long millis() {
    return sketch.millis();
  }

  @Override
  public void seedNoise(long seed) {
    sketch.noiseSeed(seed);
  }

  @Override
  public float noise(float x, float y) {
    return sketch.noise(x, y);
  }

  @Override
  public void seedRandom(long seed) {
    sketch.randomSeed(seed);
  }

  @Override
  public float random() {
    return sketch.random(1);
  }

  @Override
  public Optional<Character> keyPressed() {
    if (sketch.keyPressed && sketch.key != PConstants.CODED)
      return Optional.of(sketch.key);
    else
      return Optional.empty();
  }

}
