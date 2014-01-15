package net.darthcraft.soiler;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class BlockListener implements Listener {
    
    private Soiler plugin;
    
    public BlockListener(Soiler plugin) {
        this.plugin = plugin;
    }



    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (plugin.currentType == SoilerType.OFF) {
            return;
        }
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (!(event.getClickedBlock().getType() == Material.DIRT || event.getClickedBlock().getType() == Material.GRASS)) {
            return;
        }
        if (!event.getPlayer().hasPermission("soiler.soil")) {
            return;
        }
        Material mat = event.getPlayer().getItemInHand().getType();
        if (mat == Material.WOOD_HOE || mat == Material.STONE_HOE || mat == Material.IRON_HOE || mat == Material.GOLD_HOE || mat == Material.DIAMOND_HOE) {
            Location location = event.getClickedBlock().getLocation();
            location.setY(location.getY() + 1);
            location.getWorld().getBlockAt(event.getClickedBlock().getLocation()).setTypeId(60); // farmland
            SoilerType seed = plugin.getSeed();
            location.getWorld().getBlockAt(location).setTypeId(seed.getId(), true);
            location.getWorld().getBlockAt(location).setData(seed.getData(), true);
        }
    }
}
