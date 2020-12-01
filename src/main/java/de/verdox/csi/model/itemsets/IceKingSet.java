package de.verdox.csi.model.itemsets;

import de.verdox.csi.Core;
import de.verdox.csi.model.CustomItem;
import de.verdox.csi.model.ItemSet;
import de.verdox.vcore.files.Configuration;
import de.verdox.vcore.utils.MinecraftConstants;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class IceKingSet extends ItemSet {

    private float walkSpeedMultiplier;
    private double healthModifier;

    public IceKingSet(Configuration config, List<CustomItem> customItems) {
        super(config, customItems);
        if(!config.getConfig().isSet(configPath()+".walkSpeedMultiplier"))
            config.getConfig().set(configPath()+".walkSpeedMultiplier",1.4f);
        if(!config.getConfig().isSet(configPath()+".healthModifier"))
            config.getConfig().set(configPath()+".healthModifier",-3.0);
        config.save();
        this.walkSpeedMultiplier = (float) config.getConfig().getDouble(configPath()+".walkSpeedMultiplier");
        this.healthModifier = config.getConfig().getDouble(configPath()+".healthModifier");
    }

    @Override
    public void onEquipFullSet(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&eYou equipped the &aIce-King Set&7!"));
        player.setMaxHealth(MinecraftConstants.standardHealth + healthModifier);
    }

    @Override
    public void onUnEquipFullSet(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&eYou unequipped the &aIce-King Set&7!"));
        player.setMaxHealth(MinecraftConstants.standardHealth);
    }

    @Override
    public void onMove(PlayerMoveEvent e) {
        Location to = e.getTo();
        Location from = e.getFrom();
        if(!from.add(0,-1,0).getBlock().getType().equals(Material.ICE)){
            e.getPlayer().setWalkSpeed(MinecraftConstants.defaultPlayerWalkSpeed);
        }

        e.getPlayer().setWalkSpeed(MinecraftConstants.defaultPlayerWalkSpeed*walkSpeedMultiplier);

        Core.debug(to.getBlock().getLocation().add(0,-1,0).getBlock().getType()+"");

        if(to.getBlock().getLocation().add(0,-1,0).getBlock().getType().equals(Material.STATIONARY_WATER)){

            if(isObsidianOrCobbleNearby(to.getBlock().getLocation().add(0,1,0)))
                return;
            to.getBlock().getLocation().add(0,-1,0).getBlock().setType(Material.ICE);
            to.getBlock().getLocation().add(e.getPlayer().getLocation().getDirection()).getBlock().setType(Material.ICE);
        }
    }

    private boolean isObsidianOrCobbleNearby(Location location){
        for(int x = -1; x <= 1; x++)
            for(int y = -1; y <= 1; y++)
                for(int z = -1; z <= 1;z++){
                    if(location.add(x,y,z).getBlock().getType().equals(Material.OBSIDIAN)
                    || location.add(x,y,z).getBlock().getType().equals(Material.COBBLESTONE))
                        return true;
                }
        return false;
    }

    @Override
    public void onDamage(EntityDamageEvent e) {

    }

    @Override
    public void onDamageByEntity(EntityDamageByEntityEvent e) {

    }

    @Override
    public void onAttack(EntityDamageByEntityEvent e) {

    }

    @Override
    public String identifier() {
        return "IceKing_Set";
    }
}
