package som.lobby;

import lombok.Builder;
import som.chat.Chat;
import som.game.Game;
import som.helper.RunnableTask;
import som.player.LobbyPlayer;
import som.player.PlayerManager;
import som.player.PlayerState;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

@Builder
public class Lobby {
    final String GAME_START_MSG = "Game has started!";
    final String COUNTDOWN_CANCELLED_MSG = "Countdown has been cancelled!";
    final String COUNTDOWN_START_MSG = "Starting countdown till game start.";
    final int COUNTDOWN_IN_SEC = 5;

    final PlayerManager playerManager;
    final Chat chat = new Chat();
    @Setter @Getter
    boolean isEnabled;

    RunnableTask countdownRunnable;
    final Game game;


    private List<PlayerState> getPlayers () {
        return playerManager.getAllPlayers();
    }

    public boolean allPlayersReady () {
        for (PlayerState playerState : this.getPlayers()) {
            if (!playerState.isLobbyReady()) {
                return false;
            }
        }
        return true;
    }

    public void broadcastAmountReadyMessage () {
        chat.globalMessage(getAmountReadyMessage());
    }

    private String getAmountReadyMessage () {
        int amountReady = this.getPlayers().stream()
                .mapToInt(playerState -> playerState.isLobbyReady() ? 1 : 0)
                .sum();
        int total = this.getPlayers().size();
        return "There are (" + ChatColor.AQUA + amountReady +
                ChatColor.LIGHT_PURPLE + "/" +
                ChatColor.AQUA + total +
                ChatColor.LIGHT_PURPLE + ") players ready!";
    }

    public void startCountdownIfReady() {
        if (allPlayersReady() && isEnabled) {
            countdownRunnable = RunnableTask.builder()
                    .onTaskTick(countdownMsg())
                    .onTaskEnd(startGame())
                    .build();
            chat.globalMessage(COUNTDOWN_START_MSG);
            countdownRunnable.createTask(COUNTDOWN_IN_SEC);
        }
    }

    public Runnable startGame () {
        return () -> {
            if (allPlayersReady()) {
                isEnabled = false;
                this.onLobbyEnd();
                game.onStart();
            } else {
                chat.globalMessage(COUNTDOWN_CANCELLED_MSG);
                broadcastAmountReadyMessage();
            }
        };
    }

    private void onLobbyEnd () {
        this.getPlayers().stream()
                .map(PlayerState::getLobbyPlayer)
                .forEach(LobbyPlayer::disableLobbySettings);
    }

    public Runnable countdownMsg () {
        return new Runnable() {
            int countdown = COUNTDOWN_IN_SEC;
            @Override
            public void run() {
                chat.globalMessage(getCountdownMessage());
                countdown --;
            }

            private String getCountdownMessage () {
                return "Game will be starting in " + countdown + " seconds!";
            }
        };
    }

    public void addPlayer (final PlayerState playerState) {
        playerState.onAddedToLobby();
    }
}
