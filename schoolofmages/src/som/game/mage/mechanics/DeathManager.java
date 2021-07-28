package som.game.mage.mechanics;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.Score;
import som.chat.Chat;
import som.chat.Log;
import som.player.PlayerManager;
import som.player.PlayerState;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class DeathManager {
    // 1 is the killer, 2 is the victim
    final String KILLER_SEQUENCE = "#";
    final String VICTIM_SEQUENCE = "&";
    final List<String> KILL_MESSAGES = List.of(
            "# just pwned &!",
            "& got completely slaughtered by #!",
            "The lord # showed his power over the petty squire &!",
            "# got rekted by &!",
            "& you really gonna let # do that to you?",
            "# whispered a dank meme to &, it was too funny..."
    );
    final Random random = new Random();
    
    final PlayerState playerState;
    final ScoreKeeper scoreKeeper;
    final KillManager killManager;
    final RespawnHandler respawnHandler;
    final Chat chat;

    public DeathManager (final PlayerState playerState,
                         final ScoreKeeper scorekeeper,
                         final RespawnHandler respawnHandler) {
        this.chat = new Chat();
        this.playerState = playerState;
        this.scoreKeeper = scorekeeper;
        this.respawnHandler = respawnHandler;
        this.killManager = new KillManager(this.playerState,
                            this.scoreKeeper);
    }

    public void onPlayerDeathEvent (final PlayerDeathEvent event) {
        // check for self death
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        if (killer != null) {
            if (isThisPlayer(victim.getUniqueId())) {
                if (isThisPlayer(killer.getUniqueId())) {
                    // suicide
                    this.onSuicide();
                } else {
                    // killed from player
                    this.onDeathFromPlayer(killer.getName());
                }
            } else if (isThisPlayer(killer.getUniqueId())) {
                // killer is this player and victim is not this player
                this.killManager.onKill();
                return;
            }
        }
        this.respawn();
    }

    private void respawn () {
        this.respawnHandler.respawn();
    }

    private void onSuicide () {
        this.scoreKeeper.addPenalty();
    }

    private void onDeathFromPlayer (final String killerName) {
        playerState.getPlayer().ifPresent(player -> {
            chat.globalMessage(formatKillMessage(killerName,
                    player.getDisplayName()));
        });
    }
    
    private String getRandomKillMessage () {
        return KILL_MESSAGES.get(random.nextInt(KILL_MESSAGES.size()));
    }

    private String formatKillMessage (final String killer, final String victim) {
        String message = getRandomKillMessage();
        message = message.replaceAll(KILLER_SEQUENCE, killer);
        message = message.replaceAll(VICTIM_SEQUENCE, victim);
        return message;
    }

    private boolean isThisPlayer (final UUID playerID) {
        return this.playerState.getPlayerID().equals(playerID);
    }
}
