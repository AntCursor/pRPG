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

  public static Panel root() {
    return new Panel(new Vec2(0, 0), new Vec2(1, 1));
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
  public void handleClick(Vec2 clickPos, Vec2 parentPos, Vec2 parentSize) {
    Vec2 panelPos = relPos.hProd(parentSize).add(parentPos);
    Vec2 panelSize = relSize.hProd(parentSize);

    for (UIComponent c : components) {
      Vec2 absPos = c.relPos.hProd(panelSize).add(panelPos);
      Vec2 absSize = c.relSize.hProd(panelSize);

      boolean hitX = clickPos.x > absPos.x && clickPos.x < absPos.x + absSize.x;
      boolean hitY = clickPos.y > absPos.y && clickPos.y < absPos.y + absSize.y;

      if (hitX && hitY) {
        c.handleClick(clickPos, absPos, absSize);
      }
    }
  }
}
