package som.game.mage;

import lombok.Getter;
import som.game.mage.inventory.InventoryHandler;
import som.game.mage.inventory.ViewingInventory;
import som.game.mage.spell.ManaHandler;
import som.game.mage.spell.SpellManager;
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
    private int kills = 0;

    public Mage (final PlayerState playerState) {
        this.playerState = playerState;
        this.manaHandler = ManaHandler.builder()
                .playerState(this.playerState)
                .build();
        this.spellManager = new SpellManager(this.playerState);
        this.inventoryHandler = new InventoryHandler(this.playerState);
    }
}
