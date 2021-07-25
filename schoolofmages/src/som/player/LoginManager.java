package som.player;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;
import som.game.mage.Mage;
import som.lobby.Lobby;
import lombok.Builder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

@Builder
public class LoginManager implements Listener {
    final PlayerManager playerManager;
    final Lobby lobby;

    @EventHandler
    public void onPlayerLoginEvent (final PlayerLoginEvent login) {
        Player player = login.getPlayer();
        UUID playerID = login.getPlayer().getUniqueId();
        PlayerState playerState = playerManager.getPlayerState(playerID);
    }

    @EventHandler
    public void onPlayerJoinEvent (final PlayerJoinEvent login) {
        Player player = login.getPlayer();
        UUID playerID = login.getPlayer().getUniqueId();
        PlayerState playerState = playerManager.getPlayerState(playerID);
        this.login(playerState);
    }

    @EventHandler
    public void onPlayerQuitEvent (final PlayerQuitEvent quit) {
        Player player = quit.getPlayer();
        UUID playerID = quit.getPlayer().getUniqueId();
        PlayerState playerState = playerManager.getPlayerState(playerID);

    }

    // TODO: implement
    private void disconnectKill (final Mage mage) {

    }

    private void logout (final Player player) {

    }

    private void login (final PlayerState playerState) {
        if (lobby.isEnabled()) {
            lobby.addPlayer(playerState);
        } else {

        }
    }


}
