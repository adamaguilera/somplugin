package som.game.spell.spells;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import som.game.spell.Spell;
import som.game.spell.SpellName;
import som.game.spell.cooldown.CooldownHandler;
import som.player.PlayerState;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ParachuteSpell extends Spell {
    final String DISPLAY_NAME = ChatColor.GREEN + "Parachute";
    final SpellName NAME = SpellName.ParachuteSpell;
    final List<String> DESCRIPTION = List.of(
            "Hold this when falling to land!",
            "Use it to get an Elytra!");
    final Material DISPLAY_TYPE = Material.BIG_DRIPLEAF;
    final int COST = 0;
    final int COOLDOWN_MILLIS = 120*1000;
    final String COOLDOWN_MSG = "Elytra on cooldown for _ more seconds!";
    final List<Material> spellItems = List.of(Material.BIG_DRIPLEAF);
    final boolean SHOULD_CANCEL_EVENT = true;
    final HashMap<UUID, CooldownHandler> players = new HashMap<>();

    @Override
    public SpellName getName() {
        return NAME;
    }

    @Override
    public String getDisplayName() { return DISPLAY_NAME; }

    @Override
    public List<String> getDescription() {
        return DESCRIPTION;
    }

    @Override
    public Material getDisplayType() {
        return DISPLAY_TYPE;
    }

    @Override
    public int getCost() {
        return COST;
    }

    @Override
    public List<Material> getSpellItems() {
        return spellItems;
    }

    @Override
    public Optional<CooldownHandler> getCooldownHandler(UUID playerID) {
        return Optional.ofNullable(players.get(playerID));
    }

    @Override
    public boolean hasSpell(UUID playerID) {
        return players.containsKey(playerID);
    }

    @Override
    public CooldownHandler initializeCooldownHandler(UUID playerID) {
        return CooldownHandler.builder()
                .spellName(getName())
                .playerID(playerID)
                .cooldown(COOLDOWN_MILLIS)
                .cooldownMessage(COOLDOWN_MSG)
                .build();
    }

    @Override
    public void addPlayer(UUID playerID) {
        players.put(playerID, initializeCooldownHandler(playerID));
    }

    @Override
    public boolean shouldCancelEvent() {
        return SHOULD_CANCEL_EVENT;
    }

    @Override
    public void cast(PlayerState playerState) {
        if (!canUseSpell(playerState)) return;
        playerState.getPlayer().ifPresent(player -> {
            if (player.getInventory().getChestplate() == null) {
                player.getInventory().setChestplate(getElytra());
            } else {
                player.getInventory().addItem(getElytra());
            }
        });
        putOnCooldown(playerState);
    }

    private ItemStack getElytra () {
        return new ItemStack(Material.ELYTRA, 1);
    }
}
