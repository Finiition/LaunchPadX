package org.example;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import javax.sound.midi.*;
import javax.sound.sampled.*;
import java.io.*;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

public class MyMIDIReceiver implements Receiver {
    private Clip audioClip;

    private MidiDevice lpx_in, lpx_out, fl_in, fl_out, fl_out_native;

    @Override
    public void send(MidiMessage message, long timeStamp) {
        if (message instanceof ShortMessage) {
            ShortMessage shortMessage = (ShortMessage) message;
            int command = shortMessage.getCommand();
            int channel = shortMessage.getChannel();
            int data1 = shortMessage.getData1();
            int data2 = shortMessage.getData2();

            // Exemple : Canal pour le message SysEx
            int sysExChannel = 0; // Vous pouvez ajuster le canal SysEx en fonction de la configuration de votre Launchpad X
            int redColor = 5; // Exemple : couleur rouge (5)

            // Exemple : Si c'est un message "Note On" (command = 0x90) sur le canal 0
            if (command == ShortMessage.NOTE_ON && channel == 0) {
                if (data2 != 0) {
                    try {
                        // Construction du message SysEx
                        byte[] sysExMessage = {
                                (byte) 0xF0, // Début SysEx
                                0x00, 0x20, 0x29, 0x02, // En-tête du message SysEx (adresse du Launchpad X)
                                0x0C, 0x10, 0x01, // Données du message SysEx
                                (byte) sysExChannel, (byte) data1, (byte) redColor, // Canal, note, couleur
                                (byte) 0xF7 // Fin SysEx
                        };

                        // Création d'un message MIDI SysexMessage
                        SysexMessage sysexMessage = new SysexMessage();
                        sysexMessage.setMessage(sysExMessage, sysExMessage.length);

                        // Envoyer le message SysEx
                        this.send(sysexMessage, timeStamp);
                    } catch (InvalidMidiDataException e) {
                        throw new RuntimeException(e);
                    }
                    // playWAV();
                    System.out.println("Note On - Note : " + data1 + " Vélocité : " + data2 + " Channel : " + channel + " Command : " + command);
                }
            }
        }
    }

    @Override
    public void close() {
        if (audioClip.isRunning()) {
            audioClip.stop();
        }
    }

    private void playWAV() {
        try {
            // Ouvrez le fichier MP3 avec JLayer
            FileInputStream fileInputStream = new FileInputStream("C:\\Workspace\\soundboard-test\\src\\main\\resources\\vine-boom.mp3");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            AdvancedPlayer player = new AdvancedPlayer(bufferedInputStream);

            // Obtenez la ligne de lecture audio
            Mixer.Info[] mixers = AudioSystem.getMixerInfo();
            Mixer mixer = AudioSystem.getMixer(mixers[27]); // Vous pouvez choisir un mixer spécifique ici

            Line.Info sourceInfo = new DataLine.Info(SourceDataLine.class, null);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(sourceInfo);

            // Ouvrez la ligne audio
            line.open();

            // Réglez le gain (volume) via un FloatControl du mixer
            if (mixer.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) mixer.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-30.0f); // Ajustez cette valeur pour le volume souhaité
            }

            line.start();

            // Lecture de l'audio
            player.play();

            // Fermez la ligne de lecture audio
            line.drain();
            line.stop();
            line.close();

        } catch (JavaLayerException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    private void getColorPannel(MidiMessage msg){
        byte[] data = msg.getMessage();
        if(data.length > 6){
            byte sysex = data[6];
            switch (sysex) {
                case 0x22: {
                    //setProgramMode(); //Set layout Session / RX: F0h 00h 20h 29h 02h 18h 22h 00h F7h
                    break;
                }

                case 0x0E:  //Set all LEDs
                    break;

                case 0x0B: {
                    byte[] leds = Arrays.copyOfRange(data, 7, data.length - 1); //Set LEDs RGB mode
                    int nbLeds = leds.length / 4;
                    ByteBuffer payload = ByteBuffer.allocate(1024);
                    for (int i = 0; i < nbLeds; i++) {
                        byte index = leds[4 * i];
                        byte r = (byte) (2 * leds[4 * i + 1]);
                        byte g = (byte) (2 * leds[4 * i + 2]);
                        byte b = (byte) (2 * leds[4 * i + 3]);

                        byte[] cmd = {3, index, r, g, b};
                        payload.put(cmd);
                    }
                    payload.put((byte) 0xF7);
                    payload.flip();
                    sendSysexLPX("F0h 00h 20h 29h 02h 0Ch 03h", payload);   // LED lighting
                    break;
                }

            }
        }
    }

    private void sendSysexLPX(String header, ByteBuffer payload) {
        if (lpx_out == null) {
            return;
        }

        try {
            ByteBuffer outBuffer = ByteBuffer.allocate(1024);
            outBuffer.put(hex2byte(header));
            if (payload != null) {
                outBuffer.put(payload);
            }

            SysexMessage outMsg = new SysexMessage(outBuffer.array(), outBuffer.position());

            lpx_out.open();
            lpx_out.getReceiver().send(outMsg, -1);
            lpx_out.close();
        } catch (MidiUnavailableException | InvalidMidiDataException ex) {
            Logger.getLogger(MyMIDIReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static byte[] hex2byte(String s) {
        String[] values = s.split(" ");
        int nbBytes = values.length;
        byte[] data = new byte[nbBytes];
        for (int i = 0; i < nbBytes; i++) {
            data[i] = (byte) ((Character.digit(values[i].charAt(0), 16) << 4)
                    + Character.digit(values[i].charAt(1), 16));
        }
        return data;
    }
}
