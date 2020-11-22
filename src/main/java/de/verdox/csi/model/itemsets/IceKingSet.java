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
    public IceKingSet(Configuration config, List<CustomItem> customItems) {
        super(config, customItems);
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
        System.out.println(from.getBlock().getType());
        if(!from.add(0,-1,0).getBlock().getType().equals(Material.ICE)){
            e.getPlayer().setWalkSpeed(MinecraftConstants.defaultPlayerWalkSpeed);
            return;
        }

        e.getPlayer().setWalkSpeed(MinecraftConstants.defaultPlayerWalkSpeed*1.3f);
    }

    @Override
    public void onDamage(EntityDamageEvent e) {

    }

    @Override
    public void onAttack(EntityDamageByEntityEvent e) {

    }

    @Override
    public String identifier() {
        return null;
    }
}
