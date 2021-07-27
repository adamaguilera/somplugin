package som.game.passive;

import som.game.passive.passives.ParachutePassive;
import som.task.Task;

import java.util.List;
import java.util.Optional;

public class PassivePool implements Task {
    List<Passive> passives = List.of(new ParachutePassive());

    /**
     * Each tick the Task Register will execute this function
     */
    @Override
    public void onTick() {
        passives.forEach(Passive::onTick);
    }

    public Optional<Passive> getPassive (PassiveName name) {
        return passives.stream()
            .filter(passive -> passive.getName() == name)
            .findFirst();
    }
}
