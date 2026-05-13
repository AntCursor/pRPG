package scene.battle;

import character.Team;

public class BattleQueue {
  public static final int SIZE = 6;
  private BattleCombatant[] queue = new BattleCombatant[SIZE];

  public BattleQueue(Team heroes, Team enemies) {
    for (int i = 0; i < heroes.size(); i++)
      queue[i] = new BattleCombatant(heroes.get(i), true);
    for (int i = 0; i < enemies.size(); i++)
      queue[heroes.size() + i] = new BattleCombatant(enemies.get(i), false);

    sort();
  }

  // Selection sort descending by effective speed
  public void sort() {
    for (int i = 0; i < SIZE - 1; i++) {
      int maxIdx = i;
      for (int j = i + 1; j < SIZE; j++) {
        if (queue[j].effectiveSpeed() > queue[maxIdx].effectiveSpeed())
          maxIdx = j;
      }
      BattleCombatant temp = queue[maxIdx];
      queue[maxIdx] = queue[i];
      queue[i] = temp;
    }
  }

  public BattleCombatant get(int index) {
    return queue[index];
  }

  public BattleCombatant findAliveEnemy() {
    for (int i = 0; i < SIZE; i++)
      if (!queue[i].isHero() && queue[i].isAlive())
        return queue[i];
    return null;
  }

  public BattleCombatant findAliveHero() {
    for (int i = 0; i < SIZE; i++)
      if (queue[i].isHero() && queue[i].isAlive())
        return queue[i];
    return null;
  }

  public boolean allHeroesDead() {
    for (int i = 0; i < SIZE; i++)
      if (queue[i].isHero() && queue[i].isAlive())
        return false;
    return true;
  }

  public boolean allEnemiesDead() {
    for (int i = 0; i < SIZE; i++)
      if (!queue[i].isHero() && queue[i].isAlive())
        return false;
    return true;
  }
}
