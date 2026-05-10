package ui;

import types.Vec2;

abstract class UIComponent {
  static UIRenderer renderer;

  protected Vec2 relPos;
  protected Vec2 relSize;

  UIComponent(Vec2 relPos, Vec2 relSize) {
    this.relPos = relPos;
    this.relSize = relSize;
  }

  abstract void draw(Vec2 parentPos, Vec2 parentSize);

  void handleClick(Vec2 clickPos, Vec2 surfaceSize) {
  }
}
