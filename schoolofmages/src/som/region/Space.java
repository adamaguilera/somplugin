package som.region;


import lombok.Builder;
import lombok.Getter;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

@Builder
public class Space {
    @NotNull @Getter
    private final Coordinate first;
    @NotNull
    private final Coordinate second;

    // determines whether or not the coordinate is within the space
    public boolean inside (Location location) {
        // test x then tests y then tests z
        return (cmp (first.getX(), second.getX(), location.getBlockX())) &&
                (cmp (first.getY(), second.getY(), location.getBlockY())) &&
                (cmp (first.getZ(), second.getZ(), location.getBlockZ()));
    }

    private boolean cmp (double c1, double c2, int test) {
        double min = Math.min (c1, c2);
        double max = Math.max (c1, c2);

        if (test >= min) {
            if (test <= max) {
                return true;
            }
        }
        return false;
    }

    public double getMinY() {
        return Math.min(first.getY(), second.getY());
    }

}
