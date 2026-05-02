Grid grid;

void setup() {
  size(800, 600);
  noStroke();

  grid = new Grid(20, 15); 
  grid.setColorMap(cell -> {
    return cell == 0 ? color(0, 129, 20) : color(128);
  });
  grid.update((x, y) -> {
    byte cell = (byte)(random(0, 1) >= 0.9 ? 1 : 0);
    grid.set(x, y, cell);
  });
}

void draw() {
  grid.draw();
}
