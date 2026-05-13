import processing.PGameContext;
import processing.PUIRenderer;
import processing.core.*;
import scene.GameState;
import scene.SceneManager;
import types.Vec2;
import ui.components.UIComponent;

public class pRPG extends PApplet {

  private PUIRenderer renderer;
  private PGameContext gameContext;
  private SceneManager sceneManager;

  private Vec2 screenSize = new Vec2();
  private Vec2 mousePos = new Vec2();

  @Override
  public void settings() {
    size(800, 600);
  }

  @Override
  public void setup() {
    renderer = new PUIRenderer(this, 10, 5);
    UIComponent.renderer = renderer;

    gameContext = new PGameContext(this);

    sceneManager = new SceneManager(renderer, gameContext);
    sceneManager.transition(GameState.MAIN_MENU);

    screenSize.x = width;
    screenSize.y = height;
  }

  @Override
  public void draw() {
    sceneManager.draw(screenSize);
  }

  @Override
  public void mouseClicked() {
    mousePos.x = pmouseX;
    mousePos.y = pmouseY;

    sceneManager.handleClick(mousePos, screenSize);
  }

  @Override
  public void keyPressed() {
    sceneManager.handleKey(keyCode);
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
