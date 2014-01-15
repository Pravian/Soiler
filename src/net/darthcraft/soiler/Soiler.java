package net.darthcraft.soiler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class Soiler extends JavaPlugin {

    public Soiler plugin;
    private FileConfiguration customConfig = null;
    private File customConfigFile = null;
    public SoilerType currentType;

    @Override
    public void onEnable() {

        /* ---------------------------------------------
         * Dear BukkitDev Staffmember,
         * Thanks for all the great work you are doing.
         * You guys are awesome, keep it up! :)
         * Love, Prozza
         * --------------------------------------------- */
        
        plugin = this;
        String type = getCustomConfig().getString("current");
        if (SoilerType.getType(type) == null) {
            plugin.error("Could not resolve type: " + getCustomConfig().getString("current"));
            plugin.error("Disabling...");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }
        currentType = SoilerType.getType(getCustomConfig().getString("current"));

        plugin.log("Soiler Type set to: " + SoilerType.getType(getCustomConfig().getString("current")).toString());
        plugin.getServer().getPluginManager().registerEvents(new BlockListener(plugin), plugin);
        plugin.log("Soiler v1.0 by DarthSalamon enabled");
    }

    @Override
    public void onDisable() {
        plugin.log("Soiler v1.0 by DarthSalamon disabled");
    }

    public void log(String msg) {
        plugin.getLogger().info(msg);
    }

    public void warning(String msg) {
        plugin.getLogger().warning(msg);
    }

    public void error(String msg) {
        plugin.getLogger().severe(msg);
    }

    public SoilerType getSeed() {
        if (currentType == SoilerType.ALL) {
            int random = 1 + (int)(Math.random()*3); // 1,2,3 random
            if (random == 1) { return SoilerType.CARROT; }
            if (random == 2) { return SoilerType.WHEAT; }
            if (random == 3) { return SoilerType.POTATO; }
            plugin.error("Could not get random number: " + random);
            return null;
        } else {
            return currentType;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("autoseed")) {
            if (args.length != 1) {
                return false;
            }
            if (sender instanceof Player && !sender.hasPermission("soiler.setseed")) {
                sender.sendMessage(ChatColor.RED + "You don't have permissions to use that command.");
                return true;
            }
            for (SoilerType type : SoilerType.values()) {
                if (type.toString().equalsIgnoreCase(args[0])) {
                    currentType = type;
                    getCustomConfig().set("current", (String)type.toString());
                    saveCustomConfig();
                    sender.sendMessage(ChatColor.GREEN + "Seeding type set.");
                    return true;
                }
            }
            sender.sendMessage(ChatColor.RED + "Could not set Seeding type: " + args[0]);
            return true;

        }
        return false;
    }
    public void reloadCustomConfig() {
        if (customConfigFile == null) {
            customConfigFile = new File(getDataFolder(), "config.yml");
        }
        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);

        // Look for defaults in the jar
        InputStream defConfigStream = this.getResource("config.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            customConfig.setDefaults(defConfig);
        }
    }
    public FileConfiguration getCustomConfig() {
        if (customConfig == null) {
            this.reloadCustomConfig();
        }
        return customConfig;
    }
    public void saveCustomConfig() {
        if (customConfig == null || customConfigFile == null) {
            return;
        }
        try {
            getCustomConfig().save(customConfigFile);
        } catch (IOException ex) {
            this.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
        }
    }
}
