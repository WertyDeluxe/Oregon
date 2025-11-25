package org.oniteam.oregontrailfx.model;

public class GameManager {

    private static GameManager instance;

    // Estado del juego
    private Player jugador;
    private Caravana caravana;
    private int diaActual;
    private int distanciaRecorrida; // En millas
    private static final int DISTANCIA_TOTAL = 2000; // Millas hasta Oregon
    private boolean juegoEnCurso;
    private boolean juegoGanado;
    private Scenario currentScenario;

    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private GameManager() {
        this.diaActual = 0;
        this.distanciaRecorrida = 0;
        this.juegoEnCurso = false;
        this.juegoGanado = false;
    }

    /**
     * Obtiene la instancia única de GameManager.
     *
     * @return La instancia del GameManager
     */
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    /**
     * Inicia un nuevo juego.
     *
     * @param nombreJugador Nombre del jugador principal
     * @param profesion Profesión elegida
     */
    public void iniciarJuego(String nombreJugador, String profesion) {
        // Crear el jugador
        this.jugador = new Player(nombreJugador, profesion);

        // Crear la caravana
        this.caravana = new Caravana();

        // Agregar al jugador como primer miembro de la caravana
        Miembro miembroJugador = new Miembro(nombreJugador, 35);
        caravana.agregarMiembro(miembroJugador);

        // Reiniciar contadores
        this.diaActual = 0;
        this.distanciaRecorrida = 0;
        this.juegoEnCurso = true;
        this.juegoGanado = false;
    }

    /**
     * Reinicia el juego completamente.
     * Útil para comenzar una nueva partida.
     */
    public void reiniciarJuego() {
        this.jugador = null;
        this.caravana = null;
        this.diaActual = 0;
        this.distanciaRecorrida = 0;
        this.juegoEnCurso = false;
        this.juegoGanado = false;
    }

    /**
     * Avanza un día en el juego.
     * Actualiza recursos, salud de miembros, etc.
     *
     * @param millasRecorridas Millas recorridas en este día
     */
    public void avanzarDia(int millasRecorridas) {
        if (!juegoEnCurso) {
            return;
        }

        diaActual++;
        distanciaRecorrida += millasRecorridas;

        // Consumir comida diaria
        if (caravana != null) {
            caravana.consumirComida();
        }

        // Verificar condiciones de victoria
        if (distanciaRecorrida >= DISTANCIA_TOTAL) {
            juegoGanado = true;
            juegoEnCurso = false;
        }

        // Verificar condiciones de derrota
        if (caravana != null && caravana.todosMuertos()) {
            juegoEnCurso = false;
            juegoGanado = false;
        }
    }

    // ========== GETTERS ==========

    /**
     * Obtiene el jugador principal.
     *
     * @return El jugador
     */
    public Player getJugador() {
        return jugador;
    }

    /**
     * Obtiene la caravana.
     *
     * @return La caravana
     */
    public Caravana getCaravana() {
        return caravana;
    }

    /**
     * Obtiene el día actual del viaje.
     *
     * @return Día actual
     */
    public int getDiaActual() {
        return diaActual;
    }

    /**
     * Obtiene la distancia recorrida.
     *
     * @return Distancia en millas
     */
    public int getDistanciaRecorrida() {
        return distanciaRecorrida;
    }

    /**
     * Obtiene la distancia total del viaje.
     *
     * @return Distancia total en millas
     */
    public int getDistanciaTotal() {
        return DISTANCIA_TOTAL;
    }

    /**
     * Obtiene la distancia restante.
     *
     * @return Distancia restante en millas
     */
    public int getDistanciaRestante() {
        return Math.max(0, DISTANCIA_TOTAL - distanciaRecorrida);
    }

    /**
     * Verifica si el juego está en curso.
     *
     * @return true si el juego está activo
     */
    public boolean isJuegoEnCurso() {
        return juegoEnCurso;
    }

    /**
     * Verifica si el juego fue ganado.
     *
     * @return true si llegaron a Oregon
     */
    public boolean isJuegoGanado() {
        return juegoGanado;
    }

    // ========== SETTERS ==========

    /**
     * Establece el jugador.
     *
     * @param jugador El jugador a establecer
     */
    public void setJugador(Player jugador) {
        this.jugador = jugador;
    }

    /**
     * Establece la caravana.
     *
     * @param caravana La caravana a establecer
     */
    public void setCaravana(Caravana caravana) {
        this.caravana = caravana;
    }

    /**
     * Establece el día actual.
     *
     * @param dia El día a establecer
     */
    public void setDiaActual(int dia) {
        this.diaActual = Math.max(0, dia);
    }

    /**
     * Establece la distancia recorrida.
     *
     * @param distancia La distancia en millas
     */
    public void setDistanciaRecorrida(int distancia) {
        this.distanciaRecorrida = Math.max(0, Math.min(DISTANCIA_TOTAL, distancia));
    }

    /**
     * Establece si el juego está en curso.
     *
     * @param enCurso true para activar el juego
     */
    public void setJuegoEnCurso(boolean enCurso) {
        this.juegoEnCurso = enCurso;
    }

    /**
     * Establece si el juego fue ganado.
     *
     * @param ganado true si ganó
     */
    public void setJuegoGanado(boolean ganado) {
        this.juegoGanado = ganado;
    }

    // ========== MÉTODOS DE UTILIDAD ==========

    /**
     * Calcula el progreso del viaje en porcentaje.
     *
     * @return Porcentaje de progreso (0-100)
     */
    public double getPorcentajeProgreso() {
        return (distanciaRecorrida * 100.0) / DISTANCIA_TOTAL;
    }

    /**
     * Obtiene un resumen del estado del juego.
     *
     * @return String con el estado del juego
     */
    public String getEstadoJuego() {
        if (jugador == null || caravana == null) {
            return "Juego no iniciado";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== Oregon Trail - Estado del Juego ===\n");
        sb.append("Jugador: ").append(jugador.getNombre()).append(" (").append(jugador.getProfesion()).append(")\n");
        sb.append("Día: ").append(diaActual).append("\n");
        sb.append("Distancia: ").append(distanciaRecorrida).append("/").append(DISTANCIA_TOTAL).append(" millas ");
        sb.append("(").append(String.format("%.1f", getPorcentajeProgreso())).append("%)\n");
        sb.append("Dinero: $").append(String.format("%.2f", jugador.getDinero())).append("\n");
        sb.append("Miembros vivos: ").append(caravana.getMiembrosVivos()).append("\n");
        sb.append("Comida: ").append(caravana.getComida()).append(" lbs\n");

        if (juegoGanado) {
            sb.append("\n¡VICTORIA! Has llegado a Oregon.\n");
        } else if (!juegoEnCurso && caravana.todosMuertos()) {
            sb.append("\nGame Over: Todos los miembros han muerto.\n");
        }

        return sb.toString();
    }

    /**
     * Metodo:Termina el juego
     *
     * @param ganado true si el jugador ganó
     */
    public void terminarJuego(boolean ganado) {
        this.juegoEnCurso = false;
        this.juegoGanado = ganado;
    }

    public Scenario getCurrentScenario(){
        return currentScenario;
    }
    public void setCurrentScenario(Scenario scenario){
        this.currentScenario = scenario;
    }

    @Override
    public String toString() {
        return "GameManager{" +
                "dia=" + diaActual +
                ", distancia=" + distanciaRecorrida + "/" + DISTANCIA_TOTAL +
                ", enCurso=" + juegoEnCurso +
                ", ganado=" + juegoGanado +
                '}';
    }


    public Player getPlayer() {

        return jugador;
    }
}
