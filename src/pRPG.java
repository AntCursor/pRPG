import processing.core.*;
import grid.*;

public class pRPG extends PApplet {
  Grid grid;
  GridRenderer gridRenderer;

  Grid.CellConsumer worldGen = (x, y) -> {
    byte cell = (byte) (random(0, 1) >= 0.9 ? 1 : 0);
    grid.set(x, y, cell);
  };

  @Override
  public void settings() {
    size(800, 600);
  }

  @Override
  public void setup() {
    noStroke();

    PImage nixos = requestImage("nixos.png");
    PImage green = createImage(32, 32, RGB);

    grid = new Grid(20, 15);
    grid.forEach(worldGen);

    GridRenderer.ImageMap im = cell -> {
      return cell == 0 ? green : nixos;
    };

    gridRenderer = new GridRenderer(this, grid, im);
  }

  @Override
  public void draw() {
    gridRenderer.render();
  }

  public static void main(String[] args) {
    String[] processingArgs = { "pRPG" };
    pRPG sketch = new pRPG();
    PApplet.runSketch(processingArgs, sketch);
  }
}
