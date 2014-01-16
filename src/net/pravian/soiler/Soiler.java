package net.pravian.soiler;

import java.io.IOException;
import net.pravian.bukkitlib.BukkitLib;
import net.pravian.bukkitlib.command.BukkitCommandHandler;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.implementation.BukkitLogger;
import net.pravian.bukkitlib.implementation.BukkitPlugin;
import net.pravian.bukkitlib.metrics.Graph;
import net.pravian.bukkitlib.metrics.Metrics;
import net.pravian.bukkitlib.metrics.Plotter;
import net.pravian.soiler.command.Command_soil;
import net.pravian.soiler.listener.BlockListener;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Soiler extends BukkitPlugin {

    public Soiler plugin;
    public BukkitLogger logger;
    public YamlConfig config;
    public BukkitCommandHandler handler;
    public SeedType currentType;

    @Override
    public void onLoad() {
        plugin = this;
        logger = new BukkitLogger(plugin);
        config = new YamlConfig(plugin, "config.yml");
        handler = new BukkitCommandHandler(plugin);
    }

    @Override
    public void onEnable() {
        BukkitLib.init(plugin);

        config.load();
        currentType = SeedType.fromName(config.getString("current"));

        handler = new BukkitCommandHandler(plugin);
        handler.setCommandLocation(Command_soil.class.getPackage());

        plugin.getServer().getPluginManager().registerEvents(new BlockListener(plugin), plugin);

        logger.info("Seeding type set to " + currentType.getName());
        logger.info(plugin.getName() + " v" + plugin.getVersion() + " by " + plugin.getAuthor() + " is enabled");

        try {
            final Metrics metrics = new Metrics(plugin);

            final Graph seedType = metrics.createGraph("Seedtype");

            seedType.addPlotter(new Plotter(StringUtils.capitalize(currentType.getName())) {
                @Override
                public int getValue() {
                    return 1;
                }
            });

            metrics.start();
        } catch (IOException ex) {
            logger.warning("Failed to submit metrics");
        }

    }

    @Override
    public void onDisable() {
        logger.info(plugin.getName() + " v" + plugin.getVersion() + " is disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        return handler.handleCommand(sender, cmd, commandLabel, args);
    }
}
