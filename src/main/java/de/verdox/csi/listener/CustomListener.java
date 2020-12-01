package de.verdox.csi.listener;

import de.verdox.csi.model.*;
import de.verdox.csi.model.playersession.CSIPlayerData;
import de.verdox.vcore.events.armorequipevent.ArmorEquipEvent;
import de.verdox.vcore.playersession.PlayerSession;
import de.verdox.vcore.playersession.SessionManager;
import de.verdox.vcore.playersession.events.PlayerSessionCreateEvent;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class CustomListener implements Listener {

    @EventHandler
    public void onArmorEquip(ArmorEquipEvent e){
        Player player = e.getPlayer();
        CSIPlayerData csiPlayerData = (CSIPlayerData) SessionManager.getInstance().getSession(player).getData(CSIPlayerData.identifier);
        if(csiPlayerData == null)
            return;

        CustomArmor oldCustomArmor = (CustomArmor) CustomItem.findItem(e.getOldArmorPiece());
        csiPlayerData.unEquip(oldCustomArmor);

        CustomArmor newCustomArmor = (CustomArmor) CustomItem.findItem(e.getNewArmorPiece());
        csiPlayerData.equip(newCustomArmor);

    }

    @EventHandler
    public void onHit(ProjectileHitEvent e){
        if(!(e.getEntity().getShooter() instanceof Player))
            return;
        Player player = (Player) e.getEntity().getShooter();
        ItemStack bow = player.getItemInHand();
        if(!bow.getType().equals(Material.BOW))
            return;

        CustomBow customBow = (CustomBow) CustomItem.findItem(bow);
        if(customBow == null)
            return;
        customBow.onHit(e);
    }

    @EventHandler
    public void onHitCustomArrow(ProjectileHitEvent e){
        Projectile projectile = e.getEntity();
        if(!(projectile instanceof Arrow))
            return;
        Arrow arrow = (Arrow) projectile;
        CustomArrow customArrow = CustomArrow.findByArrow(arrow);
        if(customArrow == null)
            return;
        customArrow.callback();
        customArrow.stopParticles();
    }

    @EventHandler
    public void onBowDamage(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Arrow))
            return;
        Arrow arrow = (Arrow) e.getDamager();
        if(arrow == null)
            return;
        CustomArrow customArrow = CustomArrow.findByArrow(arrow);
        double damage = customArrow.getDamage();
        if(damage != 0){
            e.setDamage(damage);
        }
        if(customArrow.getPotionEffects() != null ){
            if(!(e.getEntity() instanceof LivingEntity))
                return;
            LivingEntity livingEntity = (LivingEntity) e.getEntity();
            for (PotionEffect potionEffect : customArrow.getPotionEffects()) {
                livingEntity.addPotionEffect(potionEffect);
            }
        }
        CustomBow customBow = customArrow.getCustomBow();
        if(customBow == null)
            return;
        customBow.onCustomArrowHit(e);
    }

    @EventHandler
    public void interact(PlayerInteractEvent e){
        Player player = e.getPlayer();
        Action action = e.getAction();
        CSIPlayerData csiPlayerData = (CSIPlayerData) SessionManager.getInstance().getSession(player).getData(CSIPlayerData.identifier);
        if(csiPlayerData == null)
            return;
        ItemStack weaponInHand = player.getItemInHand();
        CustomItem customItem = CustomItem.findItem(weaponInHand);
        if(customItem == null)
            return;
        if(!(customItem instanceof CustomWeapon))
            return;
        CustomWeapon customWeapon = (CustomWeapon) customItem;
        if(action.equals(Action.PHYSICAL))
            return;
        csiPlayerData.startCombo(customWeapon);
        if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            csiPlayerData.comboClick(Combo.Click.Right);
        }
        else {
            csiPlayerData.comboClick(Combo.Click.Left);
        }
    }

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent e){
        Player player = (Player) e.getEntity().getShooter();
        if(player == null)
            return;
        ItemStack bow = player.getItemInHand();

        CustomBow customBow = (CustomBow) CustomItem.findItem(bow);
        customBow.onProjectileLaunch(e);
    }

    @EventHandler
    public void playerDrop(PlayerDropItemEvent e){
        Player player = e.getPlayer();
        ItemStack stack = e.getItemDrop().getItemStack();
        CSIPlayerData csiPlayerData = (CSIPlayerData) SessionManager.getInstance().getSession(player).getData(CSIPlayerData.identifier);
        if(csiPlayerData == null)
            return;

        CustomItem item = CustomItem.findItem(stack);
        if(item == null)
            return;
        if(item instanceof CustomArmor){
            if(item.isItemEquipped(player))
                csiPlayerData.unEquip(item);
            return;
        }
        csiPlayerData.unEquip(item);
    }

    @EventHandler
    public void pickupItem(PlayerPickupItemEvent e){
        Player player = e.getPlayer();
        CSIPlayerData csiPlayerData = (CSIPlayerData) SessionManager.getInstance().getSession(player).getData(CSIPlayerData.identifier);
        if(csiPlayerData == null)
            return;
        if(player.getInventory().getHeldItemSlot() == player.getInventory().firstEmpty()){
            // Player gets new Item in his hands!
            CustomItem item = CustomItem.findItem(e.getItem().getItemStack());
            if(item == null)
                return;
            if(item instanceof CustomArmor)
                return;
            csiPlayerData.equip(item);
        }
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        CSIPlayerData csiPlayerData = (CSIPlayerData) SessionManager.getInstance().getSession(player).getData(CSIPlayerData.identifier);
        if(csiPlayerData == null)
            return;

        int clickedSlot = e.getSlot();
        CustomItem clickedItem = CustomItem.findItem(e.getCurrentItem());

        int mainHandSlot = player.getInventory().getHeldItemSlot();
        CustomItem mainHandItem = CustomItem.findItem(player.getItemInHand());

        CustomItem cursorItem = CustomItem.findItem(e.getCursor());

        // Case 1 - Player clicks on the Item in Mainhand
        if(clickedSlot == mainHandSlot && clickedItem != null && clickedItem.equals(mainHandItem)){
            if(mainHandItem instanceof CustomArmor)
                return;
            csiPlayerData.unEquip(mainHandItem);
        }
        else if(clickedSlot == mainHandSlot && cursorItem != null){
            if(cursorItem instanceof CustomArmor)
                return;
            csiPlayerData.equip(cursorItem);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player player = e.getEntity();
        CSIPlayerData csiPlayerData = (CSIPlayerData) SessionManager.getInstance().getSession(player).getData(CSIPlayerData.identifier);
        if(csiPlayerData == null)
            return;
        if(e.getKeepInventory() == true)
            return;
        e.getEntity().getInventory().forEach(stack -> {
            CustomItem item = CustomItem.findItem(stack);
            if(item == null)
                return;
            if(item instanceof CustomArmor)
                return;
            csiPlayerData.unEquip(item);
            item.onPlayerDeath(e.getEntity());
        });
    }

    @EventHandler
    public void onEquipCustomItem(PlayerItemHeldEvent e){
        Player player = e.getPlayer();
        CSIPlayerData csiPlayerData = (CSIPlayerData) SessionManager.getInstance().getSession(player).getData(CSIPlayerData.identifier);
        if(csiPlayerData == null)
            return;
        ItemStack newItem = e.getPlayer().getInventory().getItem(e.getNewSlot());
        ItemStack oldItem = e.getPlayer().getInventory().getItem(e.getPreviousSlot());

        CustomItem oldCustomItem = CustomItem.findItem(oldItem);
        if(!(oldCustomItem instanceof CustomArmor))
            csiPlayerData.unEquip(oldCustomItem);

        CustomItem newCustomItem = CustomItem.findItem(newItem);
        if(!(newCustomItem instanceof CustomArmor))
            csiPlayerData.equip(newCustomItem);
    }

    @EventHandler
    public void onRespawn(PlayerGameModeChangeEvent e){
        PlayerSession session = SessionManager.getInstance().getSession(e.getPlayer());
        CSIPlayerData csiPlayerData = (CSIPlayerData) session.getData(CSIPlayerData.identifier);
        csiPlayerData.checkForItems();
    }

    @EventHandler
    public void onSessionCreate(PlayerSessionCreateEvent e){
        PlayerSession session = SessionManager.getInstance().getSession(e.getPlayer());
        CSIPlayerData csiPlayerData = (CSIPlayerData) session.getData(CSIPlayerData.identifier);
        if(csiPlayerData == null){
            csiPlayerData = new CSIPlayerData(e.getPlayer());
            session.addDataToSession(csiPlayerData);
        }
        csiPlayerData.checkForItems();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        CSIPlayerData csiPlayerData = (CSIPlayerData) SessionManager.getInstance().getSession(e.getPlayer()).getData(CSIPlayerData.identifier);
        if(csiPlayerData == null)
            return;
        csiPlayerData.getEquippedSets().forEach(set -> set.onMove(e));
    }

    @EventHandler
    public void onDamageReceive(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player))
            return;
        Player player = (Player) e.getEntity();
        CSIPlayerData csiPlayerData = (CSIPlayerData) SessionManager.getInstance().getSession(player).getData(CSIPlayerData.identifier);
        csiPlayerData.getEquippedSets().forEach(set -> set.onDamage(e));
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player){
            Player attacker = (Player) e.getEntity();
            CSIPlayerData csiPlayerData = (CSIPlayerData) SessionManager.getInstance().getSession(attacker).getData(CSIPlayerData.identifier);
            csiPlayerData.getEquippedSets().forEach(set -> set.onDamageByEntity(e));
        }
        if(e.getDamager() instanceof Player){
            Player attacker = (Player) e.getDamager();
            CSIPlayerData csiPlayerData = (CSIPlayerData) SessionManager.getInstance().getSession(attacker).getData(CSIPlayerData.identifier);
            csiPlayerData.getEquippedSets().forEach(set -> set.onAttack(e));
        }
    }

    private boolean isSetComplete(ItemSet itemSet, Player player, CustomItem newItem){
        for (CustomItem customItem : itemSet.getCustomItems()) {
            // The new Item will be ignored
            if(customItem.equals(newItem))
                continue;
            // If we find a SetItem that is not equipped yet the set is not complete
            if(!customItem.isItemEquipped(player)){
                return false;
            }
        }
        return true;
    }
}
