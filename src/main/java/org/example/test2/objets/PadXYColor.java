package org.example.test2.objets;

import org.example.test2.enums.Couleur;
import org.example.test2.enums.GridPosition;

public class PadXYColor {
    private final GridPosition position;
    private final Couleur couleur;

    public PadXYColor(GridPosition position, Couleur couleur) {
        this.position = position;
        this.couleur = couleur;
    }

    public GridPosition getPosition() {
        return position;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    @Override
    public String toString() {
        return "MonObjet{" +
                "position=" + position +
                ", couleur=" + couleur +
                '}';
    }
}
