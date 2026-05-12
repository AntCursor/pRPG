package scene.scenes;

import assets.AssetManager;
import scene.SceneInf;
import scene.SceneManager;
import ui.UIComponent;
import ui.UIRenderer;

abstract class BaseScene implements SceneInf {
  protected SceneManager sceneManager;
  protected AssetManager<Byte, Object> assetManager;
  protected UIComponent rootPanel;
  protected UIRenderer renderer;

  protected BaseScene(SceneManager manager, UIRenderer renderer, AssetManager<Byte, Object> assets) {
    this.sceneManager = manager;
    this.renderer = renderer;
    this.assetManager = assets;
  }
}
