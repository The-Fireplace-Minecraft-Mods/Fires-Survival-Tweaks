package dev.the_fireplace.fst.logic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

import javax.annotation.Nullable;
import java.util.Collection;

public class CoordMath {
    public static BlockPos getFocalPoint(Collection<BlockPos> positions) {
        if(positions.isEmpty())
            return BlockPos.ORIGIN;
        int count = 0;
        int focalX = 0;
        int focalY = 0;
        int focalZ = 0;
        for(Vec3i position: positions) {
            count++;
            focalX += position.getX();
            focalY += position.getY();
            focalZ += position.getZ();
        }
        return new BlockPos(focalX/count, focalY/count, focalZ/count);
    }

    public static int getAverageDistanceFromFocus(Collection<BlockPos> positions, @Nullable BlockPos focalPoint) {
        if(positions.isEmpty())
            return 0;
        if(focalPoint == null)
            focalPoint = getFocalPoint(positions);
        double distance = 0;
        int count = 0;

        for(Vec3i position: positions) {
            count++;
            distance += getDistance(focalPoint, position);
        }
        return (int)Math.ceil(distance/count);
    }

    public static double getDistance(Vec3i a, Vec3i b) {
        return Math.sqrt(a.getSquaredDistance(b));
    }
}
