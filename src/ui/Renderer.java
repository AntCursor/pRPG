package ui;

public interface Renderer {
  void drawRect(float x, float y, float w, float h);

  void drawText(String str, float x, float y);

  void setColor(int color);
}
