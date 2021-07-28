package som.game.mage.inventory;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import som.chat.Chat;
import som.game.spell.spells.ParachuteSpell;
import som.game.spell.spells.SpellBook;
import som.player.PlayerState;

import java.util.UUID;

public class InventoryHandler {
    final String ON_DROP_SPELL_BOOK = "You cannot drop your spell book!";
    final String ON_DROP_PARACHUTE = "You cannot drop your parachute!";
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

    public boolean hasItem (final Material type) {
        return playerState.getPlayer()
                .filter(player -> player.getInventory().contains(type))
                .isPresent();
    }

    public boolean holdingItem (final Material type) {
        return holdingInMainHand(type) || holdingInOffHand(type);
    }
    public boolean holdingInMainHand (final Material type) {
        return playerState.getPlayer()
                .filter(player -> player.getInventory().getItemInMainHand().getType() == type)
                .isPresent();
    }
    public boolean holdingInOffHand (final Material type) {
        return playerState.getPlayer()
                .filter(player -> player.getInventory().getItemInOffHand().getType() == type)
                .isPresent();
    }

    public void onPlayerDropItemEvent (final PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        UUID playerID = player.getUniqueId();
        ItemStack droppedItem = event.getItemDrop().getItemStack();
        // TODO fix bad code
        if (new SpellBook().itemCastsSpell(droppedItem.getType())) {
            chat.abilityMessage(playerID, ON_DROP_SPELL_BOOK);
            event.setCancelled(true);
        }
        if (new ParachuteSpell().itemCastsSpell(droppedItem.getType())) {
            chat.abilityMessage(playerID, ON_DROP_PARACHUTE);
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
