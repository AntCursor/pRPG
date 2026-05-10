package ui;

import types.Color;
import types.Vec2;

public class Label extends UIComponent {
  protected String text = "";
  protected int fontColor = Color.rgb(0, 0, 0);

  protected Label() {
  }

  public Label(float x, float y, float w, float h) {
    super(x, y, w, h);
  }

  public Label text(String text) {
    this.text = text;
    return this;
  }

  public Label fontColor(int color) {
    this.fontColor = color;
    return this;
  }

  @Override
  public void draw(Vec2 parentPos, Vec2 parentSize) {
    float absX = relPos.x * parentSize.x + parentPos.x;
    float absY = relPos.y * parentSize.y + parentPos.y;

    renderer.setColor(fontColor);
    renderer.drawText(text, absX, absY);
  }
}
