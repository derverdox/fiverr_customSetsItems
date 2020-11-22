package de.verdox.csi.model.customarmor;

import de.verdox.csi.Core;
import de.verdox.csi.model.CustomArmor;
import de.verdox.vcore.files.Configuration;
import de.verdox.vcore.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IceKingLeggings extends CustomArmor {
    public IceKingLeggings(Configuration config) {
        super(config);
    }

    @Override
    public void onEquip(Player player) {}

    @Override
    public void onUnEquip(Player player) {}

    @Override
    public boolean onPlayerDeath(Player player) {
        return false;
    }

    @Override
    protected ItemStack customItem() {
        return ItemUtil.createStack(Core.core, Material.DIAMOND_LEGGINGS.name(), 1,(short) 0,"&bLeggings of the Ice King","&cVery powerful leggings&7!");
    }

    @Override
    public String identifier() {
        return "IceKing_Leggings";
    }
}
