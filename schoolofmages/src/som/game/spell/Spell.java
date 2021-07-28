package som.game.spell;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import som.game.mage.spell.ManaHandler;
import som.game.spell.cooldown.CooldownHandler;
import som.player.PlayerState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class Spell {
    public abstract SpellName getName ();
    public abstract String getDisplayName();
    public abstract List<String> getDescription ();
    public abstract Material getDisplayType ();
    public abstract int getCost ();
    public abstract List<Material> getSpellItems ();
    public abstract Optional<CooldownHandler> getCooldownHandler (final UUID playerID);
    public abstract boolean hasSpell (final UUID playerID);
    public abstract CooldownHandler initializeCooldownHandler (final UUID playerID);
    public abstract void addPlayer (final UUID playerID);
    public abstract boolean shouldCancelEvent ();
    public abstract void cast (final PlayerState playerState);


    public ItemStack getSpellDisplay () {
        ItemStack display = new ItemStack(getDisplayType(), 1);
        ItemMeta meta = display.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(getDisplayName());
            meta.setLore(getDescription());
            display.setItemMeta(meta);
        }
        return display;
    }

    public boolean itemCastsSpell (final Material material) {
        return this.getSpellItems() != null &&
                this.getSpellItems().contains(material);
    }

    public boolean canUseSpell (final PlayerState playerState) {
        return hasSpell(playerState.getPlayerID()) &&
                getManaHandler(playerState).hasFood(getCost()) &&
                getCooldownHandler(playerState.getPlayerID()).isPresent() &&
                !getCooldownHandler(playerState.getPlayerID()).get().onCooldown();
    }

    public void putOnCooldown (final PlayerState playerState) {
        getCooldownHandler(playerState.getPlayerID())
                .ifPresent(CooldownHandler::putOnCooldown);
    }
    private ManaHandler getManaHandler (final PlayerState playerState) {
        return playerState.getMage().getManaHandler();
    }
}
