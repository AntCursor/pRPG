package ui.components;

import types.Color;
import types.Vec2;
import ui.FloatProvider;

public class ProgressBar extends Label {
  private FloatProvider floatProvider = () -> {
    return 0.f;
  };
  private int background = Color.rgb(200, 200, 200);
  private int foreground = Color.rgb(0, 200, 80);

  public ProgressBar(float x, float y, float w, float h) {
    super(x, y, w, h);
  }

  public ProgressBar value(FloatProvider fprov) {
    floatProvider = fprov;
    return this;
  }

  @Override
  public void draw(Vec2 parentPos, Vec2 parentSize) {
    float relAbsX = relPos.x * parentSize.x;
    float relAbsY = relPos.y * parentSize.y;

    float absX = relAbsX + parentPos.x;
    float absY = relAbsY + parentPos.y;

    float absW = relSize.x * parentSize.x;
    float absH = relSize.y * parentSize.y;

    float progress = floatProvider.get();

    renderer.setColor(background);
    renderer.drawRect(absX, absY, absW, absH);

    if (progress > 0.03f) {
      renderer.setColor(foreground);

      float wScale = absW > absH ? progress : 1.f;
      float hScale = absH > absW ? progress : 1.f;

      renderer.drawRect(absX, absY, wScale * absW, hScale * absH);
    }

    float textX = absX + absW * .5f;
    float textY = absY + absH * .5f;

    super.drawText(textX, textY);
  }

}
