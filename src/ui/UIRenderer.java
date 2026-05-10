package ui;

public interface UIRenderer {
  void drawRect(float x, float y, float w, float h);

  void drawText(String str, float x, float y);

  void setColor(int color);

  void drawImage(Object img, float x, float y, float w, float h);

  void drawImageFit(Object img, float x, float y, float maxW, float maxH);
}
