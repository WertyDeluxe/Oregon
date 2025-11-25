package org.oniteam.oregontrailfx.model;

public class Miembro {

    private String nombre;
    private int edad;
    private boolean vivo;
    private int salud; // 0-100

    /**
     * Constructor de Miembro.
     *
     * @param nombre Nombre del miembro
     * @param edad Edad del miembro
     */
    public Miembro(String nombre, int edad) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }

        this.nombre = nombre;
        this.edad = edad;
        this.vivo = true;
        this.salud = 100; // Empieza con salud completa
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public boolean isVivo() {
        return vivo;
    }

    public int getSalud() {
        return salud;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = Math.max(0, edad);
    }

    public void setSalud(int salud) {
        this.salud = Math.max(0, Math.min(100, salud));
        if (this.salud == 0) {
            this.vivo = false;
        }
    }

    /**
     * Reduce la salud del miembro.
     *
     * @param cantidad Cantidad de salud a reducir
     */
    public void reducirSalud(int cantidad) {
        if (cantidad > 0 && vivo) {
            this.salud = Math.max(0, this.salud - cantidad);
            if (this.salud == 0) {
                morir();
            }
        }
    }

    /**
     * Aumenta la salud del miembro.
     *
     * @param cantidad Cantidad de salud a aumentar
     */
    public void curar(int cantidad) {
        if (cantidad > 0 && vivo) {
            this.salud = Math.min(100, this.salud + cantidad);
        }
    }

    /**
     * Marca al miembro como fallecido.
     */
    public void morir() {
        this.vivo = false;
        this.salud = 0;
    }

    /**
     * Obtiene el estado de salud como texto.
     *
     * @return Descripción del estado de salud
     */
    public String getEstadoSalud() {
        if (!vivo) {
            return "Fallecido";
        } else if (salud >= 80) {
            return "Excelente";
        } else if (salud >= 60) {
            return "Bueno";
        } else if (salud >= 40) {
            return "Regular";
        } else if (salud >= 20) {
            return "Débil";
        } else {
            return "Crítico";
        }
    }

    @Override
    public String toString() {
        return nombre + " (" + edad + " años) - Salud: " + salud + "% - " +
                (vivo ? "Vivo" : "Fallecido");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Miembro)) return false;
        Miembro miembro = (Miembro) o;
        return edad == miembro.edad &&
                nombre.equals(miembro.nombre);
    }

    @Override
    public int hashCode() {
        int result = nombre.hashCode();
        result = 31 * result + edad;
        return result;
    }
}
