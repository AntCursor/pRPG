package scene.battle;

public enum BattleAction {
  ATTACK("1 - ATTACK", 1.0f),
  SKILL("2 - SKILL", 1.8f),
  POTION("3 - POTION", 0.0f);

  public final String label;
  public final float multiplier;

  BattleAction(String label, float multiplier) {
    this.label = label;
    this.multiplier = multiplier;
  }
}
