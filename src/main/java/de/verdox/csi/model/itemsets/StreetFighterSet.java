package de.verdox.csi.model.itemsets;

import de.verdox.csi.model.CustomItem;
import de.verdox.csi.model.ItemSet;
import de.verdox.vcore.files.Configuration;
import de.verdox.vcore.utils.MinecraftConstants;
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
        player.setWalkSpeed(MinecraftConstants.defaultPlayerWalkSpeed*walkSpeedMultiplier);
    }

    @Override
    public void onUnEquipFullSet(Player player) {
        player.setWalkSpeed(MinecraftConstants.defaultPlayerWalkSpeed);
    }

    @Override
    public void onMove(PlayerMoveEvent e) {

    }

    @Override
    public void onDamage(EntityDamageEvent e) {
        Player player = (Player) e.getEntity();
        if(player.getItemInHand() == null)
            e.setDamage(e.getFinalDamage()*punchMultiplier);
    }

    @Override
    public void onDamageByEntity(EntityDamageByEntityEvent e) {

    }

    @Override
    public void onAttack(EntityDamageByEntityEvent e) {
        e.setDamage(e.getFinalDamage()*damageReceiveMultiplier);
    }

    @Override
    public String identifier() {
        return "StreetFighter_Set";
    }
}
