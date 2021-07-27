package som.game.passive.passives;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import som.game.passive.Passive;
import som.game.passive.PassiveName;
import som.game.spell.SpellPool;
import som.player.PlayerState;

public class ParachutePassive implements Passive {
    final PassiveName NAME = PassiveName.ParachutePassive;
    final Material TYPE = Material.BIG_DRIPLEAF;
    final String DISPLAY_NAME = "Parachute";
    final PotionEffect PARACHUTE = new PotionEffect(PotionEffectType.SLOW_FALLING,
    20*2, 0, true, false);

    @Override
    public PassiveName getName() {
        return NAME;
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public Material getDisplayType() {
        return TYPE;
    }

    @Override
    public void doPassive(PlayerState playerState) {
        playerState.getPlayer().ifPresent(player -> {
            if (playerState.getMage()
                    .getInventoryHandler()
                    .holdingItem(TYPE)) {
                player.addPotionEffect(PARACHUTE);
            }
        });
    }
}
