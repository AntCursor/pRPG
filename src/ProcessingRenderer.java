import processing.core.*;

class ProcessingRenderer implements Renderer {
  private PApplet sketch;

  int borderRadius;
  int padding;

  ProcessingRenderer(PApplet sketch, int borderRadius, int padding) {
    this.sketch = sketch;
    this.borderRadius = borderRadius;
    this.padding = padding;
    sketch.textAlign(PConstants.CENTER);
  }

  public void drawRect(float x, float y, float w, float h) {
    sketch.rect(
        x + padding,
        y + padding,
        w - 2 * padding,
        h - 2 * padding,
        borderRadius);
  }

  public void drawText(String str, float x, float y) {
    sketch.text(str, x, y);
  }

  public void setColor(int col) {
    sketch.fill(col);
  }
}
