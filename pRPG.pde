Grid grid;
GridRenderer gridRenderer;

void setup() {
  size(800, 600);
  noStroke();

  PImage nixos = requestImage("nixos.png");
  PImage green = createImage(32, 32, RGB);

  grid = new Grid(20, 15); 
  grid.forEach((x, y) -> {
    byte cell = (byte)(random(0, 1) >= 0.9 ? 1 : 0);
    grid.set(x, y, cell);
  });

  ImageMap im = cell -> {
    return cell == 0 ? green : nixos;
  };
  gridRenderer = new GridRenderer(grid, im);
}

void draw() {
  gridRenderer.render();
}
