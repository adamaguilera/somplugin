package som.game;

import som.chat.Chat;
import som.game.scoreboard.Leaderboard;
import som.game.spawn.Platform;
import som.player.PlayerManager;
import som.player.PlayerState;


public class Game {
    final String ON_GAME_START = "Game has started!";
    final PlayerManager playerManager;
    final Leaderboard leaderboard;
    final Platform platform;
    final Chat chat = new Chat();

    public Game (final GameConfig gameConfig,
                 final PlayerManager playerManager) {
        this.playerManager = playerManager;
        this.leaderboard = Leaderboard.builder()
                .playerManager(this.playerManager)
                .build();
        this.platform = gameConfig.getPlatform();
        this.platform.generatePlatform();
    }


    public void onStart () {
        leaderboard.updateLeaderboards();
        chat.globalMessage(ON_GAME_START);
        this.teleportAllToSpawn();
    }


    private void teleportAllToSpawn () {
        playerManager.getAllPlayers().forEach(this::teleportToSpawn);
    }

    public void teleportToSpawn (final PlayerState playerState) {
        playerState.getPlayer().ifPresent(player ->
                player.teleport(platform.getSpawn()));
    }



}
