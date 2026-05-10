interface CellConsumer {
  void apply(int x, int y);
}

class Grid {
  private int sizeX;
  private int sizeY;

  private byte[] cells;

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

  byte get(int x, int y) {
    return cells[y * sizeX + x];
  }

  void set(int x, int y, byte c) {
    cells[y * sizeX + x] = c;
  }

  void forEach(CellConsumer f) {
    for (int y = 0; y < sizeY; ++y)
      for (int x = 0; x < sizeX; ++x) {
        f.apply(x, y);
      }
  }
}
