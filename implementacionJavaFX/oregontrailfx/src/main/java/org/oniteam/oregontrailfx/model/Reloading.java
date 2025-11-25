package org.oniteam.oregontrailfx.model;

public class Reloading {
    private final boolean auto;
    private final int tRecargaMs;
    private boolean reloading = false;

    public Reloading(boolean auto, int tRecargaMs){
        this.auto = auto;
        this.tRecargaMs = tRecargaMs;
    }
    public boolean isAuto(){
        return auto;
    }

    public long getTRecargaMs(){
        return tRecargaMs;
    }

    public boolean isReloading(){
        return reloading;
    }

    public void startReload(String weapon){
        reloading = true;
    }

    public void finishReload(){
        reloading = false;
    }
}
