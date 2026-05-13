package character;

public class Team {
  public static final int SIZE = 3;
  private static final int XP_PER_LEVEL = 100;

  private Character[] members = new Character[SIZE];
  private int count = 0;
  private int level = 1;
  private int xp = 0;
  private final boolean isHeroTeam;

  public Team(boolean isHeroTeam) {
    this.isHeroTeam = isHeroTeam;
  }

  public Team add(Character c) {
    if (count < SIZE) {
      applyLevelStats(c);
      members[count++] = c;
    }
    return this;
  }

  // Package-private: only BattleCombatant wraps characters directly
  public Character get(int index) {
    return members[index];
  }

  public int size() {
    return count;
  }

  public int getLevel() {
    return level;
  }

  public boolean isHeroTeam() {
    return isHeroTeam;
  }

  public void gainXP(int amount) {
    if (!isHeroTeam)
      return;
    xp += amount;
    if (xp >= XP_PER_LEVEL) {
      xp -= XP_PER_LEVEL;
      levelUp();
    }
  }

  private void levelUp() {
    level++;
    for (int i = 0; i < count; i++)
      applyLevelStats(members[i]);
  }

  // HP = level * 50, Attack = level * 10
  private void applyLevelStats(Character c) {
    float newMaxHp = level * 50f;
    float newPower = level * 10f;
    c.maxHp(newMaxHp);
    c.power(newPower);
    c.setHp(newMaxHp);
  }
}
