package dev.the_fireplace.fst.logic;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;

import javax.annotation.Nullable;
import java.util.Collection;

public final class CoordMath {
    public static BlockPos getFocalPoint(Collection<? extends Vec3i> positions) {
        if (positions.isEmpty()) {
            return BlockPos.ZERO;
        }
        int count = positions.size();
        int focalX = 0;
        int focalY = 0;
        int focalZ = 0;
        for (Vec3i position: positions) {
            focalX += position.getX();
            focalY += position.getY();
            focalZ += position.getZ();
        }

        return new BlockPos(focalX/count, focalY/count, focalZ/count);
    }

    public static int getAverageDistanceFromFocus(Collection<? extends Vec3i> positions, @Nullable Vec3i focalPoint) {
        if (positions.isEmpty()) {
            return 0;
        }
        if (focalPoint == null) {
            focalPoint = getFocalPoint(positions);
        }

        double distance = 0;
        int count = positions.size();

        for (Vec3i position: positions) {
            distance += getDistance(focalPoint, position);
        }

        return (int)Math.ceil(distance/count);
    }

    public static double getDistance(Vec3i a, Vec3i b) {
        return Math.sqrt(a.distSqr(b));
    }
}
