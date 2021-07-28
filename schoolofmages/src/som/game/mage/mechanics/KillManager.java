package som.game.mage.mechanics;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import som.chat.Chat;
import som.player.PlayerState;

public class KillManager {
    final PlayerState playerState;
    final ScoreKeeper scoreKeeper;
    final Chat chat;

    public KillManager (final PlayerState playerState,
                        final ScoreKeeper scoreKeeper) {
        this.chat = new Chat();
        this.playerState = playerState;
        this.scoreKeeper = scoreKeeper;
    }

    public void onKill () {
        scoreKeeper.addKill();
        this.playerState.getPlayer().ifPresent(player ->
                player.getInventory().addItem(new ItemStack(Material.DIAMOND, 1)));
    }
}
