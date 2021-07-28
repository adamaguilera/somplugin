package som.game.mage.mechanics;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import som.player.PlayerState;

public class RespawnHandler {
    final PlayerState playerState;
    @Getter
    @Setter
    static Location spawn;

    public RespawnHandler (final PlayerState playerState) {
        this.playerState = playerState;
    }

    public void onPlayerRespawnEvent (final PlayerRespawnEvent event) {
        this.respawn();
    }

    public void respawn () {
        if (spawn != null) {
            playerState.getPlayer().ifPresent(player ->
                    player.teleport(spawn));
        }
    }

    public void onPlayerBedLeaveEvent (final PlayerBedLeaveEvent event) {
        event.setSpawnLocation(false);
    }
}
