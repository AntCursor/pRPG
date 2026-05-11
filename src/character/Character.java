package characters;

import processing.core.PApplet;

public abstract class Character {
    private String name;
    private float hp, maxHp;
    private float speed;
    private float power;
    private int x, y; // Posição no Grid

    private PApplet sketch;
  
    private long lastActionTime = 0;
    private long cooldown;

    public Character(String name, float speed, float maxHp, long cd) {
        this.name = name;
        this.speed = speed;
        this.cooldown = cd;
        this.maxHp = maxHp;
    }

    public void setinitialHp(float maxHp) {
        this.hp = maxHp;
    }

    public void addHp(float hp, float heal) {
        float new_hp = hp + heal;
        hp = new_hp;
    }    

    public void lostHp(float hp, float damage) {
        float new_hp = hp - damage;
        hp = new_hp;
    }

    public float getHp(){
        return hp;
    }

    public float getmaxHp(){
        return maxHp;
    }

    public float getSpeed() {
        return speed;
    }

    public String getName() {
        return name;
    }

    public float getPower(){
        return power;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean canAct() {
        if (sketch.millis() - lastActionTime >= cooldown) {
            return true;
        }
            return false;
    }
    
    void Action() {
        lastActionTime = sketch.millis();
    }
}

