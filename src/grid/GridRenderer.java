package grid;

import ui.UIRenderer;

public class GridRenderer {
  private UIRenderer renderer;

  private Grid grid;
  private ColorMap colorMap;
  private ImageMap imageMap;

  private float w;
  private float h;

  public GridRenderer(UIRenderer renderer, Grid grid, ColorMap map) {
    this.renderer = renderer;
    this.grid = grid;
    this.colorMap = map;
    this.imageMap = null;

    this.updateDimensions();
  }

  public GridRenderer(UIRenderer renderer, Grid grid, ImageMap map) {
    this.renderer = renderer;
    this.grid = grid;
    this.imageMap = map;
    this.colorMap = null;

    this.updateDimensions();
  }

  public void render() {
    if (imageMap != null) {
      grid.forEach((x, y) -> {
        renderer.drawImage(
            imageMap.mapByte(grid.get(x, y)),
            x * w, y * h, w, h);
      });
    } else if (colorMap != null) {
      grid.forEach((x, y) -> {
        renderer.fill(colorMap.mapByte(grid.get(x, y)));
        renderer.drawRect(x * w, y * h, w, h);
      });
    }
  }

  public void renderOn(Object img, int x, int y) {
    renderer.drawImageFit(img, x * w, y * h, w, h);
  }

  public void updateDimensions() {
    w = renderer.width() / (float) grid.sizeX();
    h = renderer.height() / (float) grid.sizeY();
  }

  public interface ColorMap {
    int mapByte(byte c);
  }

  public interface ImageMap {
    Object mapByte(byte c);
  }
}
