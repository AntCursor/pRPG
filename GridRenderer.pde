interface ColorMap {
  color mapByte(byte c);
}

class GridRenderer {
  private Grid grid;
  private ColorMap colorMap;

  GridRenderer(Grid grid, ColorMap map) {
    this.grid = grid;
    this.colorMap = map;
  }

  void render() {
    float w = width/(float)grid.sizeX();  
    float h = height/(float)grid.sizeY(); 

    grid.forEach((x, y) -> {
        fill(colorMap.mapByte(grid.get(x, y)));
        rect(x*w, y*h, w, h);
    });
  }
}
