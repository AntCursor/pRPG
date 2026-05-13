package scene;

import assets.AssetManager;
import assets.TileType;
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

  public SceneManager(UIRenderer renderer, GameContext game) {
    this.renderer = renderer;
    this.game = game;
    assetManager = new AssetManager(game);

    leadHero = new Hero("Levi", 1, 1).sprite(TileType.LEVI);

    heroes = new Team(true)
        .add(leadHero)
        .add(new Hero("Crash Bandicoot", 1, 2).sprite(TileType.CRASH))
        .add(new Hero("Bonzi Buddy", 1, 3).sprite(TileType.BONZI));
  }

  public void transition(GameState state) {
    currentState = state;
    switch (state) {
      case EXPLORATION:
        currentScene = new Exploration(this, renderer, assetManager, game, leadHero, 0);
        break;
      case MAIN_MENU:
        currentScene = new MainMenu(this, renderer, assetManager, game);
        break;
      case GAMEOVER:
        currentScene = new GameOver(this, renderer, assetManager, game);
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
    currentScene.handleKey(keyCode);
  }
}
