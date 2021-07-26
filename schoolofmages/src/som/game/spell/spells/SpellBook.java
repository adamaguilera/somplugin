package som.game.spell.spells;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import som.game.spell.Spell;
import som.game.spell.cooldown.CooldownHandler;
import som.player.PlayerState;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SpellBook extends Spell {
    final String NAME = ChatColor.GOLD + "Spell Book";
    final List<String> DESCRIPTION = List.of("Use this to see all your spells!");
    final Material DISPLAY_TYPE = Material.ENCHANTED_BOOK;
    final int COST = 0;
    final int COOLDOWN_MILLIS = 100;
    final List<Material> spellItems = List.of(Material.ENCHANTED_BOOK);
    final HashMap<UUID, CooldownHandler> players = new HashMap<>();

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public List<String> getDescription() {
        return DESCRIPTION;
    }

    @Override
    public Material getDisplayType () {
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
    public Optional<CooldownHandler> getCooldownHandler(final UUID playerID) {
        return Optional.ofNullable(players.get(playerID));
    }

    @Override
    public boolean hasSpell(final UUID playerID) {
        return players.containsKey(playerID);
    }

    @Override
    public void addPlayer(final UUID playerID) {
        this.players.put(playerID, CooldownHandler.builder()
                .spellName(getName())
                .playerID(playerID)
                .cooldown(COOLDOWN_MILLIS)
                .build());
    }

    @Override
    public void cast(final PlayerState playerState) {
        if (!canUseSpell(playerState)) return;
        Inventory spellInventory = playerState.getMage()
                .getSpellManager()
                .getSpellInventory();
        playerState.getMage()
                .getInventoryHandler()
                .getViewingInventory()
                .viewInventory(spellInventory);
    }
}
