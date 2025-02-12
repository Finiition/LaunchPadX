package org.example.test2.objects;

public class Note {
    private String channel;
    private String number;
    private String velocity;

    // Constructeur
    public Note(String channel, String number, String velocity) {
        this.channel = channel;
        this.number = number;
        this.velocity = velocity;
    }

    // Getters
    public String getChannel() {
        return channel;
    }

    public String getNumber() {
        return number;
    }

    public String getVelocity() {
        return velocity;
    }

    // Setters
    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setVelocity(String velocity) {
        this.velocity = velocity;
    }

    // Méthode toString
    @Override
    public String toString() {
        return " 00h " + number + " " + velocity;
    }
}