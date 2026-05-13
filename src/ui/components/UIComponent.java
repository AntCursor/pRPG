package ui.components;

import types.Vec2;
import ui.UIRenderer;

public abstract class UIComponent {
  public static UIRenderer renderer;

  protected Vec2 relPos;
  protected Vec2 relSize;

  protected UIComponent() {
    relPos = new Vec2(0, 0);
    relSize = new Vec2(1, 1);
  }

  public UIComponent(float x, float y, float w, float h) {
    relPos = new Vec2(x, y);
    relSize = new Vec2(w, h);
  }

  public abstract void draw(Vec2 parentPos, Vec2 parentSize);

  public void handleClick(Vec2 clickPos, Vec2 parentPos, Vec2 parentSize) {
  }

}
