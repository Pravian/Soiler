package net.pravian.soiler.command;

import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import net.pravian.soiler.SeedType;
import net.pravian.soiler.Soiler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandPermissions(source = SourceType.PLAYER, usage = "/<command> <wheat | potato | carrot | random | off>", permission = "soiler.setseed")
public class Command_soil extends BukkitCommand<Soiler> {

    @Override
    protected boolean run(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (args.length != 1) {
            return showUsage();
        }

        for (SeedType type : SeedType.values()) {
            if (type.getName().equalsIgnoreCase(args[0])) {
                plugin.currentType = type;
                plugin.config.set("current", type.getName());
                plugin.config.save();

                msg(ChatColor.GREEN + "Seeding type set.");
                return true;
            }
        }

        msg(ChatColor.RED + "Unknown seeding type: " + args[0]);
        return true;
    }
}
