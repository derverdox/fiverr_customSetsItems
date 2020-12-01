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
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.List;

public class SpeedsterSet extends ItemSet {
    private float walkSpeedMultiplier;
    private float damageMultiplier;
    private float potionDamageMultiplier;
    public SpeedsterSet(Configuration config, List<CustomItem> customItems) {
        super(config, customItems);
        if(!config.getConfig().isSet(configPath()+".walkSpeedMultiplier"))
            config.getConfig().set(configPath()+".walkSpeedMultiplier",1.25f);
        if(!config.getConfig().isSet(configPath()+".damageMultiplier"))
            config.getConfig().set(configPath()+".damageMultiplier",1.10f);
        if(!config.getConfig().isSet(configPath()+".potionDamageMultiplier"))
            config.getConfig().set(configPath()+".potionDamageMultiplier",1.10f);
        this.walkSpeedMultiplier = (float) config.getConfig().getDouble(configPath()+".walkSpeedMultiplier");
        this.damageMultiplier = (float) config.getConfig().getDouble(configPath()+".damageMultiplier");
        this.potionDamageMultiplier = (float) config.getConfig().getDouble(configPath()+".potionDamageMultiplier");
    }

    @Override
    public void onEquipFullSet(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&eYou equipped the &aSpeedster Set&7!"));
        player.setWalkSpeed(MinecraftConstants.defaultPlayerWalkSpeed*walkSpeedMultiplier);
    }

    @Override
    public void onUnEquipFullSet(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&eYou equipped the &aSpeedster Set&7!"));
        player.setWalkSpeed(MinecraftConstants.defaultPlayerWalkSpeed);
    }

    @Override
    public void onMove(PlayerMoveEvent e) {

    }

    @Override
    public void onDamage(EntityDamageEvent e) {
        e.setDamage(e.getFinalDamage()*damageMultiplier);
    }

    @Override
    public void onDamageByEntity(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Potion))
            return;
        Potion potion = (Potion) e.getDamager();
        if(!potion.getType().equals(PotionType.INSTANT_DAMAGE))
            return;
        e.setDamage(e.getFinalDamage()*potionDamageMultiplier);
    }

    @Override
    public void onAttack(EntityDamageByEntityEvent e) {

    }

    @Override
    public String identifier() {
        return "Speedster_Set";
    }
}
