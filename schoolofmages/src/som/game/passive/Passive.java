package som.game.passive;


import org.bukkit.Material;
import som.player.PlayerState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface Passive {
    HashSet<PlayerState> players = new HashSet<>();

    PassiveName getName ();
    String getDisplayName ();
    Material getDisplayType ();

    void doPassive (final PlayerState playerState);

    default void onTick () {
        players.forEach(this::doPassive);
    }

    default List<PlayerState> getPlayers() {
        return new ArrayList<PlayerState>();
    }

    default boolean addPlayer (final PlayerState playerState) {
        return players.add(playerState);
    }

    default boolean hasPlayer (final PlayerState playerState) {
        return players.contains(playerState);
    }
}
