package som.player;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import som.chat.Chat;
import som.chat.Log;
import som.game.mage.Mage;
import som.game.passive.PassivePool;
import som.game.scoreboard.Leaderboard;
import som.game.spell.SpellPool;

import java.util.Optional;
import java.util.UUID;

public class PlayerState implements Comparable<PlayerState> {
    @Getter
    final UUID playerID;
    @Getter
    final LobbyPlayer lobbyPlayer;
    @Getter
    final Mage mage;
    @Setter @Getter
    final boolean inGame = false;

    Chat chat = new Chat();


    public PlayerState (final UUID playerID,
                        final SpellPool spellPool,
                        final PassivePool passivePool,
                        final Leaderboard leaderboard) {
        this.playerID = playerID;
        this.lobbyPlayer = new LobbyPlayer(playerID);
        this.mage = new Mage (this, spellPool, passivePool, leaderboard);
    }

    public Optional<Player> getPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(playerID));
    }


    public boolean isConnected () {
        Player optionalPlayer = Bukkit.getPlayer(playerID);
        return optionalPlayer != null && optionalPlayer.isOnline();
    }

    public boolean isLobbyReady () {
        return lobbyPlayer.isReady();
    }

    public void onAddedToLobby() {
        lobbyPlayer.enableLobbySettings();
    }

    public void removeFromLobby () {
        lobbyPlayer.disableLobbySettings();
    }

    public void clearInventory () {
        getPlayer().ifPresent(player -> {
        });
    }

    @Override
    public int compareTo(@NotNull PlayerState o) {
        return Integer.compare(getMage().getScoreKeeper().getScore(),
                o.getMage().getScoreKeeper().getScore());
    }
}
