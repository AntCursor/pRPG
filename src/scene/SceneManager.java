package scene;

import assets.AssetManager;
import scene.scenes.Exploration;
import types.Vec2;
import ui.UIRenderer;

public class SceneManager {
  private SceneInf currentScene;
  private GameState currentState;
  private UIRenderer renderer;

  private AssetManager<Byte, Object> assetManager;

  public SceneManager(UIRenderer renderer) {
    this.renderer = renderer;
    assetManager = new AssetManager<>(renderer);
  }

  public void transition(GameState state) {
    currentState = state;
    switch (state) {
      case EXPLORATION:
        currentScene = new Exploration(this, renderer, assetManager, 0);
        break;
      default:
        return;
    }
    currentScene.setup();
  }

  public GameState getState() {
    return currentState;
  }

  public void draw(Vec2 screenSize) {
    currentScene.draw(screenSize);
  }

  public void handleClick(Vec2 clickPos, Vec2 screenSize) {
    currentScene.handleClick(clickPos, screenSize);
  }

  public void handleKey(int keyCode) {
    currentScene.handleKey(keyCode);
  }
}
