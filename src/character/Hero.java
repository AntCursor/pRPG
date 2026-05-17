package character;

import assets.AssetManager;
import assets.TileType;
import core.GameContext;
import grid.Grid;
import grid.GridRenderer;

public class Hero extends Character {
  private Direction pendingDirection = null;

  public Hero(String name, int x, int y) {
    super(name, x, y);
    this.maxHp = 100;
    this.hp = 100;
    this.speed = 5;
    this.power = 15;
    this.cooldown = 500;
    this.sprite = TileType.MAN;
  }

  public void setPosition(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void resetHp() {
    this.hp = this.maxHp;
  }

  public void queueDirection(int keyCode) {
    switch (keyCode) {
      case 'W':
      case 'w':
        pendingDirection = Direction.UP;
        break;
      case 'S':
      case 's':
        pendingDirection = Direction.DOWN;
        break;
      case 'A':
      case 'a':
        pendingDirection = Direction.LEFT;
        break;
      case 'D':
      case 'd':
        pendingDirection = Direction.RIGHT;
        break;
    }
  }

  @Override
  public void update(GameContext game, Grid grid) {
    if (pendingDirection == null)
      return;
    Direction dir = pendingDirection;
    pendingDirection = null;

    if ((game.millis() - lastActionTime) < cooldown)
      return;
    if (!canMove(dir, grid))
      return;

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
    lastActionTime = game.millis();
  }

  @Override
  public void draw(GridRenderer gridRenderer, AssetManager assets) {
    Object img = assets.get(sprite.id).get();
    gridRenderer.renderOn(img, x, y);
  }

  public Hero sprite(TileType sprite) {
    this.sprite = sprite;
    return this;
  }
}
