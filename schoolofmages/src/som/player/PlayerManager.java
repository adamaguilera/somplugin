package som.player;

import som.game.passive.PassivePool;
import som.game.spell.SpellPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerManager {
    HashMap<UUID, PlayerState> players;
    final SpellPool spellPool;
    final PassivePool passivePool;

    public PlayerManager (final SpellPool spellPool,
                            final PassivePool passivePool) {
        this.players = new HashMap<>();
        this.spellPool = spellPool;
        this.passivePool = passivePool;
    }

    public PlayerState initializePlayer (final UUID playerID) {
        PlayerState player = new PlayerState(playerID, spellPool, passivePool);
        // must add to players map
        return players.put(playerID, player);
    }

    public PlayerState getPlayerState (final UUID playerID) {
        if (players.containsKey(playerID)) {
            return players.get(playerID);
        }
        return initializePlayer(playerID);
    }

    public List<PlayerState> getAllPlayers () {
        return new ArrayList<>(players.values());
    }

    public List<PlayerState> getPlayersByRank() {
        List<PlayerState> allPlayers = getAllPlayers();
        Collections.sort(allPlayers);
        return allPlayers;

    }
}
