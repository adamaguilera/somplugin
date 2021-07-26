package som.game;

import lombok.Getter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import som.Main;
import som.chat.Chat;
import som.game.mage.Mage;
import som.game.mage.inventory.InventoryHandler;
import som.game.mage.inventory.StartingInventory;
import som.game.scoreboard.Leaderboard;
import som.game.spawn.Platform;
import som.game.spawn.PlatformPlayer;
import som.player.PlayerManager;
import som.player.PlayerState;
import som.task.PlatformTask;
import som.task.TaskRegister;


public class Game {
    final String ON_GAME_START = "Game has started!";
    @Getter
    final PlayerManager playerManager;
    final Leaderboard leaderboard;
    final Platform platform;
    final TaskRegister taskRegister;
    final PlatformTask platformTask;
    final Chat chat = new Chat();

    public Game (final GameConfig gameConfig,
                 final PlayerManager playerManager) {
        this.playerManager = playerManager;
        this.leaderboard = Leaderboard.builder()
                .playerManager(this.playerManager)
                .build();
        this.platform = gameConfig.getPlatform();
        this.platform.generatePlatform();
        this.platformTask = PlatformTask.builder()
                .playerManager(this.playerManager)
                .build();
        this.taskRegister = new TaskRegister();
        this.taskRegister.addTask(platformTask);
    }

    public void onStart () {
        leaderboard.updateLeaderboards();
        chat.globalMessage(ON_GAME_START);
        Main.GET_INSTANCE().registerGameListener();
        taskRegister.start();
        this.giveStartingItems();
        this.spawnAllPlayers();
    }

    private void spawnPlayer (final PlayerState playerState) {
        PlatformPlayer platformPlayer = playerState.getPlatformPlayer();
        if (!platformPlayer.hasSavedInventory()) {
            platformPlayer.onSpawn();
        }
        teleportToSpawn(playerState);
    }

    private void giveStartingItems () {
        playerManager.getAllPlayers().stream()
                .map(PlayerState::getMage)
                .map(Mage::getInventoryHandler)
                .map(InventoryHandler::getStartingInventory)
                .forEach(StartingInventory::giveStartingInventory);
    }

    private void spawnAllPlayers () {
        playerManager.getAllPlayers().forEach(this::spawnPlayer);
    }

    public void teleportToSpawn (final PlayerState playerState) {
        playerState.getPlayer().ifPresent(player ->
                player.teleport(platform.getSpawn()));
    }



}
