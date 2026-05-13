package character;

import assets.AssetManager;
import assets.TileType;
import core.GameContext;
import grid.Grid;
import grid.GridRenderer;

public class Team {
  public static final int SIZE = 3;
  private static final int XP_PER_LEVEL = 100;
  private static final long MIN_COOLDOWN = 2000;
  private static final long MAX_COOLDOWN = 5000;

  private Character[] members = new Character[SIZE];
  private int count = 0;
  private int level = 1;
  private int xp = 0;
  private final boolean isHeroTeam;

  // Exploration state
  private int x, y;
  private boolean defeated = false;
  private long lastMoveTime = 0;
  private long currentCooldown = MIN_COOLDOWN;
  private TileType sprite = TileType.STONE;

  public Team(boolean isHeroTeam) {
    this.isHeroTeam = isHeroTeam;
  }

  public Team(boolean isHeroTeam, int x, int y) {
    this.isHeroTeam = isHeroTeam;
    this.x = x;
    this.y = y;
  }

  public Team add(Character c) {
    if (count < SIZE) {
      applyLevelStats(c);
      members[count++] = c;
    }
    return this;
  }

  public Character get(int index) {
    return members[index];
  }

  public int size() {
    return count;
  }

  public int getLevel() {
    return level;
  }

  public boolean isHeroTeam() {
    return isHeroTeam;
  }

  public boolean isDefeated() {
    return defeated;
  }

  public void setDefeated() {
    this.defeated = true;
  }

  public boolean allMembersDead() {
    for (int i = 0; i < count; i++)
      if (members[i].isAlive())
        return false;
    return true;
  }

  public void gainXP(int amount) {
    if (!isHeroTeam)
      return;
    xp += amount;
    if (xp >= XP_PER_LEVEL) {
      xp -= XP_PER_LEVEL;
      levelUp();
    }
  }

  // Exploration: autonomous movement
  public void update(GameContext game, Grid grid) {
    if (defeated)
      return;
    if ((game.millis() - lastMoveTime) < currentCooldown)
      return;

    int idx = (int) (game.random() * 4);
    int offX = 0, offY = 0;
    switch (idx) {
      case 0:
        offY = -1;
        break;
      case 1:
        offY = 1;
        break;
      case 2:
        offX = -1;
        break;
      case 3:
        offX = 1;
        break;
    }

    int nextX = x + offX;
    int nextY = y + offY;

    if (nextX >= 0 && nextX < grid.sizeX() &&
        nextY >= 0 && nextY < grid.sizeY() &&
        !TileType.fromId(grid.get(nextX, nextY)).isSolid) {
      x = nextX;
      y = nextY;
    }

    currentCooldown = MIN_COOLDOWN + (long) (game.random() * (MAX_COOLDOWN - MIN_COOLDOWN));
    lastMoveTime = game.millis();
  }

  // Exploration: draw on grid
  public void draw(GridRenderer gridRenderer, AssetManager assets) {
    if (defeated)
      return;
    Object img = assets.get(sprite.id).get();
    gridRenderer.renderOn(img, x, y);
  }

  // Exploration: collision with hero
  public boolean isCollidingWith(Hero hero) {
    return !defeated && hero.getX() == x && hero.getY() == y;
  }

  public Team sprite(TileType sprite) {
    this.sprite = sprite;
    return this;
  }

  private void levelUp() {
    level++;
    for (int i = 0; i < count; i++)
      applyLevelStats(members[i]);
  }

  private void applyLevelStats(Character c) {
    float newMaxHp = level * 50f;
    float newPower = level * 10f;
    c.maxHp(newMaxHp);
    c.power(newPower);
    c.setHp(newMaxHp);
  }
}
