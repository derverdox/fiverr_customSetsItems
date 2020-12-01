package de.verdox.csi.model.itemsets;

import de.verdox.csi.model.CustomItem;
import de.verdox.csi.model.ItemSet;
import de.verdox.vcore.files.Configuration;
import de.verdox.vcore.utils.MinecraftConstants;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class TankSet extends ItemSet {

    private float damageMultiplier;
    private double healthBonus;
    private int resistanceLevel;
    private int slownessLevel;

    public TankSet(Configuration config, List<CustomItem> customItems) {
        super(config, customItems);
        if(!config.getConfig().isSet(configPath()+".damageMultiplier"))
            config.getConfig().set(configPath()+".damageMultiplier",0.9f);
        if(!config.getConfig().isSet(configPath()+".healthBonus"))
            config.getConfig().set(configPath()+".healthBonus",7);
        if(!config.getConfig().isSet(configPath()+".resistanceLevel"))
            config.getConfig().set(configPath()+".resistanceLevel",3);
        if(!config.getConfig().isSet(configPath()+".slownessLevel"))
            config.getConfig().set(configPath()+".slownessLevel",3);
        config.save();
        this.damageMultiplier = (float) config.getConfig().getDouble(configPath()+".damageMultiplier");
        this.healthBonus = config.getConfig().getInt(configPath()+".healthBonus");
        this.resistanceLevel = config.getConfig().getInt(configPath()+".resistanceLevel");
        this.slownessLevel = config.getConfig().getInt(configPath()+".slownessLevel");
    }

    @Override
    public void onEquipFullSet(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&eYou equipped the &aTank Set&7!"));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,Integer.MAX_VALUE,resistanceLevel,false,false));
        player.setMaxHealth(MinecraftConstants.standardHealth+healthBonus);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,Integer.MAX_VALUE,slownessLevel,false,false));
    }

    @Override
    public void onUnEquipFullSet(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&eYou unequipped the &aTank Set&7!"));
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        player.setMaxHealth(MinecraftConstants.standardHealth);
        player.removePotionEffect(PotionEffectType.SLOW);
    }

    @Override
    public void onMove(PlayerMoveEvent e) { }

    @Override
    public void onDamage(EntityDamageEvent e) {
        e.setDamage(e.getFinalDamage()*damageMultiplier);
    }

    @Override
    public void onDamageByEntity(EntityDamageByEntityEvent e) {

    }

    @Override
    public void onAttack(EntityDamageByEntityEvent e) { }

    @Override
    public String identifier() {
        return "Tank_Set";
    }
}
