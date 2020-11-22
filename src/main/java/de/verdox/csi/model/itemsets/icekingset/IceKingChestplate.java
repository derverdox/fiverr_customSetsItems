package de.verdox.csi.model.itemsets.icekingset;

import de.verdox.csi.Core;
import de.verdox.csi.model.CustomArmor;
import de.verdox.vcore.files.Configuration;
import de.verdox.vcore.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class IceKingChestplate extends CustomArmor {
    public IceKingChestplate(Configuration config) {
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
        ItemStack stack = ItemUtil.createStack(Core.core, Material.DIAMOND_CHESTPLATE.name(), 1,(short) 0,"&bChestplate of the Ice King","&cVery powerful chestplate&7!");
        stack.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE,7);
        stack.addUnsafeEnchantment(Enchantment.DURABILITY,6);
        return stack;
    }

    @Override
    public String identifier() {
        return "IceKing_Chestplate";
    }

    @Override
    protected Map<Integer, Enchantment> enchantments() {
        Map<Integer,Enchantment> enchantments = new HashMap<>();
        enchantments.put(7,Enchantment.PROTECTION_ENVIRONMENTAL);
        enchantments.put(6,Enchantment.DURABILITY);
        return enchantments;
    }
}
