package de.verdox.csi.model.custombows;

import com.sk89q.worldedit.bukkit.fastutil.Hash;
import de.verdox.csi.Core;
import de.verdox.csi.model.CustomBow;
import de.verdox.csi.model.itemsets.IceKingSet;
import de.verdox.csi.model.playersession.CSIPlayerData;
import de.verdox.vcore.files.Configuration;
import de.verdox.vcore.playersession.SessionManager;
import de.verdox.vcore.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class IceKingBow extends CustomBow {
    private HashMap<Projectile, BukkitTask> shot = new HashMap<>();
    public IceKingBow(Configuration config) {
        super(config);
    }

    @Override
    public void onHit(ProjectileHitEvent e) {
        Projectile projectile = e.getEntity();
        if(shot.containsKey(projectile)){
            shot.get(projectile).cancel();
            shot.remove(projectile);
        }
    }

    @Override
    public void onProjectileLaunch(ProjectileLaunchEvent e) {
        Projectile projectile = e.getEntity();
        BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(Core.core,() -> {
            projectile.getLocation().getWorld().playEffect(projectile.getLocation(), Effect.POTION_BREAK,2,20);
        },0L,1L);
        shot.put(projectile,task);
    }

    @Override
    public void onEquip(Player player) {

    }

    @Override
    public void onUnEquip(Player player) {

    }

    @Override
    public boolean onPlayerDeath(Player player) {
        return false;
    }

    @Override
    protected ItemStack customItem() {
        return ItemUtil.createStack(Core.core, Material.BOW.name(), 1,(short) 0,"&bIce King Bow","&cVery powerful bow&7!");

    }

    @Override
    public String identifier() {
        return "IceKing_Bow";
    }
}
