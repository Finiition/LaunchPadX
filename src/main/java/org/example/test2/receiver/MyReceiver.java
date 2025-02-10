package org.example.test2.receiver;

import org.example.test2.enums.NumberNote;
import org.example.test2.utils.Utils;

import javax.sound.midi.*;
import javax.sound.sampled.Clip;

public class MyReceiver implements Receiver {
    private Clip audioClip;
    private Receiver receiverOutput1;

    public MyReceiver() {
        try {
            receiverOutput1 = MidiSystem.getMidiDevice(MidiSystem.getMidiDeviceInfo()[5]).getReceiver(); // Ouverture d'un Receiver
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        if (message instanceof ShortMessage) {
            ShortMessage shortMessage = (ShortMessage) message;
            int command = shortMessage.getCommand();
            int channel = shortMessage.getChannel();
            int data1 = shortMessage.getData1();
            int data2 = shortMessage.getData2();
            Utils utils = new Utils();

            System.out.println("Command " + command);

            if(command == 144){
                pressKeyChangeColor(command, channel, data1, data2, utils);
            }

            switch (command) {
                case 144:
                    pressKeyChangeColor(command, channel, data1, data2, utils);
                    break;
                case 176:
                    resetAllColor(command, channel, data1, data2, utils);
                    System.out.println("RESET");
                    break;
                default:
                    System.out.println("AUTRE NOTE");
            }


            //pressKeyToNumber(command, channel, data1, data2, utils);
        }

    }

    private void pressKeyToNumber(int command, int channel, int data1, int data2, Utils utils) {
        // 144 - NOTE_ON
        // 160 - POLY_PRESSURE

        // Prise en compte de la touche pressée
        if (command == ShortMessage.NOTE_ON && channel == 0) {
            if (data2 != 0) {
                try {
                    // Envois le text de son choix
                    String debut = "F0h 00h 20h 29h 02h 0Ch 07h 01h 07h 00h 05h ";
                    String textAConvertir = utils.changeTextToHex(String.valueOf(data1-10));
                    String fin = " F7h";
                    byte[] bigMessage = utils.convertHexToByte(debut + textAConvertir + fin);
                    SysexMessage bigMessageTest = new SysexMessage();
                    bigMessageTest.setMessage(bigMessage, bigMessage.length);
                    receiverOutput1.send(bigMessageTest, -1);
                } catch (InvalidMidiDataException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Note " + data1 + " ON");
            }
        }
    }

    private void pressKeyChangeColor(int command, int channel, int data1, int data2, Utils utils){
        if (command == ShortMessage.NOTE_ON && channel == 0) {
            if (data2 != 0) {
                try {
                    // Envois le text de son choix
                    String numberNote = intToHex(data1);
                    String debut = "F0h 00h 20h 29h 02h 0Ch 03h 00h " + numberNote + " 00h F7h";
                    byte[] bigMessage = utils.convertHexToByte(debut);
                    SysexMessage bigMessageTest = new SysexMessage();
                    bigMessageTest.setMessage(bigMessage, bigMessage.length);
                    receiverOutput1.send(bigMessageTest, -1);
                } catch (InvalidMidiDataException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Note " + data1 + " ON");
            }
        }
    }

    /**
     * Reset toutes les touches
     * @param command
     * @param channel
     * @param data1
     * @param data2
     * @param utils
     */
    private void resetAllColor(int command, int channel, int data1, int data2, Utils utils){
        if (command == 176 && channel == 0) {
            if (data2 != 0) {
                try {
                    String debut = "F0h 00h 20h 29h 02h 0Ch 03h";
                    String notes = "";
                    for (NumberNote note : NumberNote.values()) {
                        notes += " 00h " + note.toString() + " 00h";
                    }
                    String fin = " F7h";
                    String total = debut + notes + fin;
                    System.out.println(total);
                    byte[] bigMessage = utils.convertHexToByte(debut + notes + fin);
                    SysexMessage bigMessageTest = new SysexMessage();
                    bigMessageTest.setMessage(bigMessage, bigMessage.length);
                    receiverOutput1.send(bigMessageTest, -1);
                } catch (InvalidMidiDataException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Note " + data1 + " ON");
            }
        }
    }

    private String intToHex(int number){
        return Integer.toHexString(number).toUpperCase() + "h";
    }

    @Override
    public void close() {
        if (audioClip.isRunning()) {
            audioClip.stop();
        }
    }
}
