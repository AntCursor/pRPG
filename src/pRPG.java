import processing.core.*;
import types.Color;
import types.Vec2;
import ui.*;

public class pRPG extends PApplet {

  Vec2 drawPos = new Vec2();

  PImage nixos;
  UIComponent rootPanel = Panel.root().components(new UIComponent[] {
      new Button(0.0f, 0.75f, 0.5f, 0.25f)
          .text("Left").action(() -> {
            drawPos.x -= 1;
          }),
      new Button(0.5f, 0.75f, 0.5f, 0.25f)
          .text("Right").action(() -> {
            drawPos.x += 1;
          }),
      new Image(0.f, 0.f, 1.f, 1.f)
          .image(() -> {
            return nixos;
          })
          .maxSize(1.f, 0.5f),
  });

  PUIRenderer pRenderer;

  Vec2 screenSize = new Vec2();
  Vec2 mousePos = new Vec2();

  @Override
  public void settings() {
    size(800, 600);
  }

  @Override
  public void setup() {
    pRenderer = new PUIRenderer(this, 10, 5);

    noStroke();
    UIComponent.renderer = pRenderer;

    screenSize.x = width;
    screenSize.y = height;

    nixos = loadImage("nixos.png");
  }

  @Override
  public void draw() {
    background(Color.rgb(64, 64, 64));

    rootPanel.draw(Vec2.origin, screenSize);

    fill(Color.rgb(255, 0, 0));
    rect(drawPos.x, drawPos.y, 40, 40);
  }

  @Override
  public void mousePressed() {
    mousePos.x = mouseX;
    mousePos.y = mouseY;

    if (mouseButton == LEFT) {
      rootPanel.handleClick(mousePos, Vec2.origin, screenSize);
    }
  }

  @Override
  public void windowResized() {
    screenSize.x = width;
    screenSize.y = height;
  }

  public static void main(String[] args) {
    String[] processingArgs = { "pRPG" };
    pRPG sketch = new pRPG();
    PApplet.runSketch(processingArgs, sketch);
  }
}
