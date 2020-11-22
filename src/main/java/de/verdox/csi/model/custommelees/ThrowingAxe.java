package de.verdox.csi.model.custommelees;

import de.verdox.csi.Core;
import de.verdox.csi.model.CustomMelee;
import de.verdox.vcore.files.Configuration;
import de.verdox.vcore.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ThrowingAxe extends CustomMelee {
    public ThrowingAxe(Configuration config) {
        super(config);
    }

    @Override
    public void onEquip(Player player) {

    }

    @Override
    public void onUnEquip(Player player) {

    }

    @Override
    public boolean onPlayerDeath(Player player) {
        return false;
    }

    @Override
    protected ItemStack customItem() {
        return ItemUtil.createStack(Core.core,Material.DIAMOND_AXE.name(), 1,(short) 0,"&eThrowing Axe","&cVery powerful axe&7!");
    }

    @Override
    public String identifier() {
        return "ThrowingAxe";
    }

    @Override
    protected Map<Integer, Enchantment> enchantments() {
        Map<Integer,Enchantment> enchantments = new HashMap<>();
        enchantments.put(6,Enchantment.DAMAGE_ALL);
        enchantments.put(5,Enchantment.DURABILITY);
        return enchantments;
    }
}
