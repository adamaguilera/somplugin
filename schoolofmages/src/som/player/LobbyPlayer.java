package som.player;

import org.bukkit.inventory.ItemStack;
import som.chat.Chat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class LobbyPlayer {
    final String JOINED_LOBBY = "You have joined the lobby!";
    final String READY_MSG = "You are now ready!";
    final String UNREADY_MSG = "You are no longer ready!";
    final String NOT_IN_LOBBY_MSG = "You are not in the lobby!";

    final UUID playerID;

    Chat chat = new Chat();

    boolean inLobby = false;

    @Getter
    boolean isReady = false;

    private Optional<Player> getPlayer () {
        return Optional.ofNullable(Bukkit.getPlayer(this.playerID));
    }

    public void enableLobbySettings () {
        this.inLobby = true;
        getPlayer().ifPresent(player -> {
            player.getInventory().setContents(new ItemStack[]{});
            player.setGameMode(GameMode.SPECTATOR);
        });
        chat.commandMessage(this.playerID, JOINED_LOBBY);
    }

    public void disableLobbySettings () {
        this.inLobby = false;
        getPlayer().ifPresent(player -> {
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().setContents(new ItemStack[]{});
            player.setHealth(20.0);
            player.setFoodLevel(20);
            player.setSaturation(3.0f);
            player.setSaturatedRegenRate(0);
        });
    }

    public void ready () {
        if (!inLobby) {
            chat.commandMessage(this.playerID, NOT_IN_LOBBY_MSG);
            return;
        }
        if (!isReady) {
            isReady = true;
            chat.commandMessage(this.playerID, READY_MSG);
        } else {
            isReady = false;
            chat.commandMessage(this.playerID, UNREADY_MSG);
        }
    }
}
