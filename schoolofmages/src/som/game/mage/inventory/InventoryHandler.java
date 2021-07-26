package som.game.mage.inventory;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import som.chat.Chat;
import som.game.spell.SpellPool;
import som.player.PlayerState;

import java.util.UUID;

public class InventoryHandler {
    final String ON_DROP_SPELL_BOOK = "You cannot drop your spell book!";
    final PlayerState playerState;
    @Getter
    final ViewingInventory viewingInventory;
    @Getter
    final StartingInventory startingInventory;
    final Chat chat;

    public InventoryHandler (final PlayerState playerState) {
        this.chat = new Chat();
        this.playerState = playerState;
        this.viewingInventory = new ViewingInventory(playerState);
        this.startingInventory = new StartingInventory(playerState);
    }

    public void onPlayerDropItemEvent (final PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        UUID playerID = player.getUniqueId();
        ItemStack droppedItem = event.getItemDrop().getItemStack();
        if (SpellPool.GET_SPELL_BOOK().itemCastsSpell(droppedItem.getType())) {
            chat.abilityMessage(playerID, ON_DROP_SPELL_BOOK);
            event.setCancelled(true);
        }
    }

    public void onInventoryClickEvent (final InventoryClickEvent event) {
        Inventory interactedInventory = event.getInventory();
        if (this.viewingInventory.isViewingInventory(interactedInventory)) {
            event.setCancelled(true);
        }
    }

    public void onInventoryDragEvent (final InventoryDragEvent event) {
        Inventory interactedInventory = event.getInventory();
        if (this.viewingInventory.isViewingInventory(interactedInventory)) {
            event.setCancelled(true);
        }
    }

    public void onInventoryCloseEvent (final InventoryCloseEvent event) {
        this.viewingInventory.eventClose();
    }
}
