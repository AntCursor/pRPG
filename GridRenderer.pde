interface ColorMap {
  color mapByte(byte c);
}

class GridRenderer {
  private ColorMap colorMap;

  GridRenderer(ColorMap map) {
    this.colorMap = map;
  }

  void render(Grid grid) {
    float w = width/(float)grid.sizeX();
    float h = height/(float)grid.sizeY();

    grid.update((x, y) -> {
        fill(colorMap.mapByte(grid.get(x, y)));
        rect(x*w, y*h, w, h);
    });
  }
}
