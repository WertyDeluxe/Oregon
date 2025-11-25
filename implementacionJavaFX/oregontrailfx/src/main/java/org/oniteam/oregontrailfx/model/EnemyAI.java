package org.oniteam.oregontrailfx.model;

public class EnemyAI {
    private final double rangoVision;

    public EnemyAI(double rangoVision){
        this.rangoVision = rangoVision;
    }

    public void update(Enemy e, Player p){
        double dist = e.getDistanceTo(p);
        if (dist <= rangoVision){
            e.setState(EnemyState.PERSEGUIR);
            int ex = e.getX();
            int ey = e.getY();
            int px = p.getX();
            int py = p.getY();
            if (px > ex){
                ex++;
            }
            else if (px < ex){
                ex--;
            }
            if (py > ey){
                ey++;
            }
            else if (py < ey){
                ey--;
            }
            e.setPosition(ex, ey);
        } else {
            e.setState(EnemyState.IDLE);
        }
    }
}
