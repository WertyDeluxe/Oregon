package org.oniteam.oregontrailfx.model;

public class Enemy {

    private TypeEnemy typeEnemy;
    private int x;
    private int y;
    private EnemyState state = EnemyState.IDLE;

    public Enemy(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setPosition(int nx, int ny){
        this.x = nx;
        this.y = ny;
    }

    public EnemyState getState(){
        return state;
    }

    public void setState(EnemyState s){
        this.state = s;
    }

    public double getDistanceTo(Player p){
        int dx = p.getX() - x;
        int dy = p.getY() - y;
        return Math.hypot(dx, dy);
    }

    public TypeEnemy getTypeEnemy() {
        return typeEnemy;
    }
}
