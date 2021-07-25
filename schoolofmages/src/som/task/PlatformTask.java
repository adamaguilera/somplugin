package som.task;

import lombok.Builder;
import som.game.spawn.PlatformPlayer;
import som.player.PlayerManager;
import som.player.PlayerState;

@Builder
public class PlatformTask implements Task {
    PlayerManager playerManager;
    /**
     * Each tick the Task Register will execute this function
     */
    @Override
    public void onTick() {
        playerManager.getAllPlayers().stream()
                .map(PlayerState::getPlatformPlayer)
                .filter(PlatformPlayer::hasSavedInventory)
                .forEach(PlatformPlayer::checkLand);
    }
}
