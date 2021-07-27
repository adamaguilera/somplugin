package som.game.mage;

import lombok.Getter;
import som.game.mage.inventory.InventoryHandler;
import som.game.mage.inventory.ViewingInventory;
import som.game.mage.spell.ManaHandler;
import som.game.mage.spell.PassiveHandler;
import som.game.mage.spell.SpellManager;
import som.game.passive.PassivePool;
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
    private int kills = 0;

    public Mage (final PlayerState playerState,
                 final SpellPool spellPool,
                 final PassivePool passivePool) {
        this.playerState = playerState;
        this.manaHandler = ManaHandler.builder()
                .playerState(this.playerState)
                .build();
        this.spellManager = new SpellManager(this.playerState, spellPool);
        this.passiveHandler = new PassiveHandler(this.playerState, passivePool);
        this.inventoryHandler = new InventoryHandler(this.playerState);
    }
}
