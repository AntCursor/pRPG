package scene;

import assets.AssetManager;
import assets.TileType;
import character.Enemy;
import character.Hero;
import character.Team;
import core.GameContext;
import scene.scenes.*;
import types.Vec2;
import ui.UIRenderer;

public class SceneManager {
  private SceneInf currentScene;
  private GameState currentState;
  private UIRenderer renderer;
  private GameContext game;
  private AssetManager assetManager;

  private Team heroes;
  private Hero leadHero;
  private Team[] enemyTeams;

  private static final int[][] SPAWN_POINTS = {
      { 5, 3 },
      { 10, 2 },
      { 15, 5 },
      { 8, 10 },
      { 17, 12 }
  };

  public SceneManager(UIRenderer renderer, GameContext game) {
    this.renderer = renderer;
    this.game = game;
    assetManager = new AssetManager(game);
    initTeams();
  }

  private void initTeams() {
    assetManager.loadTiles(
        TileType.CRASH_FACE, TileType.BONZI, TileType.LEVI_FACE,
        TileType.OGRE, TileType.MIKU, TileType.ENEMAN);
    leadHero = new Hero("Levi", 1, 1).sprite(TileType.LEVI_FACE);

    heroes = new Team(true)
        .add(leadHero)
        .add(new Hero("Crash", 1, 2).sprite(TileType.CRASH_FACE))
        .add(new Hero("Bonzi", 1, 3).sprite(TileType.BONZI));

    enemyTeams = new Team[SPAWN_POINTS.length];
    for (int i = 0; i < SPAWN_POINTS.length; i++) {
      int level = (i / 2) + 1;
      int sx = SPAWN_POINTS[i][0];
      int sy = SPAWN_POINTS[i][1];
      enemyTeams[i] = new Team(false, sx, sy, level)
          .add(new Enemy("Goblin Alfa", sx, sy).sprite(TileType.OGRE))
          .add(new Enemy("Miku", sx, sy).sprite(TileType.MIKU))
          .add(new Enemy("Goblin Beta", sx, sy).sprite(TileType.OGRE))
          .sprite(TileType.ENEMAN);
    }
  }

  public void transition(GameState state) {
    currentState = state;
    switch (state) {
      case MAIN_MENU:
        currentScene = new MainMenu(this, renderer, assetManager, game);
        break;
      case EXPLORATION:
        currentScene = new Exploration(this, renderer, assetManager, game, leadHero, enemyTeams, 0);
        break;
      case GAMEOVER:
        currentScene = new GameOver(this, renderer, assetManager, game);
        break;
      case WIN:
        currentScene = new WinScreen(this, renderer, assetManager, game);
        break;
      default:
        return;
    }
    currentScene.setup();
  }

  public void startBattle(Team enemyTeam) {
    currentState = GameState.BATTLE;
    currentScene = new Battle(this, renderer, assetManager, game, heroes, enemyTeam);
    currentScene.setup();
  }

  public void restart() {
    initTeams();
    transition(GameState.MAIN_MENU);
  }

  public Team getHeroes() {
    return heroes;
  }

  public GameState getState() {
    return currentState;
  }

  public void draw(Vec2 screenSize) {
    currentScene.draw(screenSize);
  }

  public void handleClick(Vec2 clickPos, Vec2 screenSize) {
    currentScene.handleClick(clickPos, screenSize);
  }

  public void handleKey(int keyCode) {
    if (keyCode == 'R')
      this.restart();
    else
      currentScene.handleKey(keyCode);
  }
}
