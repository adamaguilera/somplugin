package som.game;

import som.chat.Chat;
import som.game.scoreboard.Leaderboard;
import som.player.PlayerManager;

public class Game {
    final String ON_GAME_START = "Game has started!";
    final PlayerManager playerManager;
    final Leaderboard leaderboard;
    final Chat chat = new Chat();


    public Game (final PlayerManager playerManager) {
        this.playerManager = playerManager;
        this.leaderboard = Leaderboard.builder()
                .playerManager(this.playerManager)
                .build();
    }


    public void onStart () {
        leaderboard.updateLeaderboards();
        chat.globalMessage(ON_GAME_START);
    }




}
