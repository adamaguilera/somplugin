package som.game;

import som.chat.Chat;
import som.game.scoreboard.Leaderboard;
import som.game.spawn.Platform;
import som.game.spawn.PlatformPlayer;
import som.player.PlayerManager;
import som.player.PlayerState;
import som.task.PlatformTask;
import som.task.TaskRegister;


public class Game {
    final String ON_GAME_START = "Game has started!";
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
        taskRegister.start();
        this.spawnAllPlayers();
    }

    private void spawnPlayer (final PlayerState playerState) {
        PlatformPlayer platformPlayer = playerState.getPlatformPlayer();
        if (!platformPlayer.hasSavedInventory()) {
            platformPlayer.onSpawn();
        }
        teleportToSpawn(playerState);
    }

    private void spawnAllPlayers () {
        playerManager.getAllPlayers().forEach(this::spawnPlayer);
    }

    public void teleportToSpawn (final PlayerState playerState) {
        playerState.getPlayer().ifPresent(player ->
                player.teleport(platform.getSpawn()));
    }



}
