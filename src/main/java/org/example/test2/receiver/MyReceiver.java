package org.example.test2.receiver;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.sampled.Clip;

public class MyReceiver implements Receiver {
    private Clip audioClip;
    @Override
    public void send(MidiMessage message, long timeStamp) {
        if (message instanceof ShortMessage) {
            ShortMessage shortMessage = (ShortMessage) message;
            int command = shortMessage.getCommand();
            int channel = shortMessage.getChannel();
            int data1 = shortMessage.getData1();
            int data2 = shortMessage.getData2();

            // Prise en compte de la touche pressée
            if (command == ShortMessage.NOTE_ON && channel == 0) {
                if (data2 != 0) {
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
}
