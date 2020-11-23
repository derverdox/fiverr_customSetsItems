package de.verdox.csi.model.playersession;

import de.verdox.csi.Core;
import de.verdox.csi.model.Combo;
import de.verdox.csi.model.CustomItem;
import de.verdox.csi.model.CustomWeapon;
import de.verdox.csi.model.ItemSet;
import de.verdox.vcore.playersession.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class CSIPlayerData extends PlayerData {
    public static String identifier = "PlayerData_CustomItemSets";
    private Set<ItemSet> equippedSets = new HashSet<>();
    private Set<CustomItem> equippedItems = new HashSet<>();
    private Combo combo;
    public CSIPlayerData(Player player) {
        super(player);
    }

    void addItem(CustomItem item){
        equippedItems.add(item);
    }

    public boolean isSetEquipped(Class c){
        return equippedSets.stream().filter(set -> set.getClass().equals(c)).findAny().isPresent();
    }

    public void startCombo(CustomWeapon customWeapon) {
        if (this.combo != null)
            return;
        this.combo = new Combo(customWeapon, this, customWeapon.combo());
    }

    public void comboClick(Combo.Click click){
        if(this.combo == null)
            return;
        this.combo.addClick(click);
    }

    public void clearCombo(Combo combo){
        if(combo.equals(this.combo))
            this.combo = null;
    }

    void removeItem(CustomItem item){
        equippedItems.remove(item);
    }

    public void addSet(ItemSet set){
        this.equippedSets.add(set);
        set.getCustomItems().stream()
                .filter(customItem -> !equippedItems.contains(customItem))
                .forEach(customItem -> {
                    addItem(customItem);
                    customItem.onEquip(player);
                });
    }

    public void removeSet(ItemSet set){
        this.equippedSets.remove(set);
    }

    @Override
    public String identifier() {
        return identifier;
    }

    @Override
    public void onSessionRemove() {

    }

    public Set<ItemSet> getEquippedSets() {
        return equippedSets;
    }

    public void equip(CustomItem customItem){
        if(customItem == null)
            return;
        customItem.onEquip(player);
        addItem(customItem);
        Bukkit.getScheduler().runTaskAsynchronously(Core.core,() -> {
            ItemSet.cache.stream()
                    .filter(itemSet -> itemSet.getCustomItems().contains(customItem))
                    .forEach(itemSet -> {
                        if(isSetComplete(itemSet,player,customItem)) {
                            Bukkit.getScheduler().runTask(Core.core,() -> { itemSet.onEquipFullSet(player); });
                            addSet(itemSet);
                        }
                    });
        });
    }

    public void unEquip(CustomItem customItem){
        if(customItem == null)
            return;
        customItem.onUnEquip(player);
        removeItem(customItem);
        Bukkit.getScheduler().runTaskAsynchronously(Core.core,() -> {
            ItemSet.cache.stream()
                    .filter(itemSet -> itemSet.getCustomItems().contains(customItem))
                    .forEach(itemSet -> {
                        if(isSetComplete(itemSet,player,customItem)){
                            Bukkit.getScheduler().runTask(Core.core,() -> { itemSet.onUnEquipFullSet(player); });
                            removeSet(itemSet);
                        }
                    });
        });
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
