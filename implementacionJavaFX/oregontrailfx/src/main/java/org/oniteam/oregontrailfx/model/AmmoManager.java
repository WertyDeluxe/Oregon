package org.oniteam.oregontrailfx.model;

public class AmmoManager {
    private int rifle;
    private int revolver;

    public AmmoManager(){
        this(0,0);
    }

    public AmmoManager(int rifle, int revolver){
        this.rifle = Math.max(0, rifle);
        this.revolver = Math.max(0, revolver);
    }

    public int getRifleAmmo(){
        return rifle;
    }

    public int getRevolverAmmo(){
        return revolver;
    }

    public boolean hasAmmo(String weapon){
        if ("rifle".equalsIgnoreCase(weapon)) {
            return rifle > 0;
        }
        if ("revolver".equalsIgnoreCase(weapon)){
            return revolver > 0;
        }
        return false;
    }

    public boolean shoot(String weapon){
        if ("rifle".equalsIgnoreCase(weapon)){
            if (rifle > 0){
                rifle--;
                return true;
            }
            return false;

        } else if ("revolver".equalsIgnoreCase(weapon)){
            if (revolver > 0){
                revolver--;
                return true;
            }
            return false;
        }
        return false;
    }
}
