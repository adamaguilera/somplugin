package som.game.mage;

import lombok.Getter;
import som.game.mage.inventory.InventoryHandler;
import som.game.mage.inventory.ViewingInventory;
import som.game.mage.mechanics.DeathManager;
import som.game.mage.mechanics.KillManager;
import som.game.mage.mechanics.RespawnHandler;
import som.game.mage.mechanics.ScoreKeeper;
import som.game.mage.spell.ManaHandler;
import som.game.mage.spell.PassiveHandler;
import som.game.mage.spell.SpellManager;
import som.game.passive.PassivePool;
import som.game.scoreboard.Leaderboard;
import som.game.spawn.SpawnProtection;
import som.game.spell.SpellPool;
import som.player.PlayerState;

public class Mage {
    final PlayerState playerState;
    @Getter
    final SpellManager spellManager;
    @Getter
    final ManaHandler manaHandler;
    @Getter
    final InventoryHandler inventoryHandler;
    @Getter
    final PassiveHandler passiveHandler;
    @Getter
    final RespawnHandler respawnHandler;
    @Getter
    final DeathManager deathManager;
    final KillManager killManager;
    @Getter
    final ScoreKeeper scoreKeeper;
    @Getter
    final SpawnProtection spawnProtection;

    public Mage (final PlayerState playerState,
                 final SpellPool spellPool,
                 final PassivePool passivePool,
                 final Leaderboard leaderboard) {
        this.playerState = playerState;
        this.spawnProtection = new SpawnProtection();
        this.manaHandler = ManaHandler.builder()
                .playerState(this.playerState)
                .build();
        this.spellManager = new SpellManager(this.playerState, spellPool);
        this.passiveHandler = new PassiveHandler(this.playerState, passivePool);
        this.inventoryHandler = new InventoryHandler(this.playerState);
        this.respawnHandler = new RespawnHandler(this.playerState);
        this.scoreKeeper = new ScoreKeeper(leaderboard);
        this.killManager = new KillManager(this.playerState, this.scoreKeeper);
        this.deathManager = new DeathManager(this.playerState,
                                            this.scoreKeeper,
                                            this.respawnHandler);
    }
}
