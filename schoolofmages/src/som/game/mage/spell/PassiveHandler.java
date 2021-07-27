package som.game.mage.spell;

import som.game.passive.Passive;
import som.game.passive.PassiveName;
import som.game.passive.PassivePool;
import som.player.PlayerState;

import java.util.ArrayList;
import java.util.List;

public class PassiveHandler {
    final PlayerState playerState;
    final PassivePool passivePool;
    final List<Passive> passives;

    public PassiveHandler (final PlayerState playerState,
                           final PassivePool passivePool) {
        this.playerState = playerState;
        this.passives = new ArrayList<>();
        this.passivePool = passivePool;
    }

    public void addPassive (final PassiveName name) {
        passivePool.getPassive(name).ifPresent(passive -> {
            passive.addPlayer(playerState);
            passives.add(passive);
        });
    }

    public void addStartingPassives () {
        addPassive(PassiveName.ParachutePassive);
    }
}
