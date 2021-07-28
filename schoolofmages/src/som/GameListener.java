package som;

import lombok.Builder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import som.chat.Log;
import som.game.Game;
import som.game.mage.Mage;
import som.game.mage.inventory.InventoryHandler;
import som.game.mage.inventory.ViewingInventory;
import som.game.mage.mechanics.DeathManager;
import som.game.mage.mechanics.RespawnHandler;
import som.game.mage.spell.SpellManager;
import som.player.PlayerManager;
import som.player.PlayerState;

import java.util.UUID;

@Builder
public class GameListener implements Listener {
    final Game game;
    final PlayerManager playerManager;


    @EventHandler
    public void onEntityDamageEvent (final EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            playerManager.getPlayerState(event.getEntity().getUniqueId())
                    .getMage()
                    .getSpawnProtection()
                    .onEntityDamageEvent(event);
        }
    }


    @EventHandler
    public void onEntityDamageByEntityEvent (final EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            playerManager.getPlayerState(event.getEntity().getUniqueId())
                    .getMage()
                    .getSpawnProtection()
                    .onEntityDamageByEntityEvent(event);
        }
        if (event.getDamager() instanceof Player) {
            playerManager.getPlayerState(event.getDamager().getUniqueId())
                    .getMage()
                    .getSpawnProtection()
                    .onEntityDamageByEntityEvent(event);
        }
    }

    @EventHandler
    public void onPlayerDeathEvent (final PlayerDeathEvent event) {
        Player player = event.getEntity();
        UUID playerID = player.getUniqueId();
        PlayerState playerState = playerManager.getPlayerState(playerID);
        Mage mage = playerState.getMage();
        DeathManager deathManager = mage.getDeathManager();
        deathManager.onPlayerDeathEvent(event);
        Player killer = event.getEntity().getKiller();
        if (killer != null) {
            playerManager.getPlayerState(killer.getUniqueId())
                    .getMage()
                    .getDeathManager()
                    .onPlayerDeathEvent(event);
        }
    }

    @EventHandler
    public void onPlayerRespawnEvent (final PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        playerManager.getPlayerState(player.getUniqueId())
                .getMage()
                .getRespawnHandler()
                .onPlayerRespawnEvent(event);
    }

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

    @EventHandler
    public void onPlayerBedLeaveEvent(final PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        UUID playerID = player.getUniqueId();
        PlayerState playerState = playerManager.getPlayerState(playerID);
        Mage mage = playerState.getMage();
        RespawnHandler respawnHandler = mage.getRespawnHandler();
        respawnHandler.onPlayerBedLeaveEvent(event);
    }
}
