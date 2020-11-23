package de.verdox.csi.model.custommelees;

import de.verdox.csi.Core;
import de.verdox.csi.model.Combo;
import de.verdox.csi.model.CustomArrow;
import de.verdox.csi.model.CustomMelee;
import de.verdox.vcore.files.Configuration;
import de.verdox.vcore.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ThrowingAxe extends CustomMelee {

    private Map<Player, Long> cache = new HashMap<>();

    private float axeThrowDamage;
    private int slowLevel;
    private int confusionLevel;
    private int slowDuration;
    private int confusionDuration;
    private int cooldown;

    public ThrowingAxe(Configuration config) {
        super(config);
        if(!config.getConfig().isSet(configPath()+".axeThrowDamage"))
            config.getConfig().set(configPath()+".axeThrowDamage",4.5f);
        if(!config.getConfig().isSet(configPath()+".slowLevel"))
            config.getConfig().set(configPath()+".slowLevel",5);
        if(!config.getConfig().isSet(configPath()+".confusionLevel"))
            config.getConfig().set(configPath()+".confusionLevel",3);
        if(!config.getConfig().isSet(configPath()+".slowDuration"))
            config.getConfig().set(configPath()+".slowDuration",1);
        if(!config.getConfig().isSet(configPath()+".confusionDuration"))
            config.getConfig().set(configPath()+".confusionDuration",5);
        if(!config.getConfig().isSet(configPath()+".cooldown"))
            config.getConfig().set(configPath()+".cooldown",5);
        this.axeThrowDamage = (float) config.getConfig().getDouble(configPath()+".axeThrowDamage");
        this.slowLevel = config.getConfig().getInt(configPath()+".slowLevel");
        this.confusionLevel = config.getConfig().getInt(configPath()+".confusionLevel");
        this.slowDuration = config.getConfig().getInt(configPath()+".slowDuration");
        this.confusionDuration = config.getConfig().getInt(configPath()+".confusionDuration");
        this.cooldown = config.getConfig().getInt(configPath()+".cooldown");
    }

    @Override
    public void onComboTriggered(Player player) {
        if(cache.containsKey(player)){
            long lastUse = cache.get(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cThis item is still on cooldown&7! + &8[&c"+ (cooldown - (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()-lastUse))) +"s&8]"));
            return;
        }
        Arrow arrow = player.getLocation().getWorld().spawnArrow(player.getEyeLocation().add(0,1,0),player.getEyeLocation().getDirection(),0.6f,12);
        CustomArrow customArrow = CustomArrow.toCustomArrow(player, null, 5, arrow, null, 1, cArrow -> {
            cArrow.getArrow().remove();
        });
        customArrow.setPotionEffects(Arrays.asList(new PotionEffect(PotionEffectType.SLOW, (int) (slowDuration*20L),slowLevel),new PotionEffect(PotionEffectType.CONFUSION, (int) (confusionDuration*20L),confusionLevel)));
        cache.put(player,System.currentTimeMillis());
        Bukkit.getScheduler().runTaskLaterAsynchronously(Core.core,() -> {
            this.cache.remove(player);
        },20L*cooldown);
    }

    @Override
    public List<Combo.Click> combo() {
        return Arrays.asList(Combo.Click.Left, Combo.Click.Left, Combo.Click.Right);
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
