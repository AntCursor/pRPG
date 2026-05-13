package scene.battle;

import character.Character;

public class BattleCombatant {
  private final Character character;
  private final boolean isHero;

  public BattleCombatant(Character character, boolean isHero) {
    this.character = character;
    this.isHero = isHero;
  }

  public boolean isAlive() {
    return character.getHp() > 0;
  }

  public boolean isHero() {
    return isHero;
  }

  public float effectiveSpeed() {
    return isAlive() ? character.getSpeed() : Float.NEGATIVE_INFINITY;
  }

  public void applyDamage(float damage) {
    character.lostHp(damage);
  }

  public void applyHeal(float amount) {
    character.addHp(amount);
  }

  public float getHpRatio() {
    return character.getHp() / character.getMaxHp();
  }

  public String getName() {
    return character.getName();
  }

  public float getPower() {
    return character.getPower();
  }

  public float getMaxHp() {
    return character.getMaxHp();
  }
}
