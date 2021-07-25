package som.player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {
    HashMap<UUID, PlayerState> players = new HashMap<>();

    public PlayerState initializePlayer (final UUID playerID) {
        PlayerState player = new PlayerState(playerID);
        // must add to players map
        return players.put(playerID, player);
    }

    public PlayerState getPlayerState (final UUID playerID) {
        if (players.containsKey(playerID)) {
            return players.get(playerID);
        }
        return initializePlayer(playerID);
    }
}
