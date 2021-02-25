package me.dkim19375.groundspoofdetector.util;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.EnumSet;

public class BlockDetectingUtils {
    private static final EnumSet<BlockFace> totalFaces = EnumSet.of(BlockFace.WEST, BlockFace.EAST, BlockFace.SOUTH,
            BlockFace.NORTH, BlockFace.NORTH_WEST, BlockFace.SOUTH_WEST, BlockFace.NORTH_EAST, BlockFace.SOUTH_EAST);

    public static boolean isOnFakeGround(Player player) {
        if (!((Entity) player).isOnGround()) {
            return false;
        }
        if (BlockDetectingUtils.isOnGround(player)) {
            return false;
        }
        final Block downBlock = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        return (downBlock.getRelative(BlockFace.NORTH).getType().isAir()
                || downBlock.getRelative(BlockFace.SOUTH).getType().isAir())
                && (downBlock.getRelative(BlockFace.EAST).getType().isAir()
                || downBlock.getRelative(BlockFace.WEST).getType().isAir());
    }

    public static void forceLagBack(Player player) {
        final Location loc = DataHolder.getInstance().getLastLocations().get(player.getUniqueId());
        if (loc == null) {
            return;
        }
        if (loc.distance(player.getLocation()) < 0.3) {
            return;
        }
        player.teleport(loc);
        player.setVelocity(new Vector(0, -1, 0));
    }

    /**
     * Returns true if the entity is supported by a block.     *
     *
     * @param player The player to check the ground for
     *
     * @return True if entity is on ground.
     */
    public static boolean isOnGround(Player player) {
        final Block block = player.getLocation().getBlock();
        final Block downBlock = player.getLocation().getBlock().getRelative(BlockFace.DOWN);

        if (!block.getRelative(BlockFace.DOWN).getType().isAir()) {
            return true;
        }

        for (final BlockFace face : totalFaces) {
            if (!downBlock.getRelative(face).getType().isAir()) {
                if (isSupportedBy(player.getLocation(), face)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSupportedBy(final Location playerLoc, final BlockFace face) {
        switch (face) {
            case NORTH:
                final double northRequiredZP = 0.31;
                final double northRequiredZN = 0.69;
                final double northPlayerZ = Math.abs(playerLoc.getZ() - ((int) playerLoc.getZ()));
                if (playerLoc.getZ() < 0) {
                    if (!(northPlayerZ >= northRequiredZN)) {
                        System.out.println("northPlayerZN: " + northPlayerZ);
                    }
                    return northPlayerZ >= northRequiredZN;
                }
                if (!(northPlayerZ <= northRequiredZP)) {
                    System.out.println("northPlayerZP: " + northPlayerZ);
                }
                return northPlayerZ <= northRequiredZP;
            case EAST:
                final double eastRequiredXP = 0.69;
                final double eastRequiredXN = 0.31;
                final double eastPlayerX = Math.abs(playerLoc.getX() - ((int) playerLoc.getX()));
                if (playerLoc.getX() < 0) {
                    if (!(eastPlayerX <= eastRequiredXN)) {
                        System.out.println("eastPlayerXN: " + eastPlayerX);
                    }
                    return eastPlayerX <= eastRequiredXN;
                }
                if (!(eastPlayerX >= eastRequiredXP)) {
                    System.out.println("eastPlayerXP: " + eastPlayerX);
                }
                return eastPlayerX >= eastRequiredXP;
            case SOUTH:
                final double southRequiredZP = 0.69;
                final double southRequiredZN = 0.31;
                final double southPlayerZ = Math.abs(playerLoc.getZ() - ((int) playerLoc.getZ()));
                if (playerLoc.getZ() < 0) {
                    if (!(southPlayerZ <= southRequiredZN)) {
                        System.out.println("southPlayerZN: " + southPlayerZ);
                    }
                    return southPlayerZ <= southRequiredZN;
                }
                if (!(southPlayerZ >= southRequiredZP)) {
                    System.out.println("southPlayerZP: " + southPlayerZ);
                }
                return southPlayerZ >= southRequiredZP;
            case WEST:
                final double westRequiredXP = 0.31;
                final double westRequiredXN = 0.69;
                final double westPlayerX = Math.abs(playerLoc.getX() - ((int) playerLoc.getX()));
                if (playerLoc.getX() < 0) {
                    if (!(westPlayerX >= westRequiredXN)) {
                        System.out.println("westPlayerXN: " + westPlayerX);
                    }
                    return westPlayerX >= westRequiredXN;
                }
                if (!(westPlayerX <= westRequiredXP)) {
                    System.out.println("westPlayerXP: " + westPlayerX);
                }
                return westPlayerX <= westRequiredXP;
            case NORTH_EAST:
                return isSupportedBy(playerLoc, BlockFace.NORTH) || isSupportedBy(playerLoc, BlockFace.EAST);
            case SOUTH_EAST:
                return isSupportedBy(playerLoc, BlockFace.SOUTH) || isSupportedBy(playerLoc, BlockFace.EAST);
            case SOUTH_WEST:
                return isSupportedBy(playerLoc, BlockFace.SOUTH) || isSupportedBy(playerLoc, BlockFace.WEST);
            case NORTH_WEST:
                return isSupportedBy(playerLoc, BlockFace.NORTH) || isSupportedBy(playerLoc, BlockFace.WEST);
            default:
                return false;
        }
    }
}
