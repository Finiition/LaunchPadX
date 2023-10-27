package org.example.test;

public class Pad {
    private int channel;
    private int note;
    private int couleur;

    public Pad(int channel, int note, int couleur) {
        this.channel = channel;
        this.note = note;
        this.couleur = couleur;
    }

    // Méthodes getters et setters pour chaque attribut
    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getCouleur() {
        return couleur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }
}
