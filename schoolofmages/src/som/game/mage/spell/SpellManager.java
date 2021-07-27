package som.game.mage.spell;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import som.game.spell.Spell;
import som.game.spell.SpellPool;
import som.game.spell.spells.ParachuteSpell;
import som.game.spell.spells.SpellBook;
import som.player.PlayerState;

import java.util.ArrayList;
import java.util.List;

public class SpellManager {
    final String SPELL_INVENTORY_NAME = "Spell Book";
    final int MAX_SPELL_INVENTORY = 9;
    final String EMPTY_SPELL_SLOT_NAME = "Empty Spell Slot";
    final List<String> EMPTY_SPELL_SLOT_LORE = List.of(
            "Learn a new spell using a spell scroll!",
            "They are dropped every 3 minutes!");
    final PlayerState playerState;
    final SpellPool spellPool;
    List<Spell> spells;

    public SpellManager (final PlayerState playerState,
                         final SpellPool spellPool) {
        this.playerState = playerState;
        this.spellPool = spellPool;
        this.spells = new ArrayList<>();
        this.addSpells(this.spellPool.getStartingSpells());
    }

    public boolean onPlayerInteractEvent (final PlayerInteractEvent event) {
        ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();
        castSpell(mainHand.getType(), event.getAction(), true);
        ItemStack offHand = event.getPlayer().getInventory().getItemInOffHand();
        castSpell(mainHand.getType(), event.getAction(), false);
        return shouldCancelEvent (mainHand.getType()) ||
                shouldCancelEvent (offHand.getType());
    }


    public void addSpells (final List<Spell> spells) {
        spells.forEach(this::addSpell);
    }

    public boolean addSpell (final Spell spell) {
        spell.addPlayer(this.playerState.getPlayerID());
        return this.spells.add(spell);
    }

    public void castSpell (final Material type, final Action action,
                           final boolean isMainHand) {
        this.spells.stream().filter(spell -> spell.itemCastsSpell(type))
                .forEach(spell -> spell.cast(playerState));
    }

    public boolean shouldCancelEvent (final Material type) {
        return this.spells.stream().filter(spell -> spell.itemCastsSpell(type))
                .anyMatch(Spell::shouldCancelEvent);
    }

    public Inventory getSpellInventory () {
        Inventory spellInventory = Bukkit.createInventory(null,
                MAX_SPELL_INVENTORY, SPELL_INVENTORY_NAME);
        int index;
        int inventoryIndex = 0;
        for (index = 0; index < this.spells.size() &&
                index < MAX_SPELL_INVENTORY;
                index ++) {
            if (!(this.spells.get(index) instanceof SpellBook) &&
                !(this.spells.get(index) instanceof ParachuteSpell)) {
                spellInventory.setItem(inventoryIndex++,
                        this.spells.get(index).getSpellDisplay());
            }
        }
        while (inventoryIndex < MAX_SPELL_INVENTORY) {
            spellInventory.setItem(inventoryIndex++, getBlankSpell());
        }
        return spellInventory;
    }

    public ItemStack getBlankSpell () {
        ItemStack blankSpell = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = blankSpell.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(EMPTY_SPELL_SLOT_NAME);
            meta.setLore(EMPTY_SPELL_SLOT_LORE);
            blankSpell.setItemMeta(meta);
        }
        return blankSpell;
    }
}
