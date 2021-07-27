package som.game.mage.inventory;

import org.bukkit.inventory.ItemStack;
import som.game.spell.spells.ParachuteSpell;
import som.game.spell.spells.SpellBook;
import som.player.PlayerState;

import java.util.List;

public class StartingInventory {
    final PlayerState playerState;
    // TODO fix this bad code
    final List<ItemStack> STARTING_ITEMS = List.of(
            new SpellBook().getSpellDisplay(),
            new ParachuteSpell().getSpellDisplay());

    public StartingInventory (final PlayerState playerState) {
        this.playerState = playerState;
    }

    public void giveStartingInventory () {
        playerState.getPlayer().ifPresent(player -> {
            player.getInventory().setContents(new ItemStack[]{});
            STARTING_ITEMS.forEach(item ->
                    player.getInventory().addItem(item));
        });
    }
}
