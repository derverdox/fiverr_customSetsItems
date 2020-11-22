package de.verdox.csi.model.custommelees;

import de.verdox.csi.Core;
import de.verdox.csi.model.CustomMelee;
import de.verdox.vcore.files.Configuration;
import de.verdox.vcore.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SwordOfRaijn extends CustomMelee {
    public SwordOfRaijn(Configuration config) {
        super(config);
    }

    @Override
    public void onEquip(Player player) {
        player.sendMessage("You equipped the Sword Of Raijn");
    }

    @Override
    public void onUnEquip(Player player) {
        player.sendMessage("You unequipped the Sword Of Raijn");
    }

    @Override
    public boolean onPlayerDeath(Player player) {
        return false;
    }

    @Override
    protected ItemStack customItem() {
        return ItemUtil.createStack(Core.core,Material.DIAMOND_SWORD.name(), 1,(short) 0,"&eSword of Raijn","&cVery powerful sword&7!");
    }

    @Override
    public String identifier() {
        return "Sword_Of_Raijn";
    }
}
