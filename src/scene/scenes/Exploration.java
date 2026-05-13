package scene.scenes;

import assets.AssetManager;
import assets.TileType;
import core.GameContext;
import grid.Grid;
import grid.GridRenderer;
import grid.GridRenderer.ImageMap;
import scene.SceneManager;
import types.Vec2;
import ui.UIRenderer;

public class Exploration extends BaseScene {

  private GridRenderer gridRenderer;
  private Grid grid;

  private float noiseScale = 0.15f;
  private long seed;

  public Exploration(
      SceneManager manager,
      UIRenderer renderer,
      AssetManager assets,
      GameContext game,
      long seed) {
    super(manager, renderer, assets, game);
    this.seed = seed;
  }

  @Override
  public void setup() {
    loadTiles(TileType.DARK_GRASS, TileType.GRASS, TileType.SAND, TileType.STONE);

    grid = new Grid(20, 15);
    worldgen(grid);

    this.gridRenderer = new GridRenderer(renderer, grid,
        (ImageMap) (id -> assetManager.get(id).get()));
  }

  @Override
  public void draw(Vec2 screenSize) {
    gridRenderer.render();
  }

  @Override
  public void handleClick(Vec2 clickPos, Vec2 screenSize) {
  }

  @Override
  public void handleKey(int keyCode) {
  }

  private void worldgen(Grid grid) {
    game.seedNoise(seed);
    game.seedRandom(seed);

    grid.forEach((x, y) -> {
      float nos = game.noise(x * noiseScale, y * noiseScale);
      byte type;
      if (nos < 0.4)
        type = 0;
      else if (nos < 0.6)
        type = 1;
      else
        type = 2;

      if (game.random() > 0.9)
        type = 3;

      grid.set(x, y, type);
    });
  }
}
