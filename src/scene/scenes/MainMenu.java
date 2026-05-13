package scene.scenes;

import assets.AssetManager;
import assets.TileType;
import core.GameContext;
import scene.GameState;
import scene.SceneManager;
import types.Color;
import types.Vec2;
import ui.UIRenderer;
import ui.components.*;

public class MainMenu extends BaseScene {

  public MainMenu(
      SceneManager manager,
      UIRenderer renderer,
      AssetManager assets,
      GameContext game) {
    super(manager, renderer, assets, game);
  }

  @Override
  public void setup() {
    loadTiles(TileType.TITLE, TileType.MENU_BACK);

    Object imgFundo = assetManager.get(TileType.MENU_BACK.id).get();
    Object imgTitulo = assetManager.get(TileType.TITLE.id).get();

    rootPanel = Panel.root()
        // Imagem de fundo
        .add(
            new Image(0.0f, 0.0f, 1.0f, 1.0f)
                .image(() -> imgFundo))

        // Título como imagem
        .add(
            new Image(0.38f, 0.f, 0.5f, 0.4f)
                .image(() -> imgTitulo))

        // Subtítulo
        .add(
            new Label(0.5f, 0.24f, 0.7f, 0.06f)
                .text("An adventure in a Isekai starts now... Get ready hero-san")
                .fontColor(Color.rgb(239, 30, 30)))

        // Separador visual
        .add(
            new Panel(0.25f, 0.31f, 0.5f, 0.005f)
                .color(Color.rgba(255, 220, 50, 180)))

        // CONTROLS (botão visual, sem action)
        .add(
            new Button(0.3f, 0.35f, 0.4f, 0.07f)
                .text("CONTROLS")
                .color(Color.rgba(24, 24, 27, 225))
                .fontColor(Color.rgb(200, 200, 255)))

        // Instruções como botões visuais (sem action)
        .add(
            new Button(0.2f, 0.43f, 0.6f, 0.05f)
                .text("WASD - move")
                .color(Color.rgba(20, 20, 20, 225))
                .fontColor(Color.rgb(200, 200, 200)))

        .add(
            new Button(0.2f, 0.49f, 0.6f, 0.05f)
                .text("1 - Basic Atk.  |  2 - Sp. Atk.  |  3 - Potion")
                .color(Color.rgba(20, 20, 20, 225))
                .fontColor(Color.rgb(200, 200, 200)))

        .add(
            new Button(0.2f, 0.55f, 0.6f, 0.05f)
                .text("R -> Restart game")
                .color(Color.rgba(20, 20, 20, 225))
                .fontColor(Color.rgb(200, 200, 200)))

        // Separador inferior
        .add(
            new Panel(0.25f, 0.62f, 0.5f, 0.005f)
                .color(Color.rgba(255, 255, 255, 100)))

        // Botão iniciar (clicável)
        .add(
            new Button(0.3f, 0.66f, 0.4f, 0.09f)
                .text("Start game")
                .color(Color.rgb(50, 100, 200))
                .fontColor(Color.rgb(255, 255, 255))
                .action(() -> {
                  sceneManager.transition(GameState.EXPLORATION);
                }));
  }

  @Override
  public void draw(Vec2 screenSize) {
    renderer.background(0);
    rootPanel.draw(Vec2.origin, screenSize);
  }

  @Override
  public void handleClick(Vec2 clickPos, Vec2 screenSize) {
    rootPanel.handleClick(clickPos, Vec2.origin, screenSize);
  }

  @Override
  public void handleKey(int keyCode) {
  }

}
