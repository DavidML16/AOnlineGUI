package me.davidml16.aonlinegui.gui;

import me.davidml16.aonlinegui.Main;
import me.davidml16.aonlinegui.utils.Colors;
import me.davidml16.aonlinegui.utils.ItemBuilder;
import me.davidml16.aonlinegui.utils.SkullItem;
import me.davidml16.aonlinegui.utils.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class OnlinePlayers_GUI implements Listener {

    private HashMap<UUID, Integer> opened;
    private List<Integer> borders;

    private Main main;

    public OnlinePlayers_GUI(Main main) {
        this.main = main;
        this.opened = new HashMap<UUID, Integer>();
        this.borders = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 40, 42, 43, 44);
        this.main.getServer().getPluginManager().registerEvents(this, this.main);
    }

    public HashMap<UUID, Integer> getOpened() {
        return opened;
    }

    private void openPage(Player p, int page) {
        List<Player> players = new ArrayList<Player>(main.getServer().getOnlinePlayers());

        if(page > 0 && players.size() < (page * 21) + 1) {
            openPage(p, page - 1);
            return;
        }

        Inventory gui = Bukkit.createInventory(null, 45, "Online Players ()");

        ItemStack edge = new ItemBuilder(Material.STAINED_GLASS_PANE, 1).setDurability((short) 7).setName("").toItemStack();
        ItemStack back = new ItemBuilder(Material.ARROW, 1).setName(Colors.translate("&aBack to config")).toItemStack();

        for (Integer i : borders) {
            gui.setItem(i, edge);
        }

        gui.setItem(40, back);

        for (int i = 10; i <= 16; i++)
            gui.setItem(i, null);
        for (int i = 19; i <= 25; i++)
            gui.setItem(i, null);
        for (int i = 28; i <= 34; i++)
            gui.setItem(i, null);

        if (page > 0) {
            gui.setItem(18, new ItemBuilder(Material.ENDER_PEARL, 1).setName(Colors.translate("&aPrevious page")).toItemStack());
        } else {
            gui.setItem(18, new ItemBuilder(Material.STAINED_GLASS_PANE, 1).setDurability((short) 7).setName("").toItemStack());
        }

        if (players.size() > (page + 1) * 21) {
            gui.setItem(26, new ItemBuilder(Material.ENDER_PEARL, 1).setName(Colors.translate("&aNext page")).toItemStack());
        } else {
            gui.setItem(26, new ItemBuilder(Material.STAINED_GLASS_PANE, 1).setDurability((short) 7).setName("").toItemStack());
        }

        if (players.size() > 21) players = players.subList(page * 21, Math.min(((page * 21) + 21), players.size()));

        if(players.size() > 0) {
            int iterator = (page * 21) + 1;
            for (Player player : players) {
                gui.addItem(SkullItem.playerHead(player));
            }
        } else {
            gui.setItem(22, new ItemBuilder(Material.STAINED_GLASS_PANE, 1).setDurability((short) 14).setName(Colors.translate("&cNo online players")).toItemStack());
        }

        if (!opened.containsKey(p.getUniqueId())) {
            p.openInventory(gui);
        } else {
            p.getOpenInventory().getTopInventory().setContents(gui.getContents());
        }

        Bukkit.getScheduler().runTaskLaterAsynchronously(main, () -> opened.put(p.getUniqueId(), page), 1L);
    }

    public void open(Player p) {
        p.updateInventory();
        openPage(p, 0);

        Sounds.playSound(p, p.getLocation(), Sounds.MySound.CLICK, 10, 2);
        Bukkit.getScheduler().runTaskLaterAsynchronously(main, () -> opened.put(p.getUniqueId(), 0), 1L);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) return;

        if (opened.containsKey(p.getUniqueId())) {
            e.setCancelled(true);
            int slot = e.getRawSlot();
            if (slot == 18 && e.getCurrentItem().getType() == Material.ENDER_PEARL) {
                Sounds.playSound(p, p.getLocation(), Sounds.MySound.CLICK, 10, 2);
                openPage(p, opened.get(p.getUniqueId()) - 1);
            } else if (slot == 26 && e.getCurrentItem().getType() == Material.ENDER_PEARL) {
                Sounds.playSound(p, p.getLocation(), Sounds.MySound.CLICK, 10, 2);
                openPage(p, opened.get(p.getUniqueId()) + 1);
            } else if (slot == 40) {
                p.closeInventory();
            }
        }
    }

    @EventHandler
    public void InventoryCloseEvent(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        opened.remove(p.getUniqueId());
    }

}