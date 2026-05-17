package scene.scenes;

import assets.AssetManager;
import core.GameContext;
import scene.SceneManager;
import types.Color;
import types.Vec2;
import ui.UIRenderer;
import ui.components.*;

public class WinScreen extends BaseScene {

  public WinScreen(SceneManager manager, UIRenderer renderer, AssetManager assets, GameContext game) {
    super(manager, renderer, assets, game);
  }

  @Override
  public void setup() {
    rootPanel = Panel.root()
        .add(new Panel(0, 0, 1, 1)
            .color(Color.rgb(10, 10, 30)))

        .add(new Label(0.5f, 0.3f, 0.8f, 0.12f)
            .text("YOU WIN!")
            .fontColor(Color.rgb(255, 215, 0)))

        .add(new Label(0.5f, 0.45f, 0.7f, 0.06f)
            .text("All enemies have been defeated. The world is saved!")
            .fontColor(Color.rgb(200, 200, 200)))

        .add(new Button(0.35f, 0.6f, 0.3f, 0.07f)
            .text("BACK TO MENU")
            .color(Color.rgb(50, 150, 50))
            .fontColor(Color.rgb(255, 255, 255))
            .action(() -> sceneManager.restart()));
  }

  @Override
  public void draw(Vec2 screenSize) {
    rootPanel.draw(Vec2.origin, screenSize);
  }

  @Override
  public void handleClick(Vec2 clickPos, Vec2 screenSize) {
    rootPanel.handleClick(clickPos, Vec2.origin, screenSize);
  }

  @Override
  public void handleKey(int keyCode) {
  }
}
