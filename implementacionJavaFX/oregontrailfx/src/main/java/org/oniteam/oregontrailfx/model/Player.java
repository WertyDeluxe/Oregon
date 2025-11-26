package org.oniteam.oregontrailfx.model;

public class Player extends Thread {

    private int x;
    private int y;
    private int vida;
    private double hearts;
    private String nombre;
    private String profesion;
    private double dinero;
    private int currentAmmo; // Munición actual del arma equipada

    // Control de animación
    private boolean running = true;
    private volatile boolean pausado = false;

    public Player(int x, int y, int vida, double anchoCanvas, double altoCanvas){
        this.x = x;
        this.y = y;
        this.vida = vida;
        this.hearts = vida;
        this.nombre = null;
        this.profesion = null;
        this.dinero = 0.0;
        this.currentAmmo = 0;
    }

    public Player(double hearts){
        this.x = 0;
        this.y = 0;
        this.hearts = hearts;
        this.vida = (int)Math.round(hearts);
        this.nombre = null;
        this.profesion = null;
        this.dinero = 0.0;
        this.currentAmmo = 0;
    }

    public Player(String nombre, String profesion, int x, int y, int vida) {
        this.nombre = nombre;
        this.profesion = profesion;
        this.x = x;
        this.y = y;
        this.vida = vida;
        this.hearts = vida;
        this.dinero = calcularDineroInicial(profesion);
        this.currentAmmo = 0;
    }

    public Player(String nombre, String profesion) {
        this(nombre, profesion, 0, 0, 3);
    }

    @Override
    public void run() {
        System.out.println("Hilo de animación del jugador iniciado");

        while (running && vida > 0) {
            try {
                if (!pausado) {
                    // Regeneración muy lenta de salud (1 punto cada 30 segundos)
                    if (hearts < vida && hearts < 3) {
                        hearts += 0.01; // Regeneración gradual
                        if (hearts > vida) {
                            hearts = vida;
                        }
                    }
                }

                Thread.sleep(100);

            } catch (InterruptedException e) {
                System.out.println("Hilo de jugador interrumpido");
                break;
            }
        }

        System.out.println("Hilo de animación del jugador terminado");
    }

    /**
     * Detiene el hilo de animación.
     */
    public void stopAnimation() {
        running = false;
    }

    /**
     * Pausa/reanuda las animaciones.
     */
    public void setPausado(boolean pausado) {
        this.pausado = pausado;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPosX(){
        return x;
    }

    public int getPosY(){
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(int nx, int ny){
        this.x = nx;
        this.y = ny;
    }

    public Vec2 getPosition(){
        return new Vec2(x, y);
    }

    public void setPosition(Vec2 pos){
        this.x = (int)pos.getX();
        this.y = (int)pos.getY();
    }

    public int getVida(){
        return vida;
    }

    public double gethearts(){
        return hearts;
    }

    public void damage(int d){
        vida = Math.max(0, vida - Math.max(0, d));
        hearts = Math.max(0, hearts - Math.max(0, d));
    }

    public void heal(double h){
        hearts += Math.max(0, h);
        vida = (int)Math.round(hearts);
        if (vida > 3) vida = 3; // Límite máximo de vida
        if (hearts > 3) hearts = 3.0;
    }

    public int getCurrentAmmo() {
        return currentAmmo;
    }

    public void setCurrentAmmo(int ammo) {
        this.currentAmmo = Math.max(0, ammo);
    }

    /**
     * Calcula el dinero inicial según la profesión.
     */
    private double calcularDineroInicial(String profesion) {
        if (profesion == null) {
            return 800.0;
        }
        switch (profesion) {
            case "Banquero":
                return 1600.0;
            case "Carpintero":
                return 800.0;
            case "Granjero":
                return 400.0;
            default:
                return 800.0;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public double getDinero() {
        return dinero;
    }

    public void setDinero(double dinero) {
        this.dinero = Math.max(0, dinero);
    }

    public boolean tienePerfilCompleto() {
        return nombre != null && profesion != null;
    }

    public void inicializarPerfil(String nombre, String profesion) {
        this.nombre = nombre;
        this.profesion = profesion;
        this.dinero = calcularDineroInicial(profesion);
    }

    public void agregarDinero(double cantidad) {
        if (cantidad > 0) {
            this.dinero += cantidad;
        }
    }

    public boolean gastarDinero(double cantidad) {
        if (cantidad <= 0 || cantidad > dinero) {
            return false;
        }
        this.dinero -= cantidad;
        return true;
    }

    public boolean puedePagar(double cantidad) {
        return dinero >= cantidad;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Player{");

        if (nombre != null) {
            sb.append("nombre='").append(nombre).append('\'');
        }
        if (profesion != null) {
            if (nombre != null) sb.append(", ");
            sb.append("profesion='").append(profesion).append('\'');
        }
        if (dinero > 0 || (nombre != null && profesion != null)) {
            if (nombre != null || profesion != null) sb.append(", ");
            sb.append("dinero=").append(String.format("%.2f", dinero));
        }

        if (nombre != null || profesion != null || dinero > 0) sb.append(", ");
        sb.append("pos=(").append(x).append(",").append(y).append(")");
        sb.append(", vida=").append(vida);

        sb.append('}');
        return sb.toString();
    }
}