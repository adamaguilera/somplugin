package som.game.mage.mechanics;

import lombok.Getter;
import som.game.scoreboard.Leaderboard;

public class ScoreKeeper {
    final int MIN_SCORE = 0;
    @Getter
    int kills = 0;
    @Getter
    int penalty = 0;

    final Leaderboard leaderboard;

    public ScoreKeeper (final Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }


    public int getScore () {
        return Math.max(MIN_SCORE, getKills() - getPenalty());
    }

    public int addKill () {
        ++kills;
        leaderboard.updateLeaderboards();
        return kills;
    }

    public int addPenalty () {
        if (getScore() != 0) {
            ++penalty;
        }
        leaderboard.updateLeaderboards();
        return penalty;
    }
}
