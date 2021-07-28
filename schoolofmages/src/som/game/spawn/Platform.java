package som.game.spawn;

import lombok.Data;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import som.region.Coordinate;
import som.region.Space;

@Data
public class Platform {
    final int PLATFORM_RADIUS = 4;
    final String platformType;
    final String torchType;
    final Coordinate spawn;
    final Space platform;

    public void generatePlatform () {
        for (int x = -1*PLATFORM_RADIUS; x <= PLATFORM_RADIUS; x ++) {
            for (int z = -1*PLATFORM_RADIUS; z <= PLATFORM_RADIUS; z ++) {
                Location platformBlock = new Location(getWorld(), x, platform.getMinY(), z);
                platformBlock.getBlock().setType(getPlatformType());
                if (x == -1*PLATFORM_RADIUS && z == -1*PLATFORM_RADIUS ||
                        x == PLATFORM_RADIUS && z == PLATFORM_RADIUS ||
                        x == -1*PLATFORM_RADIUS && z == PLATFORM_RADIUS ||
                        x == PLATFORM_RADIUS && z == -1*PLATFORM_RADIUS) {
                    Location torchLocation = new Location(getWorld(), x, platform.getMinY() + 1, z);
                    torchLocation.getBlock().setType(getTorchType());
                }
            }
        }
    }

    public void setWorldSpawn () {
        getWorld().setSpawnLocation(getSpawn());
    }

    private World getWorld () {
        return platform.getFirst().toLocation().getWorld();
    }

    public Location getSpawn () {
        return spawn.toLocation();
    }

    private Material getPlatformType () {
        return Material.matchMaterial(platformType);
    }

    private Material getTorchType () {
        return Material.matchMaterial(torchType);
    }
}
