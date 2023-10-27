package org.example.test;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MP3Player {
    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream("C:\\Workspace\\soundboard-test\\src\\main\\resources\\vine-boom.mp3");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            AdvancedPlayer player = new AdvancedPlayer(bufferedInputStream);

            player.play();

        } catch (JavaLayerException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
