package de.verdox.csi.model.itemsets;

import de.verdox.csi.model.CustomItem;
import de.verdox.csi.model.ItemSet;
import de.verdox.vcore.files.Configuration;
import de.verdox.vcore.utils.MinecraftConstants;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class IceKingSet extends ItemSet {

    private float walkSpeedMultiplier;

    public IceKingSet(Configuration config, List<CustomItem> customItems) {
        super(config, customItems);
        if(!config.getConfig().isSet(configPath()+".walkSpeedMultiplier"))
            config.getConfig().set(configPath()+".walkSpeedMultiplier",1.4f);
        config.save();
        this.walkSpeedMultiplier = (float) config.getConfig().getDouble(configPath()+".walkSpeedMultiplier");
    }

    @Override
    public void onEquipFullSet(Player player) {

    }

    @Override
    public void onUnEquipFullSet(Player player) {

    }

    @Override
    public void onMove(PlayerMoveEvent e) {
        Location from = e.getFrom();
        if(!from.add(0,-1,0).getBlock().getType().equals(Material.ICE)){
            e.getPlayer().setWalkSpeed(MinecraftConstants.defaultPlayerWalkSpeed);
            return;
        }

        e.getPlayer().setWalkSpeed(MinecraftConstants.defaultPlayerWalkSpeed*walkSpeedMultiplier);
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
