package ui.components;

import types.Color;
import types.Vec2;
import ui.*;

public class Label extends UIComponent {
  protected TextProvider textProvider = () -> "";
  protected ColorProvider fontColorProvider = () -> Color.rgb(0, 0, 0);

  protected Label() {
  }

  public Label(float x, float y, float w, float h) {
    super(x, y, w, h);
  }

  public Label text(TextProvider prov) {
    this.textProvider = prov;
    return this;
  }

  public Label text(String s) {
    this.textProvider = () -> s;
    return this;
  }

  public Label fontColor(int color) {
    this.fontColorProvider = () -> color;
    return this;
  }

  public Label fontColor(ColorProvider prov) {
    this.fontColorProvider = prov;
    return this;
  }

  protected void drawText(float x, float y) {
    renderer.setColor(fontColorProvider.get());
    renderer.drawText(textProvider.get(), x, y);
  }

  @Override
  public void draw(Vec2 parentPos, Vec2 parentSize) {
    float absX = relPos.x * parentSize.x + parentPos.x;
    float absY = relPos.y * parentSize.y + parentPos.y;
    drawText(absX, absY);
  }
}
