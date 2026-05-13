package scene.scenes;

import assets.AssetManager;
import assets.TileType;
import character.Enemy;
import character.Hero;
import character.Team;
import core.GameContext;
import grid.Grid;
import grid.GridRenderer;
import grid.GridRenderer.ImageMap;
import scene.GameState;
import scene.SceneManager;
import types.Vec2;
import ui.UIRenderer;

public class Exploration extends BaseScene {

  private GridRenderer gridRenderer;
  private Grid grid;

  private Hero hero;
  private Team[] enemyTeams;

  private float noiseScale = 0.15f;
  private long seed;

  private int[][] spawnPoints = {
      { 5, 3 },
      { 10, 2 },
      { 15, 5 },
      { 8, 10 },
      { 17, 12 }
  };

  public Exploration(
      SceneManager manager,
      UIRenderer renderer,
      AssetManager assets,
      GameContext game,
      Hero hero,
      long seed) {
    super(manager, renderer, assets, game);
    this.hero = hero;
    this.seed = seed;
  }

  @Override
  public void setup() {
    assetManager.loadTiles(TileType.DARK_GRASS, TileType.GRASS, TileType.SAND,
        TileType.STONE, TileType.MAN, TileType.OGRE, TileType.ENEMAN, TileType.MIKU);

    grid = new Grid(20, 15);
    worldgen(grid);

    gridRenderer = new GridRenderer(renderer, grid,
        (ImageMap) (id -> assetManager.get(id).get()));

    hero.setPosition(1, 1);

    enemyTeams = new Team[spawnPoints.length];
    for (int i = 0; i < spawnPoints.length; i++) {
      enemyTeams[i] = new Team(false, spawnPoints[i][0], spawnPoints[i][1])
          .add(new Enemy("Goblin A", spawnPoints[i][0], spawnPoints[i][1]).sprite(TileType.OGRE))
          .add(new Enemy("Goblin B", spawnPoints[i][0], spawnPoints[i][1]).sprite(TileType.MIKU))
          .add(new Enemy("Goblin C", spawnPoints[i][0], spawnPoints[i][1]).sprite(TileType.OGRE))
          .sprite(TileType.ENEMAN);
    }
  }

  @Override
  public void draw(Vec2 screenSize) {
    hero.update(game, grid);

    for (int i = 0; i < enemyTeams.length; i++) {
      if (!enemyTeams[i].isDefeated())
        enemyTeams[i].update(game, grid);
    }

    checkBattleTrigger();
    checkVictory();

    gridRenderer.render();

    for (Team team : enemyTeams) {
      team.draw(gridRenderer, assetManager);
    }

    gridRenderer.renderOn(assetManager.get(TileType.MAN.id).get(), hero.getX(), hero.getY());
  }

  @Override
  public void handleKey(int keyCode) {
    hero.queueDirection(keyCode);
  }

  @Override
  public void handleClick(Vec2 clickPos, Vec2 screenSize) {
  }

  private void checkBattleTrigger() {
    for (int i = 0; i < enemyTeams.length; i++) {
      if (enemyTeams[i].isCollidingWith(hero)) {
        sceneManager.startBattle(enemyTeams[i]);
        return;
      }
    }
  }

  private void checkVictory() {
    for (int i = 0; i < enemyTeams.length; i++) {
      if (!enemyTeams[i].isDefeated())
        return;
    }
    sceneManager.transition(GameState.MAIN_MENU);
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

    grid.set(1, 1, (byte) 1);
    for (int i = 0; i < spawnPoints.length; i++) {
      grid.set(spawnPoints[i][0], spawnPoints[i][1], (byte) 1);
    }
  }
}
