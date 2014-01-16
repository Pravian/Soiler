package net.pravian.soiler.listener;

import net.pravian.soiler.SeedType;
import net.pravian.soiler.Soiler;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockListener implements Listener {

    private final Soiler plugin;

    public BlockListener(Soiler plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (plugin.currentType == SeedType.OFF) {
            return;
        }

        if (!event.getPlayer().hasPermission("soiler.soil")) {
            return;
        }

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (!(event.getClickedBlock().getType() == Material.DIRT || event.getClickedBlock().getType() == Material.GRASS)) {
            return;
        }

        final Material mat = event.getPlayer().getItemInHand().getType();

        if (mat != Material.WOOD_HOE
                && mat != Material.STONE_HOE
                && mat != Material.IRON_HOE
                && mat != Material.GOLD_HOE
                && mat != Material.DIAMOND_HOE) {
            return;
        }

        final Block block = event.getClickedBlock().getLocation().getBlock();

        block.setType(Material.SOIL);

        if (plugin.currentType != SeedType.RANDOM) {
            block.getRelative(BlockFace.UP).setTypeIdAndData(plugin.currentType.getId(), plugin.currentType.getData(), true);
        } else {

            final SeedType type = SeedType.getRandom();

            block.getRelative(BlockFace.UP).setTypeIdAndData(type.getId(), type.getData(), true);
        }
    }
}
