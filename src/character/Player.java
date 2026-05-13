package character;

import assets.AssetManager;
import core.GameContext;
import grid.Grid;
import ui.UIRenderer;

public class Player extends Character {

  public Player(String name, int x, int y) {
    super(name, x, y);
  }

  @Override
  public void update(GameContext game, Grid grid) {

  }

  @Override
  public void draw(UIRenderer renderer, AssetManager assets, Grid grid) {
  }

}
