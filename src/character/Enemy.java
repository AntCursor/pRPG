package character;

import processing.core.PApplet;
import grid.Grid;

public class Enemy extends Character {

    public Enemy(PApplet sketch, String name, float speed) {
        super(sketch, name, speed, (long) sketch.random(2000, 5000));
    }

    // IA simples: sorteia uma direção aleatória
    public void moveRandom(Grid grid) {
        if (!canAct()) return;

        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        int tries = 0;
        while (tries < 4) {
            int dir = (int) sketch.random(4);
            int nx = x + dx[dir];
            int ny = y + dy[dir];

            if (nx >= 0 && nx < grid.sizeX() &&
                ny >= 0 && ny < grid.sizeY() &&
                grid.get(nx, ny) != 3) {
                x = nx;
                y = ny;
                registerAction();
                return;
            }
            tries++;
        }
    }

    @Override
    public void update() {}
}