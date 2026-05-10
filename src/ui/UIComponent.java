package ui;

abstract class UIComponent {
  static UIRenderer renderer;

  protected float relX, relY, relW, relH;

  UIComponent(float relX, float relY, float relW, float relH) {
    this.relX = relX;
    this.relY = relY;
    this.relW = relW;
    this.relH = relH;
  }

  abstract void draw(float pX, float pY, float pW, float pH);

  void handleClick(float x, float y, float width, float height) {
  }
}
