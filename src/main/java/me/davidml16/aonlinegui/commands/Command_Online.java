package me.davidml16.aonlinegui.commands;

import me.davidml16.aonlinegui.Main;
import me.davidml16.aonlinegui.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class Command_Online implements CommandExecutor {

    private Main main;
    public Command_Online(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Colors.translate("&cThe commands only can be use by players!"));
            return true;
        }

        Player p = (Player) sender;

        main.getOnlinePlayersGui().open(p);

        return true;
    }

}
