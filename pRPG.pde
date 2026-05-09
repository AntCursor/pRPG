Grid grid;
GridRenderer gridRenderer;

CellConsumer worldGen = (x, y) -> {
    byte cell = (byte)(random(0, 1) >= 0.9 ? 1 : 0);
    grid.set(x, y, cell);
  };

void setup() {
  size(800, 600);
  noStroke();

  PImage nixos = requestImage("nixos.png");
  PImage green = createImage(32, 32, RGB);

  grid = new Grid(20, 15); 
  grid.forEach(worldGen);

  ImageMap im = cell -> {
    return cell == 0 ? green : nixos;
  };
  gridRenderer = new GridRenderer(grid, im);
}

void draw() {
  gridRenderer.render();
}
