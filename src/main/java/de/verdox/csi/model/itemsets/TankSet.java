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

    public TankSet(Configuration config, List<CustomItem> customItems) {
        super(config, customItems);
        if(!config.getConfig().isSet(configPath()+".damageMultiplier"))
            config.getConfig().set(configPath()+".damageMultiplier",0.9f);
        config.save();
        this.damageMultiplier = (float) config.getConfig().getDouble(configPath()+".damageMultiplier");
    }

    @Override
    public void onEquipFullSet(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,Integer.MAX_VALUE,3,true,false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,Integer.MAX_VALUE,3,true,false));
    }

    @Override
    public void onUnEquipFullSet(Player player) {
        player.setWalkSpeed(MinecraftConstants.defaultPlayerWalkSpeed);
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
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
