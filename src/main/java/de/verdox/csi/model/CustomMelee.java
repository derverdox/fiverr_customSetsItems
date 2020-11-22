package de.verdox.csi.model;

import de.verdox.vcore.files.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class CustomMelee extends CustomWeapon{

    public CustomMelee(Configuration config) {
        super(config);
    }

    @Override
    public boolean isItemEquipped(Player player) {
        ItemStack customItem = getItemStack();
        return player.getItemInHand().equals(customItem);
    }
}
