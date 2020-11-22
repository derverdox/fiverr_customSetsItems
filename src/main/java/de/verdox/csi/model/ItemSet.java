package de.verdox.csi.model;

import de.verdox.vcore.files.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ItemSet {
    public static Set<ItemSet> cache = new HashSet<>();
    private List<CustomItem> customItems;

    public ItemSet(Configuration config, List<CustomItem> customItems){
        this.customItems = customItems;
        cache.add(this);

    }

    protected String configPath(){return "CustomSets."+identifier();}
    public List<CustomItem> getCustomItems() {
        return customItems;
    }
    public abstract void onEquipFullSet(Player player);
    public abstract void onUnEquipFullSet(Player player);
    public abstract void onMove(PlayerMoveEvent e);
    public abstract void onDamage(EntityDamageEvent e);
    public abstract void onAttack(EntityDamageByEntityEvent e);
    public abstract String identifier();
}
