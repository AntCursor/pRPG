package scene.battle;

import character.Team;
import core.GameContext;

public class BattleManager {
  private static final long ENEMY_TURN_DELAY = 2000;

  private final Team heroes;
  private final BattleQueue queue;
  private final GameContext game;

  private BattleState state;
  private int currentIndex = 0;
  private long enemyTurnStart = 0;
  private TurnResult lastResult = null;

  public BattleManager(Team heroes, Team enemies, GameContext game) {
    this.heroes = heroes;
    this.game = game;
    this.queue = new BattleQueue(heroes, enemies);

    advanceToNextAlive();
    determineState();
  }

  
  public void update() {
    if (state == BattleState.WAITING_ENEMY) {
      if (game.millis() - enemyTurnStart >= ENEMY_TURN_DELAY) {
        BattleCombatant attacker = queue.get(currentIndex);
        BattleCombatant target = queue.findAliveHero();
        if (target != null) {
          BattleAction action = BattleRules.randomEnemyAction(game.random());
          lastResult = BattleRules.resolve(attacker, target, action);
        }
        advanceTurn();
      }
    }
  }


  public void act(BattleAction action) {
    if (state != BattleState.WAITING_PLAYER)
      return;

    BattleCombatant attacker = queue.get(currentIndex);
    BattleCombatant target = action == BattleAction.POTION
        ? attacker
        : queue.findAliveEnemy();

    if (target == null)
      return;

    lastResult = BattleRules.resolve(attacker, target, action);
    advanceTurn();
  }

  private void advanceTurn() {
    if (queue.allEnemiesDead()) {
      heroes.gainXP(BattleRules.XP_REWARD);
      state = BattleState.BATTLE_WON;
      return;
    }
    if (queue.allHeroesDead()) {
      state = BattleState.BATTLE_LOST;
      return;
    }

    currentIndex = (currentIndex + 1) % BattleQueue.SIZE;
    advanceToNextAlive();
    queue.sort();
    determineState();
  }

  private void advanceToNextAlive() {
    for (int i = 0; i < BattleQueue.SIZE; i++) {
      if (queue.get(currentIndex).isAlive())
        break;
      currentIndex = (currentIndex + 1) % BattleQueue.SIZE;
    }
  }

  private void determineState() {
    boolean isHeroTurn = queue.get(currentIndex).isHero();
    state = isHeroTurn ? BattleState.WAITING_PLAYER : BattleState.WAITING_ENEMY;
    if (state == BattleState.WAITING_ENEMY)
      enemyTurnStart = game.millis();
  }

  
  public BattleDisplayState getDisplayState() {
    return new BattleDisplayState(
        queue,
        currentIndex,
        state == BattleState.WAITING_PLAYER,
        state,
        lastResult);
  }
}
