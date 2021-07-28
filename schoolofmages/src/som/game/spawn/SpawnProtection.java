package som.game.spawn;

import lombok.Builder;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import som.chat.Chat;
import som.chat.Log;
import som.game.Game;

public class SpawnProtection {
    final String ATTACKING_IN_PLATFORM = "You cannot attack in the platform!";
    final Chat chat = new Chat();

    public void onEntityDamageEvent (final EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (inPlatform(entity.getLocation())) {
            event.setCancelled(true);
        }
    }

    public void onEntityDamageByEntityEvent (final EntityDamageByEntityEvent event) {
        Entity victim = event.getEntity();
        Entity attacker = event.getDamager();
        if (inPlatform(victim.getLocation()) ||
                inPlatform(attacker.getLocation())) {
            chat.gameMessage(attacker.getUniqueId(), ATTACKING_IN_PLATFORM);
            event.setCancelled(true);
        }
    }

    public boolean inPlatform (final Location location) {
        return Game.GET_PLATFORM().getPlatform().inside(location);
    }
}
