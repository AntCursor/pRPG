package processing;

import processing.core.*;
import ui.UIRenderer;

public class PUIRenderer implements UIRenderer {
  private PApplet sketch;

  private int borderRadius;
  private int padding;

  public PUIRenderer(PApplet sketch, int borderRadius, int padding) {
    this.sketch = sketch;
    this.borderRadius = borderRadius;
    this.padding = padding;
    sketch.textAlign(PConstants.CENTER);
  }

  @Override
  public void drawRect(float x, float y, float w, float h) {
    sketch.rect(
        x + padding,
        y + padding,
        w - 2 * padding,
        h - 2 * padding,
        borderRadius);
  }

  @Override
  public void drawText(String str, float x, float y) {
    sketch.text(str, x, y);
  }

  @Override
  public void setColor(int col) {
    sketch.fill(col);
  }

  @Override
  public void drawImage(Object img, float x, float y, float w, float h) {
    sketch.image((PImage) img, x, y, w, h);
  }

  @Override
  public void drawImageFit(Object img, float x, float y, float maxW, float maxH) {
    PImage image = (PImage) img;
    float scale = Math.min(maxW / image.width, maxH / image.height);
    sketch.image(image, x, y, image.width * scale, image.height * scale);
  }

  @Override
  public void background(int color) {
    sketch.background(color);
  }

  @Override
  public void noStroke() {
    sketch.noStroke();
  }

  @Override
  public void stroke(int color) {
    sketch.stroke(color);
  }

  @Override
  public void fill(int color) {
    sketch.fill(color);
  }

  @Override
  public int width() {
    return sketch.width;
  }

  @Override
  public int height() {
    return sketch.height;
  }
}
