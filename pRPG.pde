Grid grid;
GridRenderer gridRenderer;

void setup() {
  size(800, 600);
  noStroke();

  grid = new Grid(20, 15); 
  grid.forEach((x, y) -> {
    byte cell = (byte)(random(0, 1) >= 0.9 ? 1 : 0);
    grid.set(x, y, cell);
  });

  gridRenderer = new GridRenderer(grid, cell -> {
    return cell == 0 ? color(0x00, 0xa0, 0x10) : color(0x78);
  });
}

void draw() {
  gridRenderer.render();
}
