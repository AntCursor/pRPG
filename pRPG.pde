Grid grid;
GridRenderer gridRenderer;
GameModes currentGamemode = GameModes.MENU;
private float noiseScale = 0.15;

void generateMap(Grid grid) {
    noiseSeed((long)random(1000));
    grid.forEach((x, y) -> {
      float nos = noise(x * noiseScale, y * noiseScale);

      byte type;
      if (nos < 0.3) type = 0;
      else if (nos < 0.45) type = 1;
      else if (nos < 0.7) type =2;
      else type = 3;

      grid.set(x, y, type);
    });
  }

void setup() {
  size(800, 600);
  noStroke();

  PImage nixos = loadImage("gramapixeltextura.jpg");
  PImage green = createImage(32, 32, RGB);

  switch (currentGamemode) {
    case MENU:
    break;

    case EXPLORATION:
      grid = new Grid(20, 15); 
      generateMap(grid);
      ImageMap im = cell -> {
        return cell == 0 ? nixos : green;
      };
      gridRenderer = new GridRenderer(grid, im);
      break;

    case BATTLE:
    break;

    case GAMEOVER:
    break;
  }
}

void draw() {
  gridRenderer.render();
}
