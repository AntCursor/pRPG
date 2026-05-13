package ui.components;

import types.Vec2;
import ui.ImageProvider;

public class Image extends UIComponent {
  private ImageProvider imgProvider;
  private float maxW = 0, maxH = 0;

  public Image(float x, float y, float w, float h) {
    super(x, y, w, h);
  }

  public Image maxSize(float maxW, float maxH) {
    this.maxW = maxW;
    this.maxH = maxH;
    return this;
  }

  public Image image(ImageProvider provider) {
    this.imgProvider = provider;
    return this;
  }

  @Override
  public void draw(Vec2 parentPos, Vec2 parentSize) {
    float absX = relPos.x * parentSize.x + parentPos.x;
    float absY = relPos.y * parentSize.y + parentPos.y;
    float absW = relSize.x * parentSize.x;
    float absH = relSize.y * parentSize.y;

    float fitW = maxW == 0 ? absW : maxW * absW;
    float fitH = maxH == 0 ? absH : maxH * absH;
    Object img = imgProvider.get();
    if (img == null)
      return;
    renderer.drawImageFit(img, absX, absY, fitW, fitH);
  }
}
