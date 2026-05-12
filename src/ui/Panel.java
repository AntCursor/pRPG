package ui;

import types.*;

public class Panel extends UIComponent {
  private List<UIComponent> components = new List<>();
  private int color = Color.rgba(0x00, 0x00, 0x00, 0x00);

  public Panel(float x, float y, float w, float h) {
    super(x, y, w, h);
  }

  public Panel() {
    super(0, 0, 0, 0);
  }

  public static Panel root() {
    return new Panel(0, 0, 1, 1);
  }

  public Panel add(UIComponent item) {
    components.add(item);
    return this;
  }

  public Panel color(int color) {
    this.color = color;
    return this;
  }

  @Override
  public void draw(Vec2 parentPos, Vec2 parentSize) {
    float panelX = relPos.x * parentSize.x + parentPos.x;
    float panelY = relPos.y * parentSize.y + parentPos.y;

    float panelW = relSize.x * parentSize.x;
    float panelH = relSize.y * parentSize.y;

    Vec2 cPos = new Vec2(panelX, panelY);
    Vec2 cSize = new Vec2(panelW, panelH);

    if (Color.a(color) != 0) {
      renderer.setColor(color);
      renderer.drawRect(panelX, panelY, panelW, panelH);
    }
    for (UIComponent c : components) {
      c.draw(cPos, cSize);
    }
  }

  @Override
  public void handleClick(Vec2 clickPos, Vec2 parentPos, Vec2 parentSize) {
    float panelX = relPos.x * parentSize.x + parentPos.x;
    float panelY = relPos.y * parentSize.y + parentPos.y;
    float panelW = relSize.x * parentSize.x;
    float panelH = relSize.y * parentSize.y;

    for (UIComponent c : components) {
      float absX = c.relPos.x * panelW + panelX;
      float absY = c.relPos.y * panelH + panelY;
      float absW = c.relSize.x * panelW;
      float absH = c.relSize.y * panelH;

      if (clickPos.x > absX && clickPos.x < absX + absW &&
          clickPos.y > absY && clickPos.y < absY + absH) {
        c.handleClick(clickPos, new Vec2(absX, absY), new Vec2(absW, absH));
      }
    }
  }
}
