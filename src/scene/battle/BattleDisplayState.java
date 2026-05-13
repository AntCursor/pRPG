package scene.battle;

import assets.TileType;

public class BattleDisplayState {
  // Queue slots — name and HP ratio for each position
  public final String[] names = new String[BattleQueue.SIZE];
  public final TileType[] imgs = new TileType[BattleQueue.SIZE];
  public final float[] hpRatios = new float[BattleQueue.SIZE];
  public final boolean[] isHero = new boolean[BattleQueue.SIZE];
  public final boolean[] isAlive = new boolean[BattleQueue.SIZE];

  public final int currentTurnIndex;
  public final boolean isPlayerTurn;
  public final BattleState state;
  public final TurnResult lastResult; // may be null on first turn

  public BattleDisplayState(
      BattleQueue queue,
      int currentTurnIndex,
      boolean isPlayerTurn,
      BattleState state,
      TurnResult lastResult) {

    for (int i = 0; i < BattleQueue.SIZE; i++) {
      BattleCombatant c = queue.get(i);
      names[i] = c.getName();
      hpRatios[i] = c.getHpRatio();
      isHero[i] = c.isHero();
      isAlive[i] = c.isAlive();
      imgs[i] = c.getSprite();
    }

    this.currentTurnIndex = currentTurnIndex;
    this.isPlayerTurn = isPlayerTurn;
    this.state = state;
    this.lastResult = lastResult;
  }
}
