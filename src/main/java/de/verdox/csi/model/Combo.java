package de.verdox.csi.model;

import de.verdox.csi.Core;
import de.verdox.csi.model.playersession.CSIPlayerData;
import org.bukkit.Bukkit;

import java.util.List;

public class Combo {

    private CustomWeapon customWeapon;
    private List<Click> combo;
    private int clicksClicked = -1;
    private CSIPlayerData csiPlayerData;

    public Combo(CustomWeapon customWeapon, CSIPlayerData csiPlayerData, List<Click> combo){
        this.csiPlayerData = csiPlayerData;
        this.customWeapon = customWeapon;
        this.combo = combo;
        Bukkit.getScheduler().runTaskLaterAsynchronously(Core.core,() -> cleanUp(),20L);
    }

    private void cleanUp(){
        this.csiPlayerData.clearCombo(this);
    }

    public void addClick(Click click){
        if(combo == null || combo.isEmpty())
            return;
        clicksClicked++;
        Click nextClickNeeded = combo.get(clicksClicked);

        if(!click.equals(nextClickNeeded)){
            cleanUp();
            return;
        }

        // Ist Last click?
        if(nextClickNeeded.equals(combo.get(combo.size()-1))){
            customWeapon.onComboTriggered(csiPlayerData.getPlayer());
            cleanUp();
        }
    }

    public enum Click{
        Left,
        Right
    }
}
