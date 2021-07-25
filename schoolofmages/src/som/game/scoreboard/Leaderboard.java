package som.game.scoreboard;

import lombok.Builder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import som.game.mage.Mage;
import som.player.PlayerManager;
import som.player.PlayerState;

import java.util.List;

@Builder
public class Leaderboard {
    final String OBJECTIVE_NAME = "kills";
    final String OBJECTIVE_CRITERIA = "kill";
    final String OBJECTIVE_DISPLAY = "Kills";
    final String TOP_BAR = ChatColor.RED + "=-=-=-=-=-=-=";
    final String BOT_BAR = ChatColor.RED + "-=-=-=-=-=-=-";
    final int TOP_BAR_SCORE = -10;
    final int PLAYER_KILLS_SCORE = -11;
    final int BOT_BAR_SCORE = -12;
    final int MAX_DISPLAY = 4;
    final PlayerManager playerManager;


    public void updateLeaderboards () {
        List<PlayerState> ranked = playerManager.getPlayersByRank();
        playerManager.getAllPlayers().forEach(playerState -> displayLeaderboard(playerState, ranked));
    }

    private void displayLeaderboard (final PlayerState playerState,
                                     final List<PlayerState> ranked) {
        playerState.getPlayer().ifPresent(player -> {
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective objective = scoreboard.registerNewObjective(OBJECTIVE_NAME,
                    OBJECTIVE_CRITERIA, OBJECTIVE_DISPLAY);
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            setBaseBoard(ranked, objective);
            addPlayerInformation(playerState, objective);
            player.setScoreboard(scoreboard);
        });
    }

    private void setBaseBoard (List<PlayerState> ranked,
                               Objective objective) {
        for (int index = 0; index < ranked.size() &&
                index < MAX_DISPLAY; index ++) {
            PlayerState playerState = ranked.get(index);
            Mage mage = playerState.getMage();
            playerState.getPlayer().ifPresent(player -> {
                objective.getScore(player.getDisplayName())
                        .setScore(mage.getKills());
            });
        }
    }

    private void addPlayerInformation (final PlayerState playerState,
                                             final Objective objective) {
        int kills = playerState.getMage().getKills();
        objective.getScore(TOP_BAR).setScore(TOP_BAR_SCORE);
        objective.getScore("You have " + kills + " kills!")
                .setScore(PLAYER_KILLS_SCORE);
        objective.getScore(BOT_BAR).setScore(BOT_BAR_SCORE);
    }
}
