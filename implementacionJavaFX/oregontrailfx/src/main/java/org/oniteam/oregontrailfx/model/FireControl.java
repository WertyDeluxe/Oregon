package org.oniteam.oregontrailfx.model;

public class FireControl {

    private String selectedWeapon;
    private AmmoManager ammo;
    private Reloading reloading;
    private GameManager gameManager;
    private Player player;

    public FireControl(String selectedWeapon){
        this(selectedWeapon, null, null);
    }

    public FireControl(GameManager gameManager, Player player){
        this.gameManager = gameManager;
        this.player = player;
    }

    public FireControl(String selectedWeapon, AmmoManager ammo, Reloading reloading){
        this.selectedWeapon = selectedWeapon;
        this.ammo = ammo;
        this.reloading = reloading;
    }

    public FireControl(String selectedWeapon, AmmoManager ammo, int reloadingValue){
        this.selectedWeapon = selectedWeapon;
        this.ammo = ammo;
        this.reloading = null;
    }

    public double aimAt(AimReticle ret){
        double dx = ret.getX();
        double dy = ret.getY();
        double rad = Math.atan2(dy, dx);
        double deg = Math.toDegrees(rad);

        if (deg < 0){
            deg += 360.0;
        }
        return deg;
    }

    public boolean shoot(){
        if (reloading != null && reloading.isReloading()){
            return false;
        }

        if (ammo != null && !ammo.hasAmmo(selectedWeapon)){
            if (reloading != null && reloading.isAuto()){
                reloading.startReload(selectedWeapon);
            }
            return false;
        }

        if (ammo != null){
            return ammo.shoot(selectedWeapon);
        }
        return false;
    }
    public void playerShoot(Player player) {
        if (player.getCurrentAmmo() > 0) {
            player.setCurrentAmmo(player.getCurrentAmmo() - 1);
        }
    }
}
