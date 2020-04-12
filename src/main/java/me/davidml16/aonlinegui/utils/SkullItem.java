package me.davidml16.aonlinegui.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullItem {

    public static ItemStack playerHead(Player p) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
        skullmeta.setOwner(p.getName());
        skullmeta.setDisplayName(Colors.translate("&a" + p.getDisplayName()));
        skull.setItemMeta(skullmeta);
        return skull;
    }

}
