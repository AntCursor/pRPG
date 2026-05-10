package ui;

import types.Color;
import types.Vec2;

public class Button extends UIComponent {
  private String label = "";
  private Runnable action;
  private int color = Color.rgb(0xff, 0xff, 0xff);
  private int fontColor = Color.rgb(0, 0, 0);

  Button(Vec2 relPos, Vec2 relSize) {
    super(relPos, relSize);
  }

  Button label(String label) {
    this.label = label;
    return this;
  }

  Button action(Runnable action) {
    this.action = action;
    return this;
  }

  Button color(int color) {
    this.color = color;
    return this;
  }

  Button fontColor(int color) {
    this.fontColor = color;
    return this;
  }

  @Override
  public void draw(Vec2 parentPos, Vec2 parentSize) {
    Vec2 rectPos = relPos.hProd(parentSize).add(parentPos);
    Vec2 rectSize = relSize.hProd(parentSize);

    renderer.setColor(color);
    renderer.drawRect(
        rectPos.x,
        rectPos.y,
        rectSize.x,
        rectSize.y);

    Vec2 textPos = relPos
        .hProd(parentSize)
        .add(parentPos)
        .add(relSize.hProd(parentSize).prod(0.5f));

    renderer.setColor(fontColor);
    renderer.drawText(
        label,
        textPos.x, textPos.y);
  }

  @Override
  public void handleClick(Vec2 clickPos, Vec2 surfaceSize) {
    action.run();
  }
}
