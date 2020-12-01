package de.verdox.csi.model.itemsets;

import de.verdox.csi.Core;
import de.verdox.csi.model.CustomItem;
import de.verdox.csi.model.ItemSet;
import de.verdox.vcore.files.Configuration;
import de.verdox.vcore.utils.MinecraftConstants;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class StreetFighterSet extends ItemSet {

    private float punchMultiplier;
    private float walkSpeedMultiplier;
    private float damageReceiveMultiplier;

    public StreetFighterSet(Configuration config, List<CustomItem> customItems) {
        super(config, customItems);
        if(!config.getConfig().isSet(configPath()+".punchMultiplier"))
            config.getConfig().set(configPath()+".punchMultiplier",1.25f);
        if(!config.getConfig().isSet(configPath()+".damageReceiveMultiplier"))
            config.getConfig().set(configPath()+".damageReceiveMultiplier",1.10f);
        if(!config.getConfig().isSet(configPath()+".walkSpeedMultiplier"))
            config.getConfig().set(configPath()+".walkSpeedMultiplier",1.10f);
        config.save();
        this.punchMultiplier = (float) config.getConfig().getDouble(configPath()+".punchMultiplier");
        this.damageReceiveMultiplier = (float) config.getConfig().getDouble(configPath()+".damageReceiveMultiplier");
        this.walkSpeedMultiplier = (float) config.getConfig().getDouble(configPath()+".walkSpeedMultiplier");
    }

    @Override
    public void onEquipFullSet(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&eYou equipped the &aStreet Fighter Set&7!"));
        player.setWalkSpeed(MinecraftConstants.defaultPlayerWalkSpeed*walkSpeedMultiplier);
    }

    @Override
    public void onUnEquipFullSet(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&eYou unequipped the &aStreet Fighter Set&7!"));
        player.setWalkSpeed(MinecraftConstants.defaultPlayerWalkSpeed);
    }

    @Override
    public void onMove(PlayerMoveEvent e) {

    }

    @Override
    public void onDamage(EntityDamageEvent e) {
        e.setDamage(e.getFinalDamage()*damageReceiveMultiplier);
    }

    @Override
    public void onDamageByEntity(EntityDamageByEntityEvent e) {

    }

    @Override
    public void onAttack(EntityDamageByEntityEvent e) {
        Core.debug("Attack "+identifier()+" ");
        Player player = (Player) e.getDamager();
        if(player.getItemInHand() == null || player.getItemInHand().getType().equals(Material.AIR)){
            e.setDamage(e.getFinalDamage()*punchMultiplier);
            Core.debug("Damage with Hand: "+e.getFinalDamage());
        }
    }

    @Override
    public String identifier() {
        return "StreetFighter_Set";
    }
}
