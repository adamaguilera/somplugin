package som.game.spell;

import lombok.Data;
import lombok.Getter;
import som.game.spell.spells.SpellBook;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class SpellPool {
    final int MAX_ITERATIONS = 100;
    final Random random = new Random();
    List<Spell> spellList;
    static SpellBook spellBook;

    public static SpellBook GET_SPELL_BOOK () {
        if (spellBook == null) {
            spellBook = new SpellBook();
        }
        return spellBook;
    }

    public List<Spell> discoverSpell (final int amount) {
        List<Spell> result = new ArrayList<>();
        for (int index = 0; index < amount; index ++) {
            getRandomSpell(result).ifPresent(result::add);
        }
        return result;
    }

    public Optional<Spell> getRandomSpell (final List<Spell> exclude) {
        if (exclude.size() >= spellList.size()) {
            return Optional.empty();
        } else {
            int index = random.nextInt(spellList.size());
            int iterations = 0;
            while (exclude.contains(spellList.get(index)) &&
                    iterations++ <= MAX_ITERATIONS) {
                index = random.nextInt(spellList.size());
            }
            return Optional.of(spellList.get(index));
        }
    }
}
