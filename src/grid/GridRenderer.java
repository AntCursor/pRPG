package grid;

import processing.core.*;

public class GridRenderer {
  public interface ColorMap {
    int mapByte(byte c);
  }

  public interface ImageMap {
    PImage mapByte(byte c);
  }

  private PApplet sketch;

  private Grid grid;
  private ColorMap colorMap;
  private ImageMap imageMap;

  private float w;
  private float h;

  public GridRenderer(PApplet sketch, Grid grid, ColorMap map) {
    this.sketch = sketch;
    this.grid = grid;
    this.colorMap = map;
    this.imageMap = null;

    this.updateDimensions();
  }

  public GridRenderer(PApplet sketch, Grid grid, ImageMap map) {
    this.sketch = sketch;
    this.grid = grid;
    this.imageMap = map;
    this.colorMap = null;

    this.updateDimensions();
  }

  public void render() {
    if (imageMap != null) {
      grid.forEach((x, y) -> {
        sketch.image(
            imageMap.mapByte(grid.get(x, y)),
            x * w, y * h, w, h);
      });
    } else if (colorMap != null) {
      grid.forEach((x, y) -> {
        sketch.fill(colorMap.mapByte(grid.get(x, y)));
        sketch.rect(x * w, y * h, w, h);
      });
    }
  }

  void updateDimensions() {
    w = sketch.width / (float) grid.sizeX();
    h = sketch.height / (float) grid.sizeY();
  }
}
