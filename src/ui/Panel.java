package ui;

import types.Vec2;

public class Panel extends UIComponent {
  private UIComponent[] components;

  public Panel(Vec2 relPos, Vec2 relSize) {
    super(relPos, relSize);
  }

  public Panel() {
    super(new Vec2(), new Vec2());
  }

  public Panel root() {
    this.relPos.x = 0;
    this.relPos.y = 0;
    this.relSize.x = 1;
    this.relSize.y = 1;

    return this;
  }

  public Panel components(UIComponent[] comps) {
    components = comps;
    return this;
  }

  @Override
  public void draw(Vec2 parentPos, Vec2 parentSize) {
    for (UIComponent c : components) {
      Vec2 cPos = relPos.hProd(parentSize).add(parentPos);
      Vec2 cSize = relSize.hProd(parentSize);

      c.draw(cPos, cSize);
    }
  }

  @Override
  public void handleClick(Vec2 clickPos, Vec2 surfaceSize) {
    for (UIComponent c : components) {
      Vec2 absPos = c.relPos.hProd(surfaceSize);
      Vec2 absSize = c.relSize.hProd(surfaceSize);

      boolean hit_x = clickPos.x > absPos.x
          && clickPos.x < absPos.x + absSize.x;

      boolean hit_y = clickPos.y > absPos.y
          && clickPos.y < absPos.y + absSize.y;

      if (hit_x && hit_y) {
        c.handleClick(clickPos, absSize);
      }
    }
  }
}
