package de.verdox.csi.model;

import de.verdox.vcore.files.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public abstract class CustomBow extends CustomWeapon{

    public CustomBow(Configuration config) {
        super(config);
    }

    @Override
    public boolean isItemEquipped(Player player) {
        ItemStack customItem = getItemStack();
        return player.getItemInHand().equals(customItem);
    }

    public abstract void onCustomArrowHit(EntityDamageByEntityEvent e);
    public abstract void onHit(ProjectileHitEvent e);
    public abstract void onProjectileLaunch(ProjectileLaunchEvent e);

}
