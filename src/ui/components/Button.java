package ui.components;

import types.Color;
import types.Vec2;
import ui.*;

public class Button extends Label {
  private Runnable action = () -> {
  };
  private int color = Color.rgb(0xff, 0xff, 0xff);

  public Button(float x, float y, float w, float h) {
    super(x, y, w, h);
  }

  @Override
  public Button text(String text) {
    super.text(text);
    return this;
  }

  @Override
  public Button text(TextProvider prov) {
    super.text(prov);
    return this;
  }

  @Override
  public Button fontColor(int color) {
    super.fontColor(color);
    return this;
  }

  @Override
  public Button fontColor(ColorProvider prov) {
    super.fontColor(prov);
    return this;
  }

  public Button action(Runnable action) {
    this.action = action;
    return this;
  }

  public Button color(int color) {
    this.color = color;
    return this;
  }

  @Override
  public void draw(Vec2 parentPos, Vec2 parentSize) {
    float rectX = relPos.x * parentSize.x + parentPos.x;
    float rectY = relPos.y * parentSize.y + parentPos.y;
    float rectW = relSize.x * parentSize.x;
    float rectH = relSize.y * parentSize.y;

    if (Color.a(color) != 0) {
      renderer.setColor(color);
      renderer.drawRect(rectX, rectY, rectW, rectH);
    }

    drawText(rectX + rectW * 0.5f, rectY + rectH * 0.5f);
  }

  @Override
  public void handleClick(Vec2 clickPos, Vec2 parentPos, Vec2 parentSize) {
    action.run();
  }
}
