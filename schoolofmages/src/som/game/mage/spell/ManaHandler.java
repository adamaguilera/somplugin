package som.game.mage.spell;

import lombok.Builder;
import org.bukkit.entity.Player;
import som.player.PlayerState;

import java.util.Optional;

@Builder
public class ManaHandler {
    final PlayerState playerState;

    private Optional<Player> getPlayer () {
        return playerState.getPlayer();
    }
    public boolean hasFood (int requirement) {
        return getPlayer()
                .filter(player -> player.getFoodLevel() >= requirement)
                .isPresent();
    }

    public void useFood (int amount) {
        getPlayer().ifPresent(player ->
                player.setFoodLevel(Math.max(player.getFoodLevel() - amount, 0)));
    }
}
