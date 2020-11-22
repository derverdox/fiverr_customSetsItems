package de.verdox.csi.model;

import de.verdox.vcore.files.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class CustomArmor extends CustomItem{

    public CustomArmor(Configuration config) {
        super(config);
    }

    @Override
    public boolean isItemEquipped(Player player) {
        ItemStack customItem = getItemStack();
        for (ItemStack armorContent : player.getInventory().getArmorContents()) {
            if(armorContent == null)
                continue;
            if(armorContent.equals(customItem))
                return true;
        }
        return false;
    }
}
