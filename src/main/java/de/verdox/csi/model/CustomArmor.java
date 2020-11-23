package de.verdox.csi.model;

import de.verdox.vcore.files.Configuration;
import de.verdox.vcore.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class CustomArmor extends CustomItem{

    public CustomArmor(Configuration config) {
        super(config);
    }

    @Override
    public boolean isItemEquipped(Player player) {
        for (ItemStack armorContent : player.getInventory().getArmorContents()) {
            if(armorContent == null || armorContent.getType().equals(Material.AIR))
                continue;
            NBTItem nbtItem = new NBTItem(armorContent);
            if(identifier().equals(nbtItem.getString(IDENTIFIER)))
                return true;
        }
        return false;
    }
}
