package scene.scenes;

import assets.AssetManager;
import assets.TileType;
import character.Team;
import core.GameContext;
import scene.GameState;
import scene.SceneManager;
import scene.battle.BattleAction;
import scene.battle.BattleDisplayState;
import scene.battle.BattleManager;
import types.Color;
import types.Vec2;
import ui.UIRenderer;
import ui.components.*;

public class Battle extends BaseScene {
  private BattleManager battleManager;
  private Team heroes;
  private Team enemyTeam;
  private BattleDisplayState display;

  public Battle(
      SceneManager manager,
      UIRenderer renderer,
      AssetManager assets,
      GameContext game,
      Team heroes,
      Team enemyTeam) {
    super(manager, renderer, assets, game);
    this.heroes = heroes;
    this.enemyTeam = enemyTeam;
  }

  @Override
  public void setup() {
    assetManager.loadTiles(TileType.BATTLE_BG);
    battleManager = new BattleManager(heroes, enemyTeam, game);
    display = battleManager.getDisplayState();
    buildUI();
  }

  private void buildUI() {
    rootPanel = Panel.root()
        .add(new Image(0, 0, 1, 1)
            .image(() -> assetManager.get(TileType.BATTLE_BG.id).get()))

        .add(new Panel(0.1f, 0.02f, 0.8f, 0.12f)
            .color(Color.rgba(0, 0, 0, 200))
            .add(new Label(0.5f, 0.15f, 1f, 0.3f)
                .text("TURN ORDER")
                .fontColor(Color.rgb(255, 215, 0)))
            .add(buildQueueSlot(0, 0.03f))
            .add(buildQueueSlot(1, 0.19f))
            .add(buildQueueSlot(2, 0.36f))
            .add(buildQueueSlot(3, 0.53f))
            .add(buildQueueSlot(4, 0.70f))
            .add(buildQueueSlot(5, 0.87f)))

        .add(new Panel(0f, 0.63f, 0.5f, 0.17f)
            .color(Color.rgba(0, 0, 30, 200))
            .add(new Label(0.25f, 0.08f, 0.5f, 0.2f)
                .text("HEROES")
                .fontColor(Color.rgb(100, 200, 255)))
            .add(buildHpBar(0, 0.28f))
            .add(buildHpBar(1, 0.55f))
            .add(buildHpBar(2, 0.80f)))

        .add(new Panel(0.5f, 0.63f, 0.5f, 0.17f)
            .color(Color.rgba(30, 0, 0, 200))
            .add(new Label(0.75f, 0.08f, 0.5f, 0.2f)
                .text("ENEMIES")
                .fontColor(Color.rgb(255, 100, 100)))
            .add(buildHpBar(3, 0.28f))
            .add(buildHpBar(4, 0.55f))
            .add(buildHpBar(5, 0.80f)))

        .add(new Label(0.5f, 0.79f, 0.9f, 0.04f)
            .text(() -> display.lastResult == null ? "Battle started!"
                : display.lastResult.attackerName + " → "
                    + display.lastResult.action.label
                    + " → " + display.lastResult.targetName
                    + " (" + (int) display.lastResult.value + ")")
            .fontColor(Color.rgb(220, 220, 220)))

        .add(new Panel(0f, 0.82f, 1f, 0.18f)
            .color(Color.rgba(10, 10, 20, 245))
            .add(new Label(0.5f, 0.2f, 0.4f, 0.35f)
                .text(() -> display.isPlayerTurn ? "▶ YOUR TURN" : "⏳ ENEMY TURN")
                .fontColor(() -> display.isPlayerTurn
                    ? Color.rgb(100, 255, 100)
                    : Color.rgb(255, 100, 100)))
            .add(new Button(0.05f, 0.55f, 0.28f, 0.38f)
                .text(BattleAction.ATTACK.label)
                .color(Color.rgb(60, 30, 30))
                .fontColor(Color.rgb(255, 200, 200))
                .action(() -> battleManager.act(BattleAction.ATTACK)))
            .add(new Button(0.37f, 0.55f, 0.28f, 0.38f)
                .text(BattleAction.SKILL.label)
                .color(Color.rgb(30, 30, 60))
                .fontColor(Color.rgb(200, 200, 255))
                .action(() -> battleManager.act(BattleAction.SKILL)))
            .add(new Button(0.68f, 0.55f, 0.28f, 0.38f)
                .text(BattleAction.POTION.label)
                .color(Color.rgb(30, 60, 30))
                .fontColor(Color.rgb(200, 255, 200))
                .action(() -> battleManager.act(BattleAction.POTION))));
  }

  private Panel buildQueueSlot(int idx, float x) {
    return (Panel) new Panel(x, 0.35f, 0.14f, 0.65f)
        .color(Color.rgba(0, 0, 0, 0))
        .add(new Image(0.1f, 0.0f, 0.8f, 0.7f)
            .image(() -> {
              TileType tile = display.imgs[idx];
              if (tile == null)
                return null;
              return assetManager.get(tile.id).get();
            }))
        .add(new Label(0.5f, 0.75f, 1f, 0.3f)
            .text(() -> display.isAlive[idx] ? display.names[idx] : "✝")
            .fontColor(() -> {
              if (!display.isAlive[idx])
                return Color.rgb(80, 80, 80);
              if (idx == display.currentTurnIndex)
                return Color.rgb(255, 215, 0);
              return display.isHero[idx]
                  ? Color.rgb(100, 200, 255)
                  : Color.rgb(255, 100, 100);
            }));
  }

  private ProgressBar buildHpBar(int idx, float y) {
    return (ProgressBar) new ProgressBar(0.05f, y, 0.9f, 0.2f)
        .value(() -> Math.max(0, display.hpRatios[idx]))
        .text(() -> display.isAlive[idx]
            ? display.names[idx]
            : display.names[idx] + " (dead)");
  }

  @Override
  public void draw(Vec2 screenSize) {
    battleManager.update();
    display = battleManager.getDisplayState();

    switch (display.state) {
      case BATTLE_WON:
        enemyTeam.setDefeated();
        sceneManager.transition(GameState.EXPLORATION);
        return;
      case BATTLE_LOST:
        sceneManager.transition(GameState.GAMEOVER);
        return;
      default:
        break;
    }

    rootPanel.draw(Vec2.origin, screenSize);
  }

  @Override
  public void handleClick(Vec2 clickPos, Vec2 screenSize) {
    if (display.isPlayerTurn)
      rootPanel.handleClick(clickPos, Vec2.origin, screenSize);
  }

  @Override
  public void handleKey(int keyCode) {
    if (!display.isPlayerTurn)
      return;
    switch (keyCode) {
      case '1':
        battleManager.act(BattleAction.ATTACK);
        break;
      case '2':
        battleManager.act(BattleAction.SKILL);
        break;
      case '3':
        battleManager.act(BattleAction.POTION);
        break;
    }
  }
}
