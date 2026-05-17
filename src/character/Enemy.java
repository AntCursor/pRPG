package character;

import assets.AssetManager;
import assets.TileType;
import core.GameContext;
import grid.Grid;
import grid.GridRenderer;

public class Enemy extends Character {
  private static final long MIN_COOLDOWN = 2000;
  private static final long MAX_COOLDOWN = 5000;

  private long currentCooldown = MIN_COOLDOWN;

  public Enemy(String name, int x, int y) {
    super(name, x, y);
    this.maxHp = 80;
    this.hp = 80;
    this.speed = 3;
    this.power = 10;
    this.sprite = TileType.STONE;
  }

  @Override
  public void update(GameContext game, Grid grid) {
    if ((game.millis() - lastActionTime) < currentCooldown)
      return;

    Direction dir = randomDirection(game);
    if (canMove(dir, grid)) {
      switch (dir) {
        case UP:
          y -= 1;
          break;
        case DOWN:
          y += 1;
          break;
        case LEFT:
          x -= 1;
          break;
        case RIGHT:
          x += 1;
          break;
      }
    }
    currentCooldown = MIN_COOLDOWN + (long) (game.random() * (MAX_COOLDOWN - MIN_COOLDOWN));
    lastActionTime = game.millis();
  }

  @Override
  public void draw(GridRenderer gridRenderer, AssetManager assets) {
    Object img = assets.get(sprite.id).get();
    gridRenderer.renderOn(img, x, y);
  }

  private Direction randomDirection(GameContext game) {
    switch ((int) (game.random() * 4)) {
      case 0:
        return Direction.UP;
      case 1:
        return Direction.DOWN;
      case 2:
        return Direction.LEFT;
      default:
        return Direction.RIGHT;
    }
  }

  public Enemy sprite(TileType sprite) {
    this.sprite = sprite;
    return this;
  }
}
