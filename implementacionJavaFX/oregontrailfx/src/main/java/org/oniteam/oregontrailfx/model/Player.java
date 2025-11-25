package org.oniteam.oregontrailfx.model;

import javafx.scene.input.KeyEvent;

public class Player extends Thread{

    private int x;
    private int y;
    private int vida;
    private double hearts;
    private String nombre;
    private String profesion;
    private double dinero;



    public Player(int x, int y, int vida,double anchoCanvas, double altoCanvas){
        this.x = x;
        this.y = y;
        this.vida = vida;
        this.hearts = vida;
        this.nombre = null;
        this.profesion = null;
        this.dinero = 0.0;
    }

    public Player(double hearts){
        this.x = 0;
        this.y = 0;
        this.hearts = hearts;
        this.vida = (int)Math.round(hearts);
        this.nombre = null;
        this.profesion = null;
        this.dinero = 0.0;
    }

    public Player(String nombre, String profesion, int x, int y, int vida) {
        this.nombre = nombre;
        this.profesion = profesion;
        this.x = x;
        this.y = y;
        this.vida = vida;
        this.hearts = vida;
        this.dinero = calcularDineroInicial(profesion);
    }

    public Player(String nombre, String profesion) {
        this(nombre, profesion, 0, 0, 3);
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
    }

    /**
     * Metodo: calcularDineroInicial
     * Calcula el dinero inicial según la profesión.
     *
     * @param profesion La profesión del jugador
     * @return Dinero inicial
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

    /**
     * Metodo: tienePerfilCompleto
     * Verifica si el jugador tiene un perfil completo (nombre y profesión).
     *
     * @return true si tiene nombre y profesión asignados
     */
    public boolean tienePerfilCompleto() {
        return nombre != null && profesion != null;
    }

    /**
     * Metodo: inicializarPerfil
     * Inicializa el perfil del jugador con nombre y profesión.
     * cuando se crea el jugador para combate y luego se le asigna identidad.
     *
     * @param nombre Nombre del jugador
     * @param profesion Profesión elegida
     */
    public void inicializarPerfil(String nombre, String profesion) {
        this.nombre = nombre;
        this.profesion = profesion;
        this.dinero = calcularDineroInicial(profesion);
    }

    /**
     * Metodo:agregarDinero
     * Añade dinero al jugador.
     *
     * @param cantidad Cantidad a añadir
     */
    public void agregarDinero(double cantidad) {
        if (cantidad > 0) {
            this.dinero += cantidad;
        }
    }

    /**
     * Metodo: Gasta dinero
     *
     * @param cantidad Cantidad a gastar
     * @return true si tenía suficiente dinero
     */
    public boolean gastarDinero(double cantidad) {
        if (cantidad <= 0 || cantidad > dinero) {
            return false;
        }
        this.dinero -= cantidad;
        return true;
    }

    /**
     * Metodo: puedePagar
     * Verifica si el jugador puede pagar una cantidad.
     *
     * @param cantidad Cantidad a verificar
     * @return true si tiene suficiente dinero
     */
    public boolean puedePagar(double cantidad) {
        return dinero >= cantidad;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Player{");

        // Muestra perfil si existe
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

        // Siempre mostrar posición y vida
        if (nombre != null || profesion != null || dinero > 0) sb.append(", ");
        sb.append("pos=(").append(x).append(",").append(y).append(")");
        sb.append(", vida=").append(vida);

        sb.append('}');
        return sb.toString();
    }
}






































































































