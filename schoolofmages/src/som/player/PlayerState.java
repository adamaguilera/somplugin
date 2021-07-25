package som.player;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import som.chat.Chat;

import java.util.Optional;
import java.util.UUID;

public class PlayerState {
    @Getter
    final UUID playerID;
    @Getter
    final LobbyPlayer lobbyPlayer;

    Chat chat = new Chat();


    public PlayerState (final UUID playerID) {
        this.playerID = playerID;
        this.lobbyPlayer = new LobbyPlayer(playerID);

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

}
