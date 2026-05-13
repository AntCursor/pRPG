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
import scene.battle.BattleState;
import types.Color;
import types.Vec2;
import ui.UIRenderer;
import ui.components.*;

public class Battle extends BaseScene {
  private BattleManager battleManager;
  private Team heroes;
  private Team enemies;

  // Cached display state, refreshed every draw
  private BattleDisplayState display;

  public Battle(
      SceneManager manager,
      UIRenderer renderer,
      AssetManager assets,
      GameContext game,
      Team heroes,
      Team enemies) {
    super(manager, renderer, assets, game);
    this.heroes = heroes;
    this.enemies = enemies;
  }

  @Override
  public void setup() {
    assetManager.loadTiles(TileType.LEVI_FACE, TileType.CRASH, TileType.BATTLE_BG);
    battleManager = new BattleManager(heroes, enemies, game);
    display = battleManager.getDisplayState();
    buildUI();
  }

  private void buildUI() {
    rootPanel = Panel.root()
        // Background
        .add(new Image(0, 0, 1, 1)
            .image(() -> assetManager.get(TileType.BATTLE_BG.id).get()))

        // Initiative bar — shows queue order, highlights current turn
        .add(new Panel(0.15f, 0.02f, 0.7f, 0.1f)
            .color(Color.rgba(0, 0, 0, 180))
            .add(new Label(0.5f, 0.1f, 1f, 0.4f)
                .text("TURN ORDER")
                .fontColor(Color.rgb(255, 255, 255)))
            .add(buildQueueSlot(0, 0.05f))
            .add(buildQueueSlot(1, 0.22f))
            .add(buildQueueSlot(2, 0.39f))
            .add(buildQueueSlot(3, 0.56f))
            .add(buildQueueSlot(4, 0.73f))
            .add(buildQueueSlot(5, 0.90f)))

        // Hero HP bars
        .add(new Panel(0f, 0.65f, 0.5f, 0.15f)
            .color(Color.rgba(0, 0, 0, 150))
            .add(buildHpBar(0, 0.1f))
            .add(buildHpBar(1, 0.4f))
            .add(buildHpBar(2, 0.7f)))

        // Enemy HP bars
        .add(new Panel(0.5f, 0.65f, 0.5f, 0.15f)
            .color(Color.rgba(0, 0, 0, 150))
            .add(buildHpBar(3, 0.1f))
            .add(buildHpBar(4, 0.4f))
            .add(buildHpBar(5, 0.7f)))

        // Last action log
        .add(new Label(0.5f, 0.78f, 0.8f, 0.04f)
            .text(() -> display.lastResult == null ? ""
                : display.lastResult.attackerName + " used " +
                    display.lastResult.action.label + " for " +
                    (int) display.lastResult.value)
            .fontColor(Color.rgb(255, 255, 255)))

        // Action menu
        .add(new Panel(0f, 0.8f, 1f, 0.2f)
            .color(Color.rgba(15, 15, 20, 240))
            .add(new Label(0.15f, 0.3f, 0.2f, 0.2f)
                .text(() -> display.isPlayerTurn ? "YOUR TURN" : "ENEMY TURN")
                .fontColor(Color.rgb(255, 215, 0)))
            .add(new Button(0.05f, 0.6f, 0.28f, 0.3f)
                .text(BattleAction.ATTACK.label)
                .color(Color.rgb(50, 50, 60))
                .action(() -> battleManager.act(BattleAction.ATTACK)))
            .add(new Button(0.36f, 0.6f, 0.28f, 0.3f)
                .text(BattleAction.SKILL.label)
                .color(Color.rgb(50, 50, 60))
                .action(() -> battleManager.act(BattleAction.SKILL)))
            .add(new Button(0.67f, 0.6f, 0.28f, 0.3f)
                .text(BattleAction.POTION.label)
                .color(Color.rgb(50, 50, 60))
                .action(() -> battleManager.act(BattleAction.POTION))));
  }

  // Queue slot: name label, colored by hero/enemy, dimmed if dead
  private Panel buildQueueSlot(int queueIndex, float x) {
    return (Panel) new Panel(x, 0.5f, 0.14f, 0.5f)
        .color(Color.rgba(0, 0, 0, 0))
        .add(new Label(0.5f, 0.5f, 1f, 1f)
            .text(() -> display.isAlive[queueIndex] ? display.names[queueIndex] : "---")
            .fontColor(Color.rgb(200, 187, 21)));
  }

  // HP bar reading from display snapshot
  private ProgressBar buildHpBar(int queueIndex, float y) {
    return (ProgressBar) new ProgressBar(0.05f, y, 0.9f, 0.25f)
        .value(() -> display.hpRatios[queueIndex])
        .text(() -> display.names[queueIndex]);
  }

  @Override
  public void draw(Vec2 screenSize) {
    battleManager.update();
    display = battleManager.getDisplayState();

    if (display.state == BattleState.BATTLE_WON) {
      sceneManager.transition(GameState.EXPLORATION);
      return;
    }
    if (display.state == BattleState.BATTLE_LOST) {
      sceneManager.transition(GameState.GAMEOVER);
      return;
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
