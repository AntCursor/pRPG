package character;

import assets.AssetManager;
import assets.TileType;
import core.GameContext;
import grid.Grid;
import grid.GridRenderer;

public class Enemy extends Character {

  private static final long MIN_COOLDOWN = 2000;
  private static final long MAX_COOLDOWN = 5000;

  private TileType sprite = TileType.STONE;
  private long currentCooldown;

  public Enemy(String name, int x, int y) {
    super(name, x, y);
    this.maxHp    = 80;
    this.hp       = 80;
    this.speed    = 3;
    this.power    = 10;
    this.currentCooldown = MIN_COOLDOWN;
    this.cooldown        = MIN_COOLDOWN;
  }

  @Override
  public void update(GameContext game, Grid grid) {
    boolean cooldownOk = (game.millis() - lastActionTime) >= currentCooldown;
    if (!cooldownOk) return;

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
    currentCooldown = resortCooldown(game);
    lastActionTime  = game.millis();
  }

  @Override
  public void draw(GridRenderer gridRenderer, AssetManager assets) {
    Object img = assets.get(sprite.id)
        .orElseThrow(() -> new IllegalStateException(
            "Sprite do inimigo não carregado: " + sprite.path));

    gridRenderer.renderOn(img, x, y);
  }

  private Direction randomDirection(GameContext game) {
    int idx = (int)(game.random() * 4);
    switch (idx) {
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

  private long resortCooldown(GameContext game) {
    long range = MAX_COOLDOWN - MIN_COOLDOWN;          //3000
    return MIN_COOLDOWN + (long)(game.random() * range); //[2000, 5000)
  }

  public Enemy sprite(TileType sprite) {
    this.sprite = sprite;
    return this;
  }
}
