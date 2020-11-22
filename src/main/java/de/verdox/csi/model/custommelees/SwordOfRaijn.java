package de.verdox.csi.model.custommelees;

import de.verdox.csi.Core;
import de.verdox.csi.model.CustomMelee;
import de.verdox.vcore.files.Configuration;
import de.verdox.vcore.utils.ItemUtil;
import de.verdox.vcore.utils.SecondsConverter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SwordOfRaijn extends CustomMelee {

    private Map<Player, Arrow> arrowCache = new HashMap<>();
    private Set<Player> cooldownCache = new HashSet<>();
    private long lastUse;

    private int cooldownInSeconds;

    public SwordOfRaijn(Configuration config) {
        super(config);
        if(!config.getConfig().isSet(configPath()+".cooldownInSeconds"))
            config.getConfig().set(configPath()+".cooldownInSeconds",60);
        this.cooldownInSeconds = config.getConfig().getInt(configPath()+".cooldownInSeconds");
    }

    @Override
    public void onRightClick(Player player) {
        System.out.println("Triggered MiddleClick");
        if(arrowCache.containsKey(player)){
            player.teleport(arrowCache.get(player).getLocation().add(0,2,0));
        }
        else {
            if(cooldownCache.contains(player)){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cItem is still on cooldown! &8["+ SecondsConverter.convertSeconds(cooldownInSeconds-(System.currentTimeMillis()-lastUse)) +"&8]"));
                return;
            }
            Arrow arrow = player.getLocation().getWorld().spawnArrow(player.getLocation(),player.getLocation().getDirection(),0.6f,12);
            arrowCache.put(player,arrow);
            cooldownCache.add(player);
            lastUse = System.currentTimeMillis();
            Bukkit.getScheduler().runTaskLaterAsynchronously(Core.core,() -> {
                cooldownCache.remove(player);
            },20L*cooldownInSeconds);
        }
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
        return ItemUtil.createStack(Core.core,Material.DIAMOND_SWORD.name(), 1,(short) 0,"&eSword of Raijn","&cVery powerful sword&7!");
    }

    @Override
    public String identifier() {
        return "Sword_Of_Raijn";
    }

    @Override
    protected Map<Integer, Enchantment> enchantments() {
        Map<Integer,Enchantment> enchantments = new HashMap<>();
        enchantments.put(6,Enchantment.DAMAGE_ALL);
        enchantments.put(5,Enchantment.DURABILITY);
        return enchantments;
    }
}
