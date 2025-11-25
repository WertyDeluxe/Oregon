package org.oniteam.oregontrailfx.model;

public class EnemyAttack {
    private final int damage;
    private final int cadenceMs;

    public EnemyAttack(int damage, int cadenceMs){
        this.damage = Math.max(0, damage);
        this.cadenceMs = cadenceMs;
    }

    public boolean tryAttack(Enemy e, Player p){
        if (e.getX() == p.getX() && e.getY() == p.getY()){
            p.damage(damage);
            return true;
        }
        return false;
    }
}
