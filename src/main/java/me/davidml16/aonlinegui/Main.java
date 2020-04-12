package me.davidml16.aonlinegui;

import me.davidml16.aonlinegui.commands.Command_Online;
import me.davidml16.aonlinegui.gui.OnlinePlayers_GUI;
import me.davidml16.aonlinegui.utils.Colors;
import me.davidml16.aonlinegui.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static ConsoleCommandSender log;

    private MetricsLite metrics;

    private OnlinePlayers_GUI onlinePlayersGui;

    @Override
    public void onEnable() {
        metrics = new MetricsLite(this, 7108);
        log = Bukkit.getConsoleSender();

        onlinePlayersGui = new OnlinePlayers_GUI(this);

        registerCommands();

        PluginDescriptionFile pdf = getDescription();
        log.sendMessage("");
        log.sendMessage(Colors.translate("  &eAOnlineGUI Enabled!"));
        log.sendMessage(Colors.translate("    &aVersion: &b" + pdf.getVersion()));
        log.sendMessage(Colors.translate("    &aAuthor: &b" + pdf.getAuthors().get(0)));
        log.sendMessage("");

        Bukkit.getScheduler().runTaskLaterAsynchronously(this, () -> {
            try {
                Main.log.sendMessage(Colors.translate(""));
                Main.log.sendMessage(Colors.translate("  &eAOnlineGUI checking updates:"));
                new UpdateChecker(this).getVersion(version -> {
                    if (getDescription().getVersion().equalsIgnoreCase(version)) {
                        Main.log.sendMessage(Colors.translate("    &cNo update found!"));
                        Main.log.sendMessage(Colors.translate(""));
                    } else {
                        Main.log.sendMessage(Colors.translate("    &aNew update found! [" + version + "]"));
                        Main.log.sendMessage(Colors.translate(""));
                    }
                });
            }catch(Exception e) {
                Main.log.sendMessage(Colors.translate("    &cCould not proceed update-checking"));
                Main.log.sendMessage(Colors.translate(""));
            }
        }, 10);
    }

    @Override
    public void onDisable() {
        PluginDescriptionFile pdf = getDescription();
        log.sendMessage("");
        log.sendMessage(Colors.translate("  &eAOnlineGUI Disabled!"));
        log.sendMessage(Colors.translate("    &aVersion: &b" + pdf.getVersion()));
        log.sendMessage(Colors.translate("    &aAuthor: &b" + pdf.getAuthors().get(0)));
    }

    private void registerCommands() {
        getCommand("online").setExecutor(new Command_Online(this));
    }

    public OnlinePlayers_GUI getOnlinePlayersGui() { return onlinePlayersGui; }

}
