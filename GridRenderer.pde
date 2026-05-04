interface ColorMap {
  color mapByte(byte c);
}
interface ImageMap {
  PImage mapByte(byte c);
}

class GridRenderer {
  private Grid grid;
  private ColorMap colorMap;
  private ImageMap imageMap;

  private float w;
  private float h;

  GridRenderer(Grid grid, ColorMap map) {
    this.grid = grid;
    this.colorMap = map;
    this.imageMap = null;

    this.upDimensions();
  }
  GridRenderer(Grid grid, ImageMap map) {
    this.grid = grid;
    this.imageMap = map;
    this.colorMap = null;

    this.upDimensions();
  }

  void render() {
    if (imageMap != null) {
      grid.forEach((x, y) -> {
        image(
          imageMap.mapByte(grid.get(x, y)),
          x*w, y*h, w, h
        );
      });
    } else if (colorMap != null) {
      grid.forEach((x, y) -> {
          fill(colorMap.mapByte(grid.get(x, y)));
          rect(x*w, y*h, w, h);
      });
    }
  }

  void upDimensions() {
    w = width/(float)grid.sizeX();  
    h = height/(float)grid.sizeY(); 
  }
}
