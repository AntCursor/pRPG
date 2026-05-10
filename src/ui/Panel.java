package ui;

public class Panel extends UIComponent {
  private UIComponent[] components;

  Panel(float relX, float relY, float relW, float relH) {
    super(relX, relY, relW, relH);
  }

  Panel components(UIComponent[] comps) {
    components = comps;
    return this;
  }

  @Override
  void draw(float pX, float pY, float pW, float pH) {
    for (UIComponent c : components) {
      c.draw(
          relX * pW + pX,
          relY * pH + pY,
          relW * pW,
          relH * pH);
    }
  }

  @Override
  void handleClick(float x, float y, float width, float height) {
    for (UIComponent c : components) {
      boolean hit_x = x > c.relX * width
          && x < c.relX * width + c.relW * width;
      boolean hit_y = y > c.relY * height
          && y < c.relY * height + c.relH * height;

      if (hit_x && hit_y) {
        c.handleClick(
            x, y,
            width * relW,
            height * relH);
      }
    }
  }
}
