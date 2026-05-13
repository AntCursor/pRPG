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
import types.Color;
import types.Vec2;
import ui.UIRenderer;
import ui.components.*;

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

    rootPanel = Panel.root()

        // ── Fundo escuro do HUD (faixa direita 20%) ──────────────
        .add(new Panel(0.8f, 0.0f, 0.2f, 1.0f)
            .color(Color.rgba(10, 10, 26, 230)))

        // Separador esquerdo do HUD
        .add(new Panel(0.8f, 0.0f, 0.002f, 1.0f)
            .color(Color.rgba(221, 196, 74, 100)))

        // ── Título ───────────────────────────────────────────────
        .add(new Label(0.9f, 0.04f, 0.18f, 0.035f)
            .text("EQUIPE HERÓI")
            .fontColor(Color.rgb(221, 196, 74)))

        .add(new Label(0.9f, 0.08f, 0.18f, 0.03f)
            .text(() -> "Poder: " + hero.getPower())
            .fontColor(Color.rgb(180, 180, 220)))

        // ── XP bar ───────────────────────────────────────────────
        .add(new ProgressBar(0.81f, 0.115f, 0.17f, 0.014f)
            .value(() -> 0.3f) // TODO: xp real
            .text(() -> "XP 30/100"))

        // ── Membro 0 ─────────────────────────────────────────────
        .add(new Label(0.9f, 0.17f, 0.18f, 0.03f)
            .text(() -> hero.getName())
            .fontColor(Color.rgb(230, 230, 240)))

        .add(new ProgressBar(0.81f, 0.2f, 0.17f, 0.018f)
            .value(() -> hero.getHp() / hero.getMaxHp())
            .text(() -> (int) hero.getHp() + "/" + (int) hero.getMaxHp() + " HP"))

        // ── Membro 1 ─────────────────────────────────────────────
        .add(new Label(0.9f, 0.25f, 0.18f, 0.03f)
            .text("Brynn")
            .fontColor(Color.rgb(230, 230, 240)))

        .add(new ProgressBar(0.81f, 0.28f, 0.17f, 0.018f)
            .value(() -> 0.75f)
            .text("75/100 HP"))

        // ── Membro 2 ─────────────────────────────────────────────
        .add(new Label(0.9f, 0.33f, 0.18f, 0.03f)
            .text("Cael")
            .fontColor(Color.rgb(230, 230, 240)))

        .add(new ProgressBar(0.81f, 0.36f, 0.17f, 0.018f)
            .value(() -> 0.4f)
            .text("40/100 HP"))

        // ── Separador ────────────────────────────────────────────
        .add(new Panel(0.81f, 0.415f, 0.17f, 0.002f)
            .color(Color.rgba(60, 60, 100, 180)))

        // ── Inimigos restantes ───────────────────────────────────
        .add(new Label(0.9f, 0.435f, 0.18f, 0.03f)
            .text("INIMIGOS")
            .fontColor(Color.rgb(221, 196, 74)))

        .add(new Label(0.9f, 0.47f, 0.18f, 0.05f)
            .text("4/4") // TODO: dinâmico
            .fontColor(Color.rgb(200, 80, 80)))

        // ── Separador ────────────────────────────────────────────
        .add(new Panel(0.81f, 0.535f, 0.17f, 0.002f)
            .color(Color.rgba(60, 60, 100, 180)))

        // ── Cooldown do herói ────────────────────────────────────
        .add(new Label(0.9f, 0.555f, 0.18f, 0.03f)
            .text("COOLDOWN")
            .fontColor(Color.rgb(221, 196, 74)))

        .add(new ProgressBar(0.81f, 0.588f, 0.17f, 0.014f)
            .value(() -> {
              // 0.0 = no cooldown; 1.0 = pronto
              // lastActionTime fica em Character, expor via getter
              return Math.min(1f, (game.millis() - 0L) / 2000f);
            })
            .text(() -> {
              long elapsed = game.millis();
              return elapsed >= 2000 ? "Pronto" : String.format("%.1fs", (2000 - elapsed) / 1000f);
            }))

        // ── Separador ────────────────────────────────────────────
        .add(new Panel(0.81f, 0.88f, 0.17f, 0.002f)
            .color(Color.rgba(60, 60, 100, 120)))

        // ── Dicas de controle ────────────────────────────────────
        .add(new Button(0.81f, 0.895f, 0.18f, 0.033f)
            .text("WASD - mover")
            .color(Color.rgba(0, 0, 0, 0))
            .fontColor(Color.rgba(120, 120, 150, 200)))

        .add(new Button(0.81f, 0.932f, 0.18f, 0.033f)
            .text("Encontre inimigos!")
            .color(Color.rgba(0, 0, 0, 0))
            .fontColor(Color.rgba(120, 120, 150, 200)));
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

    rootPanel.draw(Vec2.origin, screenSize);
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
