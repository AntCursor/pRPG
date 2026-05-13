package scene.scenes;

import assets.AssetManager;
import assets.TileType;
import core.GameContext;
import scene.SceneManager;
import types.Color;
import types.Vec2;
import ui.UIRenderer;
import ui.components.*;

public class Battle extends BaseScene {

  public Battle(SceneManager manager, UIRenderer renderer, AssetManager assets, GameContext game) {
    super(manager, renderer, assets, game);
  }

  @Override
  public void setup() {
    assetManager.loadTiles(TileType.LEVI_FACE, TileType.CRASH, TileType.BATTLE_BG);

    rootPanel = Panel.root()
        .add(new Image(0, 0, 1, 1).image(() -> assetManager.get(TileType.BATTLE_BG.id).get()))

        // Initiative Bar
        .add(new Panel(0.15f, 0.02f, 0.7f, 0.1f).color(Color.rgba(0, 0, 0, 180))
            .add(new Label(0.5f, 0.2f, 1, 0.4f).text("TURN ORDER").fontColor(Color.rgb(255, 255, 255)))
            .add(new Image(0.1f, 0.6f, 0.06f, 0.5f).image(() -> assetManager.get(TileType.LEVI_FACE.id).get()))
            .add(new Image(0.18f, 0.6f, 0.06f, 0.5f).image(() -> assetManager.get(TileType.CRASH.id).get())))

        // // Enemies
        // .add(createCharacterSlot(0.1f, 0.25f, "Stone Golem", Color.rgb(255, 50, 50),
        // () -> sprEnemy1, 0.85f))
        // .add(createCharacterSlot(0.1f, 0.45f, "Ancient Guardian", Color.rgb(255, 50,
        // 50), () -> sprEnemy2, 0.40f))
        //
        // // Heroes
        // .add(createCharacterSlot(0.6f, 0.35f, "Reia", Color.rgb(51, 255, 80), () ->
        // sprHero1, 0.90f))
        // .add(createCharacterSlot(0.6f, 0.55f, "Shadow", Color.rgb(50, 255, 80), () ->
        // sprHero2, 0.65f))

        // Action Menu
        .add(new Panel(0, 0.8f, 1, 0.2f).color(Color.rgba(15, 15, 20, 240))
            .add(new Label(0.15f, 0.3f, 0.2f, 0.2f).text("PLAYER TURN").fontColor(Color.rgb(255, 215, 0)))
            .add(new Button(0.05f, 0.6f, 0.28f, 0.3f).text("1 - ATTACK").color(Color.rgb(50, 50, 60)))
            .add(new Button(0.36f, 0.6f, 0.28f, 0.3f).text("2 - SKILL").color(Color.rgb(50, 50, 60)))
            .add(new Button(0.67f, 0.6f, 0.28f, 0.3f).text("3 - POTION").color(Color.rgb(50, 50, 60))));
  }

  @Override
  public void draw(Vec2 screenSize) {
    rootPanel.draw(Vec2.origin, screenSize);
  }

  @Override
  public void handleClick(Vec2 clickPos, Vec2 screenSize) {
  }

  @Override
  public void handleKey(int keyCode) {
  }

}
