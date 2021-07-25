package som.region;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;


@Builder
@Data
public class Coordinate {
    @NonNull
    private String world;
    @NonNull
    private double x;
    @NonNull
    private double y;
    @NonNull
    private double z;
    private float yaw;
    private float pitch;

    public static Coordinate fromLocation(Location location) {
        return Coordinate.builder()
                .world(location.getWorld().getName())
                .x(location.getX())
                .y(location.getY())
                .z(location.getZ())
                .yaw(location.getYaw())
                .pitch(location.getPitch())
                .build();
    }

    public boolean aboutSameVector (Vector vector) {
        Vector compare = toVector();
        return compare.getBlockX() == vector.getBlockX() &&
                compare.getBlockY() == vector.getBlockY() &&
                compare.getBlockZ() == vector.getBlockZ();
    }

    public boolean sameLocation (Location location) {
        return location.getWorld().getName().equals(world) &&
                aboutSameVector(location.toVector());
    }

    public Vector toVector () {
        return new Vector (x, y, z);
    }

    public Location toLocation() {
        final World world = Bukkit.getWorld(this.world);
        return new Location(world, x, y, z, yaw, pitch);
    }
}
