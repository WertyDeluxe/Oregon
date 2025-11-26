package org.oniteam.oregontrailfx.model;

import java.util.ArrayList;

public class OregonTrail {
    private ArrayList<Achivement> achivements = new ArrayList<>();
    private TreeAchivement treeAchivement = new TreeAchivement();

    /**
     * Inicializa los 10 logros del juego Oregon Trail.
     * Los logros están ordenados por dificultad (1=fácil, 10=extremo).
     */
    public void insertAllachivementsToArraylist(){
        // Logros de Exploración (1-3)
        Achivement a1 = new Achivement(1,
                "Primer Paso",
                "Da tu primer paso en el sendero de Oregón");

        Achivement a2 = new Achivement(2,
                "Explorador Novato",
                "Recorre 100 millas del camino");

        Achivement a3 = new Achivement(3,
                "Cruzando las Llanuras",
                "Completa el primer escenario (Llanuras)");

        // Logros de Combate (4-6)
        Achivement a4 = new Achivement(4,
                "Primera Sangre",
                "Elimina tu primer enemigo");

        Achivement a5 = new Achivement(5,
                "Tirador Experto",
                "Elimina 10 enemigos sin recibir daño");

        Achivement a6 = new Achivement(6,
                "Maestro del Rifle",
                "Elimina 5 enemigos con headshots usando el rifle");

        // Logros de Supervivencia (7-8)
        Achivement a7 = new Achivement(7,
                "Superviviente",
                "Mantén a todos los miembros vivos por 50 días");

        Achivement a8 = new Achivement(8,
                "Gestor de Recursos",
                "Completa un escenario con más de 500 lbs de comida");

        // Logros de Victoria (9-10)
        Achivement a9 = new Achivement(9,
                "Conquistador de Oregón",
                "Completa el juego llegando a Oregón");

        Achivement a10 = new Achivement(10,
                "Leyenda del Sendero",
                "Completa el juego sin que muera ningún miembro de la caravana");

        // Agregar en orden mixto para probar el ABB
        achivements.add(a5);
        achivements.add(a4);
        achivements.add(a3);
        achivements.add(a2);
        achivements.add(a1);
        achivements.add(a6);
        achivements.add(a7);
        achivements.add(a8);
        achivements.add(a9);
        achivements.add(a10);
    }

    /**
     * Inserta todos los logros en el árbol binario de búsqueda.
     */
    public void insertAchivementsToTree(){
        for(int i = 0; i < achivements.size(); i++){
            NodeAchivement n = new NodeAchivement(achivements.get(i), null, null);
            treeAchivement.insertInAllAchivement(n);
        }
    }

    /**
     * Obtiene el ArrayList de logros.
     */
    public ArrayList<Achivement> getAchivements() {
        return achivements;
    }

    /**
     * Obtiene el árbol de logros.
     */
    public TreeAchivement getTreeAchivement() {
        return treeAchivement;
    }

    /**
     * Desbloquea un logro para el jugador si cumple la dificultad.
     *
     * @param dificultad la dificultad del logro a desbloquear
     * @return true si se desbloqueó exitosamente
     */
    public boolean unlockAchivement(int dificultad) {
        for (Achivement a : achivements) {
            if (a.getDifficulty() == dificultad) {
                NodeAchivement node = new NodeAchivement(a, null, null);
                treeAchivement.insert(node);
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si un logro ya fue desbloqueado por el jugador.
     *
     * @param dificultad la dificultad del logro
     * @return true si el jugador lo tiene
     */
    public boolean hasAchivement(int dificultad) {
        Achivement temp = new Achivement(dificultad, "", "");
        NodeAchivement node = new NodeAchivement(temp, null, null);
        return treeAchivement.search(node) != null;
    }
}