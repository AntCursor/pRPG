package character;

import processing.core.PApplet;

public class Team {
    public int level;
    public Character[] members = new Character[3];
    public int x, y;

    private int xp = 0;
    private int xpToLevelUp = 100;
    private PApplet sketch;
    public boolean isPlayer;

    public Team(PApplet sketch, int level, boolean isPlayer) {
        this.sketch = sketch;
        this.level = level;
        this.isPlayer = isPlayer;

        if (isPlayer) {
            members[0] = new Hero(sketch, "Guerreiro", 8);
            members[1] = new Hero(sketch, "Mago", 6);
            members[2] = new Hero(sketch, "Arqueiro", 10);
        } else {
            members[0] = new Enemy(sketch, "Goblin", 5);
            members[1] = new Enemy(sketch, "Orc", 4);
            members[2] = new Enemy(sketch, "Troll", 3);
        }

        for (Character c : members) {
            c.setStats(level);
        }
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        for (Character c : members) {
            c.x = x;
            c.y = y;
        }
    }

    public boolean isDefeated() {
        for (Character c : members) {
            if (!c.isDead()) return false;
        }
        return true;
    }

    public void gainXP(int amount) {
        xp += amount;
        if (xp >= xpToLevelUp) {
            xp -= xpToLevelUp;
            levelUp();
        }
    }

    private void levelUp() {
        level++;
        xpToLevelUp = (int)(xpToLevelUp * 1.5f);
        for (Character c : members) {
            if (!c.isDead()) {
                c.setStats(level);
            }
        }
    }

    public int getLevel() { return level; }
    public int getXP()    { return xp; }
    public int getXPNeeded() { return xpToLevelUp; }
}