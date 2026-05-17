package scene.battle;

public class BattleRules {
  private BattleRules() {
  }

  public static final int XP_REWARD = 50;
  public static final float POTION_HEAL_RATIO = 0.3f;

  public static TurnResult resolve(
      BattleCombatant attacker,
      BattleCombatant target,
      BattleAction action) {

    float value;

    switch (action) {
      case ATTACK:
      case SKILL:
        value = attacker.getPower() * action.multiplier;
        target.applyDamage(value);
        break;
      case POTION:
        value = attacker.getMaxHp() * POTION_HEAL_RATIO;
        attacker.applyHeal(value);
        target = attacker;
        break;
      default:
        value = 0;
    }

    return new TurnResult(attacker.getName(), target.getName(), action, value);
  }

  public static BattleAction randomEnemyAction(float random) {

    if (random < 0.6f)
      return BattleAction.ATTACK;
    if (random < 0.9f)
      return BattleAction.SKILL;
    return BattleAction.ATTACK;
  }
}
