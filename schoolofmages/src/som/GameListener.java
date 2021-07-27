package som;

import lombok.Builder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import som.chat.Log;
import som.game.Game;
import som.game.mage.Mage;
import som.game.mage.inventory.InventoryHandler;
import som.game.mage.inventory.ViewingInventory;
import som.game.mage.spell.SpellManager;
import som.player.PlayerManager;
import som.player.PlayerState;

import java.util.UUID;

@Builder
public class GameListener implements Listener {
    final Game game;
    final PlayerManager playerManager;

    @EventHandler
    public void onPlayerInteractEvent (final PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID playerID = player.getUniqueId();
        PlayerState playerState = playerManager.getPlayerState(playerID);
        Mage mage = playerState.getMage();
        SpellManager spellManager = mage.getSpellManager();
        boolean shouldCancel = spellManager.onPlayerInteractEvent(event);
        event.setCancelled(shouldCancel);
    }

    @EventHandler
    public void onPlayerDropEvent (final PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        UUID playerID = player.getUniqueId();
        PlayerState playerState = playerManager.getPlayerState(playerID);
        Mage mage = playerState.getMage();
        InventoryHandler inventoryHandler = mage.getInventoryHandler();
        inventoryHandler.onPlayerDropItemEvent(event);
    }

    @EventHandler
    public void onInventoryClickEvent (final InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID playerID = player.getUniqueId();
        PlayerState playerState = playerManager.getPlayerState(playerID);
        Mage mage = playerState.getMage();
        InventoryHandler inventoryHandler = mage.getInventoryHandler();
        inventoryHandler.onInventoryClickEvent(event);
    }

    @EventHandler
    public void onInventoryDragEvent(final InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID playerID = player.getUniqueId();
        PlayerState playerState = playerManager.getPlayerState(playerID);
        Mage mage = playerState.getMage();
        InventoryHandler inventoryHandler = mage.getInventoryHandler();
        inventoryHandler.onInventoryDragEvent(event);
    }

    @EventHandler
    public void onInventoryCloseEvent(final InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        UUID playerID = player.getUniqueId();
        PlayerState playerState = playerManager.getPlayerState(playerID);
        Mage mage = playerState.getMage();
        InventoryHandler inventoryHandler = mage.getInventoryHandler();
        inventoryHandler.onInventoryCloseEvent(event);
    }
}
