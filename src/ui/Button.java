package ui;

import types.Color;

public class Button extends UIComponent {
  private String label = "";
  private Runnable action;
  private int color = Color.rgb(0xff, 0xff, 0xff);
  private int fontColor = Color.rgb(0, 0, 0);

  Button(float relX, float relY, float relW, float relH) {
    super(relX, relY, relW, relH);
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
  void draw(float pX, float pY, float pW, float pH) {
    renderer.setColor(color);
    renderer.drawRect(
        relX * pW + pX,
        relY * pH + pY,
        relW * pW, relH * pH);

    renderer.setColor(fontColor);
    renderer.drawText(
        label,
        (float) (relX * pW + pX + 0.5 * relW * pW),
        (float) (relY * pH + pY + 0.5 * relH * pH));
  }

  @Override
  void handleClick(float x, float y, float width, float height) {
    action.run();
  }
}
