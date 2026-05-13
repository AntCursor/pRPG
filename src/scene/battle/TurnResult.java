package scene.battle;

public class TurnResult {
  public final String attackerName;
  public final String targetName;
  public final BattleAction action;
  public final float value; // damage dealt or HP healed

  public TurnResult(String attackerName, String targetName, BattleAction action, float value) {
    this.attackerName = attackerName;
    this.targetName = targetName;
    this.action = action;
    this.value = value;
  }
}
