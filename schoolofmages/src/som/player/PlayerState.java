package som.player;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import som.chat.Chat;
import som.game.mage.Mage;
import som.game.spawn.PlatformPlayer;

import java.util.Optional;
import java.util.UUID;

public class PlayerState implements Comparable<PlayerState> {
    @Getter
    final UUID playerID;
    @Getter
    final LobbyPlayer lobbyPlayer;
    @Getter
    final PlatformPlayer platformPlayer;
    @Getter
    final Mage mage = new Mage();
    @Setter @Getter
    final boolean inGame = false;

    Chat chat = new Chat();


    public PlayerState (final UUID playerID) {
        this.playerID = playerID;
        this.lobbyPlayer = new LobbyPlayer(playerID);
        this.platformPlayer = PlatformPlayer.builder()
                .playerID(playerID)
                .build();
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
        return Integer.compare(getMage().getKills(),
                o.getMage().getKills());
    }
}
