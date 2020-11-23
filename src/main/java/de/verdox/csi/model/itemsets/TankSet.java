package de.verdox.csi.model.itemsets;

import de.verdox.csi.model.CustomItem;
import de.verdox.csi.model.ItemSet;
import de.verdox.vcore.files.Configuration;
import de.verdox.vcore.utils.MinecraftConstants;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class TankSet extends ItemSet {

    private float damageMultiplier;
    private int healthBoostLevel;
    private int resistanceLevel;
    private int slownessLevel;

    public TankSet(Configuration config, List<CustomItem> customItems) {
        super(config, customItems);
        if(!config.getConfig().isSet(configPath()+".damageMultiplier"))
            config.getConfig().set(configPath()+".damageMultiplier",0.9f);
        if(!config.getConfig().isSet(configPath()+".healthBoostLevel"))
            config.getConfig().set(configPath()+".healthBoostLevel",1);
        if(!config.getConfig().isSet(configPath()+".resistanceLevel"))
            config.getConfig().set(configPath()+".resistanceLevel",3);
        if(!config.getConfig().isSet(configPath()+".slownessLevel"))
            config.getConfig().set(configPath()+".slownessLevel",3);
        config.save();
        this.damageMultiplier = (float) config.getConfig().getDouble(configPath()+".damageMultiplier");
        this.healthBoostLevel = config.getConfig().getInt(configPath()+".healthBoostLevel");
        this.resistanceLevel = config.getConfig().getInt(configPath()+".resistanceLevel");
        this.slownessLevel = config.getConfig().getInt(configPath()+".slownessLevel");
    }

    @Override
    public void onEquipFullSet(Player player) {
        System.out.println("You eqipped "+identifier());
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,Integer.MAX_VALUE,healthBoostLevel,false,false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,Integer.MAX_VALUE,resistanceLevel,false,false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,Integer.MAX_VALUE,slownessLevel,false,false));
    }

    @Override
    public void onUnEquipFullSet(Player player) {
        System.out.println("You unequipped "+identifier());
        player.setWalkSpeed(MinecraftConstants.defaultPlayerWalkSpeed);
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
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
