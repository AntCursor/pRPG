interface ColorMap {
  color mapByte(byte c);
}

interface CellUpdate {
  void update(int x, int y);
}

class Grid {
  private int sizeX;
  private int sizeY;

  private byte[] cells;
  private ColorMap colorMap;

  int sizeX() {
    return sizeX;
  }
  int sizeY() {
    return sizeY;
  }

  Grid(int w, int h) {
    this.sizeX = w;
    this.sizeY = h;

    this.cells = new byte[h * w];
  }

  void setColorMap(ColorMap cm) {
    this.colorMap = cm;
  }

  void draw() {
    if (colorMap == null) return;

    float w = width/(float)this.sizeX;
    float h = height/(float)this.sizeY;

    for (int y = 0; y < sizeY; ++y)
      for (int x = 0; x < sizeX; ++x) {
        fill(colorMap.mapByte(this.get(x, y)));
        rect(x*w, y*h, w, h);
    }
  }

  byte get(int x, int y) {
    return cells[y * sizeX + x];
  }
  void set(int x, int y, byte c) {
    cells[y * sizeX + x] = c;
  }

  void update(CellUpdate up) {
    for (int y = 0; y < sizeY; ++y)
      for (int x = 0; x < sizeX; ++x) {
        up.update(x, y);
    }
  }
}
