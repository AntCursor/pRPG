package scene.scenes;

import assets.AssetManager;
import grid.Grid;
import grid.GridRenderer;
import grid.GridRenderer.ImageMap;
import scene.SceneInf;
import scene.SceneManager;
import types.Vec2;
import ui.UIComponent;
import ui.UIRenderer;

public class Exploration implements SceneInf {
  private SceneManager sceneManager;
  private AssetManager assetManager;

  private GridRenderer gridRenderer;
  private Grid grid;

  private UIComponent rootPanel;
  private UIRenderer renderer;

  public Exploration(SceneManager manager, UIRenderer renderer, AssetManager assets) {
    this.sceneManager = manager;
    this.renderer = renderer;
    this.assetManager = assets;
  }

  ImageMap textureMap = textId -> {

  };

  @Override
  public void setup() {
    grid = new Grid(20, 15);
    // this.gridRenderer = new GridRenderer(renderer, grid, map);
  }

  @Override
  public void draw(Vec2 screenSize) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'draw'");
  }

  @Override
  public void handleClick(Vec2 clickPos, Vec2 screenSize) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'handleClick'");
  }

  @Override
  public void handleKey(int keyCode) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'handleKey'");
  }
}
