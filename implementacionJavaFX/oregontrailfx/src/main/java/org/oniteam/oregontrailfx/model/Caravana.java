package org.oniteam.oregontrailfx.model;
import java.util.ArrayList;
import java.util.List;


public class Caravana {

    private List<Miembro> miembros;
    private int comida; // En libras
    private int ritmoViaje; // 1=constante, 2=moderado, 3=descansado
    private int racionesComida; // 1=escasa, 2=moderada, 3=abundante

    /**
     * Constructor de Caravana.
     * Inicializa una caravana vacía con recursos básicos.
     */
    public Caravana() {
        this.miembros = new ArrayList<>();
        this.comida = 0;
        this.ritmoViaje = 2; // Moderado por defecto
        this.racionesComida = 2; // Moderadas por defecto
    }

    /**
     * Constructor con parámetros iniciales.
     *
     * @param comidaInicial Cantidad inicial de comida en libras
     */
    public Caravana(int comidaInicial) {
        this();
        this.comida = Math.max(0, comidaInicial);
    }

    // ========== GESTIÓN DE MIEMBROS ==========

    /**
     * Agrega un miembro a la caravana.
     *
     * @param miembro El miembro a agregar
     * @return true si se agregó exitosamente
     */
    public boolean agregarMiembro(Miembro miembro) {
        if (miembro == null) {
            return false;
        }
        if (miembros.size() >= 5) { // Límite típico de Oregon Trail
            return false;
        }
        return miembros.add(miembro);
    }

    /**
     * Elimina un miembro de la caravana.
     *
     * @param miembro El miembro a eliminar
     * @return true si se eliminó exitosamente
     */
    public boolean eliminarMiembro(Miembro miembro) {
        return miembros.remove(miembro);
    }

    /**
     * Obtiene la lista de miembros.
     *
     * @return Lista de miembros
     */
    public List<Miembro> getMiembros() {
        return new ArrayList<>(miembros);
    }

    /**
     * Obtiene el número de miembros vivos.
     *
     * @return Cantidad de miembros vivos
     */
    public int getMiembrosVivos() {
        return (int) miembros.stream()
                .filter(Miembro::isVivo)
                .count();
    }

    /**
     * Obtiene la lista de miembros vivos.
     *
     * @return Lista de miembros vivos
     */
    public List<Miembro> getMiembrosVivosList() {
        List<Miembro> vivos = new ArrayList<>();
        for (Miembro m : miembros) {
            if (m.isVivo()) {
                vivos.add(m);
            }
        }
        return vivos;
    }

    /**
     * Obtiene el número total de miembros (vivos y muertos).
     *
     * @return Total de miembros
     */
    public int getTotalMiembros() {
        return miembros.size();
    }

    /**
     * Verifica si todos los miembros están muertos.
     *
     * @return true si no hay miembros vivos
     */
    public boolean todosMuertos() {
        return getMiembrosVivos() == 0;
    }

    // ========== GESTIÓN DE COMIDA ==========

    /**
     * Obtiene la cantidad de comida disponible.
     *
     * @return Comida en libras
     */
    public int getComida() {
        return comida;
    }

    /**
     * Establece la cantidad de comida.
     *
     * @param comida Cantidad de comida en libras
     */
    public void setComida(int comida) {
        this.comida = Math.max(0, comida);
    }

    /**
     * Agrega comida a la caravana.
     *
     * @param cantidad Cantidad de comida a agregar
     */
    public void agregarComida(int cantidad) {
        if (cantidad > 0) {
            this.comida += cantidad;
        }
    }

    /**
     * Consume comida según las raciones configuradas y número de miembros vivos.
     *
     * @return Comida consumida
     */
    public int consumirComida() {
        int miembrosVivos = getMiembrosVivos();
        if (miembrosVivos == 0) {
            return 0;
        }

        int consumoPorPersona;
        switch (racionesComida) {
            case 1: // Escasa
                consumoPorPersona = 2;
                break;
            case 2: // Moderada
                consumoPorPersona = 3;
                break;
            case 3: // Abundante
                consumoPorPersona = 5;
                break;
            default:
                consumoPorPersona = 3;
        }

        int consumoTotal = consumoPorPersona * miembrosVivos;
        int comidaConsumida = Math.min(consumoTotal, comida);

        comida -= comidaConsumida;

        // Si no hay suficiente comida, afectar la salud de los miembros
        if (comidaConsumida < consumoTotal) {
            int deficit = consumoTotal - comidaConsumida;
            afectarSaludPorHambre(deficit);
        }

        return comidaConsumida;
    }

    /**
     * Reduce la salud de los miembros cuando no hay suficiente comida.
     *
     * @param deficit Déficit de comida
     */
    private void afectarSaludPorHambre(int deficit) {
        for (Miembro miembro : getMiembrosVivosList()) {
            int dañoSalud = deficit * 2; // Cada libra de déficit causa 2 puntos de daño
            miembro.reducirSalud(dañoSalud);
        }
    }

    /**
     * Verifica si hay comida disponible.
     *
     * @return true si hay comida
     */
    public boolean tieneComida() {
        return comida > 0;
    }

    // ========== GESTIÓN DE RITMO Y RACIONES ==========

    /**
     * Obtiene el ritmo de viaje actual.
     *
     * @return Ritmo de viaje (1=constante, 2=moderado, 3=descansado)
     */
    public int getRitmoViaje() {
        return ritmoViaje;
    }

    /**
     * Establece el ritmo de viaje.
     *
     * @param ritmo Ritmo de viaje (1=constante, 2=moderado, 3=descansado)
     */
    public void setRitmoViaje(int ritmo) {
        if (ritmo >= 1 && ritmo <= 3) {
            this.ritmoViaje = ritmo;
        }
    }

    /**
     * Obtiene el nivel de raciones de comida.
     *
     * @return Nivel de raciones (1=escasa, 2=moderada, 3=abundante)
     */
    public int getRacionesComida() {
        return racionesComida;
    }

    /**
     * Establece el nivel de raciones de comida.
     *
     * @param raciones Nivel de raciones (1=escasa, 2=moderada, 3=abundante)
     */
    public void setRacionesComida(int raciones) {
        if (raciones >= 1 && raciones <= 3) {
            this.racionesComida = raciones;
        }
    }

    /**
     * Obtiene el nombre del ritmo de viaje como texto.
     *
     * @return Descripción del ritmo
     */
    public String getRitmoViajeTexto() {
        switch (ritmoViaje) {
            case 1: return "Constante";
            case 2: return "Moderado";
            case 3: return "Descansado";
            default: return "Desconocido";
        }
    }

    /**
     * Obtiene el nombre del nivel de raciones como texto.
     *
     * @return Descripción de las raciones
     */
    public String getRacionesComidaTexto() {
        switch (racionesComida) {
            case 1: return "Escasa";
            case 2: return "Moderada";
            case 3: return "Abundante";
            default: return "Desconocida";
        }
    }

    // ========== UTILIDADES ==========

    /**
     * Obtiene un resumen del estado de la caravana.
     *
     * @return String con el estado de la caravana
     */
    public String getEstado() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Estado de la Caravana ===\n");
        sb.append("Miembros vivos: ").append(getMiembrosVivos()).append("/").append(getTotalMiembros()).append("\n");
        sb.append("Comida: ").append(comida).append(" lbs\n");
        sb.append("Ritmo: ").append(getRitmoViajeTexto()).append("\n");
        sb.append("Raciones: ").append(getRacionesComidaTexto()).append("\n");
        sb.append("\nMiembros:\n");
        for (Miembro m : miembros) {
            sb.append("  - ").append(m.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Caravana{" +
                "miembros=" + miembros.size() +
                ", vivos=" + getMiembrosVivos() +
                ", comida=" + comida +
                ", ritmo=" + getRitmoViajeTexto() +
                ", raciones=" + getRacionesComidaTexto() +
                '}';
    }


}


