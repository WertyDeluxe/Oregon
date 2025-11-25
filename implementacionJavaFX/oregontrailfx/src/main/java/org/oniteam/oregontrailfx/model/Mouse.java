package org.oniteam.oregontrailfx.model;

public final class Mouse {
    private static int x;
    private static int y;

    private Mouse(){

    }
    public static void setPos(int nx, int ny){
        x = nx;
        y = ny;
    }

    public static Vec2 getPos(){
        return new Vec2(x, y);
    }
}
