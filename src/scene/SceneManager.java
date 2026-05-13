package scene;

import assets.AssetManager;
import core.GameContext;
import scene.scenes.*;
import types.Vec2;
import ui.UIRenderer;

public class SceneManager {
  private SceneInf currentScene;
  private GameState currentState;
  private UIRenderer renderer;
  private GameContext game;

  private AssetManager assetManager;

  public SceneManager(UIRenderer renderer, GameContext game) {
    this.renderer = renderer;
    this.game = game;
    assetManager = new AssetManager(game);
  }

  public void transition(GameState state) {
    currentState = state;
    switch (state) {
      case EXPLORATION:
        currentScene = new Exploration(this, renderer, assetManager, game, 0);
        break;
      case MAIN_MENU:
        currentScene = new MainMenu(this, renderer, assetManager, game);
        break;
      case BATTLE:
        currentScene = new Battle(this, renderer, assetManager, game);
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
