package scene.scenes;

import assets.AssetManager;
import assets.TileType;
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
    private Team heroes;

    private float noiseScale = 0.15f;
    private long seed;

    public Exploration(
            SceneManager manager,
            UIRenderer renderer,
            AssetManager assets,
            GameContext game,
            Hero hero,
            Team[] enemyTeams,
            long seed) {
        super(manager, renderer, assets, game);
        this.hero = hero;
        this.enemyTeams = enemyTeams;
        this.heroes = manager.getHeroes();
        this.seed = seed;
    }

    @Override
    public void setup() {
        assetManager.loadTiles(
                TileType.DARK_GRASS, TileType.GRASS, TileType.SAND,
                TileType.STONE, TileType.MAN, TileType.OGRE,
                TileType.ENEMAN, TileType.MIKU);

        grid = new Grid(20, 15);
        worldgen(grid);

        gridRenderer = new GridRenderer(renderer, grid,
                (ImageMap) (id -> assetManager.get(id).get()));

        hero.setPosition(1, 1);

        buildUI();
    }

    private void buildUI() {

        rootPanel = Panel.root()
                .add(new Panel(0f, 0f, 1f, 0.06f)
                        .color(Color.rgba(10, 10, 26, 210))

                        .add(new Label(0.08f, 0.5f, 0.14f, 0.8f)
                                .text(() -> "LVL " + heroes.getLevel())
                                .fontColor(Color.rgb(255, 215, 0)))

                        .add(new ProgressBar(0.22f, 0.2f, 0.3f, 0.6f)
                                .value(() -> hero.getHp() / hero.getMaxHp())
                                .text(() -> (int) hero.getHp() + "/" + (int) hero.getMaxHp() + " HP"))

                        .add(new Label(0.65f, 0.5f, 0.2f, 0.8f)
                                .text(() -> "Enemies: " + countRemainingEnemies() + "/" + enemyTeams.length)
                                .fontColor(Color.rgb(200, 80, 80)))

                        .add(new Label(0.88f, 0.5f, 0.2f, 0.7f)
                                .text("WASD to move")
                                .fontColor(Color.rgb(120, 120, 150))));
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

        for (int i = 0; i < enemyTeams.length; i++) {
            enemyTeams[i].draw(gridRenderer, assetManager);
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
        rootPanel.handleClick(clickPos, Vec2.origin, screenSize);
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
        if (countRemainingEnemies() == 0)
            sceneManager.transition(GameState.WIN);
    }

    private int countRemainingEnemies() {
        int count = 0;
        for (int i = 0; i < enemyTeams.length; i++)
            if (!enemyTeams[i].isDefeated())
                count++;
        return count;
    }

    private void worldgen(Grid grid) {
        game.seedNoise(seed);
        game.seedRandom(seed);

        int[][] spawnPoints = { { 5, 3 }, { 10, 2 }, { 15, 5 }, { 8, 10 }, { 17, 12 } };

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
        for (int[] sp : spawnPoints)
            grid.set(sp[0], sp[1], (byte) 1);
    }
}
