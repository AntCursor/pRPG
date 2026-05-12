package scene.scenes;

import assets.AssetManager;
import assets.TileType;
import scene.SceneManager;
import types.Vec2;
import ui.UIRenderer;

public class MainMenu extends BaseScene {

  public MainMenu(SceneManager manager, UIRenderer renderer, AssetManager<Byte, Object> assets) {
    super(manager, renderer, assets);
  }

  @Override
  public void setup() {
    loadTiles();

  }

  @Override
  public void draw(Vec2 screenSize) {

  }

  @Override
  public void handleClick(Vec2 clickPos, Vec2 screenSize) {
  }

  @Override
  public void handleKey(int keyCode) {
  }

}
