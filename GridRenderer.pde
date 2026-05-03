interface ColorMap {
  color mapByte(byte c);
}

class GridRenderer {
  private Grid grid;
  private ColorMap colorMap;

  private float w;
  private float h;

  GridRenderer(Grid grid, ColorMap map) {
    this.grid = grid;
    this.colorMap = map;

    this.upDimensions();
  }

  void render() {
    grid.forEach((x, y) -> {
        fill(colorMap.mapByte(grid.get(x, y)));
        rect(x*w, y*h, w, h);
    });
  }

  void upDimensions() {
    w = width/(float)grid.sizeX();  
    h = height/(float)grid.sizeY(); 
  }
}
