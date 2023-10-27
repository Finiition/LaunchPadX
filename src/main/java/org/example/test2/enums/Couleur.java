package org.example.test2.enums;

public enum Couleur {
    JAUNE(13),
    ROUGE(5),
    VERT(21),
    BLEU(41);

    private final int valeur;

    Couleur(int valeur) {
        this.valeur = valeur;
    }

    public int getValeur() {
        return valeur;
    }
}