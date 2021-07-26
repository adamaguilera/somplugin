package som.game.spawn;

import lombok.Builder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import som.game.spell.SpellPool;

import java.util.Optional;
import java.util.UUID;

@Builder
public class PlatformPlayer {
    final UUID playerID;
    final PotionEffect SLOW_FALLING = new PotionEffect(
            PotionEffectType.SLOW_FALLING,
            20*5, 0, false, false);
    final PotionEffect SHORT_RESISTANCE = new PotionEffect(
            PotionEffectType.DAMAGE_RESISTANCE,
            20*5, 100, false, false);
    final PotionEffect INFINITE_RESISTANCE = new PotionEffect(
            PotionEffectType.DAMAGE_RESISTANCE,
            Integer.MAX_VALUE, 90, false, false);
    final int AUTO_LAND_Y = 125;
    final int JUMPED_Y = 254;
    ItemStack[] storageContents;
    ItemStack[] armorContents;

    public void onSpawn () {
        if (!hasSavedInventory()) {
            saveInventory();
        }
        clearInventory();
        giveFlySetup();
    }

    public void onLand () {
        clearInventory();
        giveInventory();
        giveShortBuff();
    }

    public void checkLand () {
        if (hasSavedInventory()) {
            getPlayer().ifPresent(player -> {
                if (player.getLocation().getY() > AUTO_LAND_Y &&
                        player.getLocation().getY() <= JUMPED_Y) {
                    player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                }
                if (player.getLocation().getY() <= AUTO_LAND_Y) {
                    onLand();
                }
            });
        }
    }

    private void giveShortBuff () {
        getPlayer().ifPresent(player -> {
            player.addPotionEffect(SHORT_RESISTANCE);
            player.addPotionEffect(SLOW_FALLING);
        });
    }

    public boolean hasSavedInventory () {
        return storageContents != null ||
                armorContents != null;
    }

    private void saveInventory () {
        getPlayer().ifPresent(player -> {
            this.storageContents = player.getInventory().getStorageContents();
            this.armorContents = player.getInventory().getArmorContents();
        });
    }

    private void clearInventory () {
        getPlayer().ifPresent(player ->
                player.getInventory().setContents(new ItemStack[]{}));
    }

    private ItemStack getElytra () {
        return new ItemStack(Material.ELYTRA, 1);
    }

    private ItemStack getSpellBook () {
        return SpellPool.GET_SPELL_BOOK().getSpellDisplay();
    }

    private void giveFlySetup() {
        getPlayer().ifPresent(player -> {
            player.getInventory().setChestplate(getElytra());
            player.getInventory().addItem(getSpellBook());
            player.addPotionEffect(INFINITE_RESISTANCE);
        });
    }

    private void giveInventory () {
        if (!hasSavedInventory()) return;
        getPlayer().ifPresent(player -> {
            player.getInventory().setStorageContents(this.storageContents);
            player.getInventory().setArmorContents(this.armorContents);
            // fix their existing elytra
            if (player.getInventory().contains(Material.ELYTRA)) {
                for (ItemStack material : player.getInventory()) {
                    if (material.getType() == Material.ELYTRA) {
                        ItemMeta meta = material.getItemMeta();
                        if (meta != null) {
                            ((Damageable) meta).setDamage(0);
                            material.setItemMeta(meta);
                        }
                        break;
                    }
                }
            } else if (player.getInventory().getChestplate() == null) {
                // give them a new one they are wearing
                player.getInventory().setChestplate(getElytra());
            } else {
                // add it to their inventory
                player.getInventory().addItem(getElytra());
            }

            this.storageContents = null;
            this.armorContents = null;
        });
    }

    private Optional<Player> getPlayer () {
        return Optional.ofNullable(Bukkit.getPlayer(this.playerID));
    }

}
