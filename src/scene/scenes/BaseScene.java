package scene.scenes;

import assets.AssetManager;
import scene.SceneInf;
import scene.SceneManager;
import ui.UIRenderer;
import ui.components.UIComponent;
import core.GameContext;

abstract class BaseScene implements SceneInf {
  protected SceneManager sceneManager;
  protected AssetManager assetManager;

  protected UIComponent rootPanel;
  protected UIRenderer renderer;

  protected GameContext game;

  protected BaseScene(
      SceneManager manager,
      UIRenderer renderer,
      AssetManager assets,
      GameContext game) {
    this.sceneManager = manager;
    this.renderer = renderer;
    this.assetManager = assets;
    this.game = game;
  }

}
