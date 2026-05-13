package character;

import assets.AssetManager;
import assets.TileType;
import core.GameContext;
import grid.Grid;
import grid.GridRenderer;

public abstract class Character {
    protected String name;
    protected int x, y;

    protected float maxHp = 100;
    protected float hp = maxHp;
    protected float speed = 1;
    protected float power = 10;

    protected long lastActionTime = 0;
    protected long cooldown = 2000;

    protected TileType sprite;

    public Character(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public abstract void update(GameContext game, Grid grid);

    public abstract void draw(GridRenderer GridRenderer, AssetManager assets);

    protected boolean canMove(Direction dir, Grid grid) {
        int offX = 0;
        int offY = 0;
        switch (dir) {
            case LEFT:
                offX = -1;
                break;
            case RIGHT:
                offX = 1;
                break;
            case UP:
                offY = -1;
                break;
            case DOWN:
                offY = 1;
                break;
        }

        int nextX = x + offX;
        int nextY = y + offY;
        if (nextX == grid.sizeX() || nextX < 0 || nextY == grid.sizeY() || nextY < 0)
            return false;

        return !TileType.fromId(grid.get(nextX, nextY)).isSolid;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Character maxHp(float maxHp) {
        this.maxHp = maxHp;
        return this;
    }

    public Character speed(float speed) {
        this.speed = speed;
        return this;
    }

    public Character power(float power) {
        this.power = power;
        return this;
    }

    public Character cooldown(long cooldown) {
        this.cooldown = cooldown;
        return this;
    }

    public void setHp(float hp) {
        this.hp = Math.min(hp, maxHp);
    }

    public void addHp(float heal) {
        hp = Math.min(hp + heal, maxHp);
    }

    public void lostHp(float damage) {
        hp = Math.max(hp - damage, 0);
    }

    public float getHp() {
        return hp;
    }

    public float getMaxHp() {
        return maxHp;
    }

    public float getSpeed() {
        return speed;
    }

    public String getName() {
        return name;
    }

    public float getPower() {
        return power;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public TileType getSprite() {
        return sprite;
    }

    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }
}
