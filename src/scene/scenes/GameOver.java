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

public class GameOver extends BaseScene {

  public GameOver(SceneManager manager, UIRenderer renderer, AssetManager assets, GameContext game) {
    super(manager, renderer, assets, game);
  }

  @Override
  public void setup() {
    assetManager.loadTiles(TileType.GAMEOVER);
    Object imgGameOver = assetManager.get(TileType.GAMEOVER.id).get();

    rootPanel = Panel.root()
        // Fundo escurecido ou imagem de game over
        .add(new Image(0, 0, 1, 1).image(() -> imgGameOver))

        // Retângulo central semi-transparente para destacar o texto
        .add(new Panel(0.2f, 0.3f, 0.6f, 0.4f).color(Color.rgba(0, 0, 0, 180)))
        // Título de Derrota
        .add(new Label(0.5f, 0.4f, 0.5f, 0.1f)
            .text("YOU DIED")
            .fontColor(Color.rgb(255, 30, 30)))

        // Frase de efeito
        .add(new Label(0.5f, 0.5f, 0.5f, 0.05f)
            .text("Seems you were not The Chosen Mc one...")
            .fontColor(Color.rgb(180, 180, 180)))

        .add(new Button(0.35f, 0.58f, 0.3f, 0.06f)
            .text("EXIT TO MENU")
            .color(Color.rgb(120, 20, 20))
            .fontColor(Color.rgb(255, 255, 255))
            .action(() -> {
              sceneManager.transition(GameState.MAIN_MENU);
            }));
  }

  @Override
  public void draw(Vec2 screenSize) {
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
