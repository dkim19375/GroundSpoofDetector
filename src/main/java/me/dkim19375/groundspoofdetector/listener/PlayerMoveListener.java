package me.dkim19375.groundspoofdetector.listener;

import me.dkim19375.groundspoofdetector.event.GroundSpoofEvent;
import me.dkim19375.groundspoofdetector.util.BlockDetectingUtils;
import me.dkim19375.groundspoofdetector.util.DataHolder;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    public PlayerMoveListener() {}

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerMoveEvent(PlayerMoveEvent e) {
        if (!((Entity) e.getPlayer()).isOnGround()) {
            return;
        }
        if (BlockDetectingUtils.isOnGround(e.getPlayer())) {
            DataHolder.getInstance().getLastLocations().put(e.getPlayer().getUniqueId(), e.getPlayer().getLocation());
            return;
        }
        final Block downBlock = e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
        if ((!downBlock.getRelative(BlockFace.NORTH).getType().isAir()
                && !downBlock.getRelative(BlockFace.SOUTH).getType().isAir())
                || (!downBlock.getRelative(BlockFace.EAST).getType().isAir()
                && !downBlock.getRelative(BlockFace.WEST).getType().isAir())) {
            return;
        }
        if (DataHolder.getInstance().getLastLocations().containsKey(e.getPlayer().getUniqueId())) {
            Bukkit.getPluginManager().callEvent(new GroundSpoofEvent(e.getPlayer(), e));
        }
    }
}