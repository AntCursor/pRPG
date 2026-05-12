package character;

import processing.core.PApplet;
import grid.Grid;

public class Hero extends Character {

    public Hero(PApplet sketch, String name, float speed) {
        super(sketch, name, speed, 200); // 200ms cooldown de movimento
    }

    // Tenta mover no grid, respeitando cooldown e colisões
    public boolean tryMove(int dx, int dy, Grid grid) {
        if (!canAct()) return false;

        int nx = x + dx;
        int ny = y + dy;

        if (nx < 0 || nx >= grid.sizeX()) return false;
        if (ny < 0 || ny >= grid.sizeY()) return false;
        if (grid.get(nx, ny) == 3) return false; // obstáculo

        x = nx;
        y = ny;
        registerAction();
        return true;
    }

    // Habilidade 1: Ataque básico
    public float skill1() {
        registerAction();
        return power;
    }

    // Habilidade 2: Ataque pesado (mais dano, menos consistente)
    public float skill2() {
        registerAction();
        return power * 1.5f + sketch.random(-5, 5);
    }

    // Habilidade 3: Cura (recupera 25% do maxHp)
    public float skill3() {
        registerAction();
        float healAmount = maxHp * 0.25f;
        heal(healAmount);
        return 0; // não causa dano
    }

    @Override
    public void update() {
    }
}