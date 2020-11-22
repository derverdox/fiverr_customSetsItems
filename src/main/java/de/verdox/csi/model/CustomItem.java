package de.verdox.csi.model;

import de.verdox.vcore.files.Configuration;
import de.verdox.vcore.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class CustomItem {
    public static String IDENTIFIER = "CSI_Custom_Item";
    protected Configuration config;
    private ItemStack stack;
    public static HashMap<String,CustomItem> itemCache = new HashMap<>();

    public CustomItem(Configuration config){
        this.config = config;
        itemCache.put(identifier(),this);
        if(!config.getConfig().isSet(configPath()))
            config.setItemStack(configPath(),customItem(),"CSI_Custom_Item_"+identifier());
        config.save();
        this.stack = config.getItemStack(configPath());
    }

    public ItemStack getItemStack(){
        NBTItem nbtItem = new NBTItem(stack);
        nbtItem.setString(IDENTIFIER,identifier());
        ItemStack stack = nbtItem.getItem();
        if(enchantments() != null){
            enchantments().forEach((integer, enchantment) -> {
                stack.addUnsafeEnchantment(enchantment,integer);
            });
        }
        return stack;
    }

    public static CustomItem findItem(ItemStack stack){
        if(stack == null || stack.getType().equals(Material.AIR))
            return null;
        NBTItem nbtItem = new NBTItem(stack);
        if(!nbtItem.hasKey(IDENTIFIER))
            return null;
        String identifier = nbtItem.getString(IDENTIFIER);
        return findItem(identifier);
    }

    public static CustomItem findItem(String identifier){
        return itemCache.get(identifier);
    }

    protected String configPath(){return "CustomItems."+identifier();}

    public abstract void onEquip(Player player);
    public abstract void onUnEquip(Player player);
    public abstract boolean onPlayerDeath(Player player);
    public abstract boolean isItemEquipped(Player player);
    protected abstract ItemStack customItem();
    public abstract String identifier();
    protected abstract Map<Integer,Enchantment> enchantments();
}
