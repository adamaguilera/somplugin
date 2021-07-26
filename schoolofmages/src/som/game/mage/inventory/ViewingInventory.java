package som.game.mage.inventory;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import som.player.PlayerState;

import java.util.Optional;

@RequiredArgsConstructor
public class ViewingInventory {
    final PlayerState playerState;
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    Optional<Inventory> viewingInventory;
    Runnable onForceClose = EMPTY_FORCE_CLOSE();

    public Runnable EMPTY_FORCE_CLOSE () { return () -> {}; }

    public void viewInventory (@NotNull final Inventory inventory) {
        this.viewInventory(inventory, EMPTY_FORCE_CLOSE());
    }
    public void viewInventory (@NotNull final Inventory inventory,
                              final Runnable onForceClose) {
        this.commandClose();
        this.viewingInventory = Optional.of(inventory);
        this.onForceClose = onForceClose;
        playerState.getPlayer().ifPresent(player -> {
            player.openInventory(inventory);
        });
    }

    public boolean isViewingInventory (final Inventory inventory) {
        return viewingInventory.map(curr -> curr.equals(inventory)).isPresent();
    }

    /**
     * Will execute force close
     */
    public void eventClose () {
        onForceClose.run();
        emptyData();
    }

    /**
     * Will not execute forceClose
     */
    public void commandClose () {
        emptyData();
        playerState.getPlayer().ifPresent(Player::closeInventory);
    }

    public void emptyData () {
        viewingInventory = Optional.empty();
        onForceClose = EMPTY_FORCE_CLOSE();
    }
}
