package som.game.mage.inventory;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import som.game.spell.SpellPool;
import som.player.PlayerState;

import java.util.List;

public class StartingInventory {
    final PlayerState playerState;
    final List<ItemStack> STARTING_ITEMS = List.of(
            SpellPool.GET_SPELL_BOOK().getSpellDisplay());

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
