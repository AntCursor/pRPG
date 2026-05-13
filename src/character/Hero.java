package character;

import assets.AssetManager;
import core.GameContext;
import grid.Grid;
import grid.GridRenderer;
import ui.UIRenderer;

public class Hero extends Character {

  public Hero(String name, int x, int y) {
    super(name, x, y);
  }

  @Override
  public void update(GameContext game, Grid grid) {

  }

  @Override
  public void draw(UIRenderer renderer, AssetManager assets, GridRenderer gRenderer) {
  }

}
