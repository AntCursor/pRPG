class ProcessingRenderer implements Renderer {
  int borderRadius;
  int padding;

  ProcessingRenderer(int borderRadius, int padding) {
    this.borderRadius = borderRadius;
    this.padding = padding;
    textAlign(CENTER);
  }

  void drawRect(float x, float y, float w, float h) {
    rect(
      x + padding,
      y + padding,
      w - 2*padding,
      h - 2*padding,
      borderRadius);
  }
  void drawText(String str, float x, float y) {
    text(str, x, y);
  }
  void setColor(int col) {
    fill(color(col));
  }
}
