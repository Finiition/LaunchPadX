package org.example.test2.receiver;

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

            //System.out.println("Note command : " + data1 + " Vélocité : " + data2 + " Channel : " + channel + " Command : " + command);

            // 144 - NOTE_ON
            // 160 - POLY_PRESSURE

            // Prise en compte de la touche pressée
            if (command == ShortMessage.NOTE_ON && channel == 0) {
                if (data2 != 0) {
                    byte[] note = utils.createSysExMessage(command, data1, 5);

                    byte[] sysexMessage = {
                            (byte) 0xF0,  // Début du message SysEx
                            (byte) 0x00, (byte) 0x20, (byte) 0x29, (byte) 0x02, (byte) 0x0C, (byte) 0x03,// Id du fabricant
                            (byte) 0x00, (byte) 0x63, (byte) 0x05, // Note
                            (byte) 0xF7   // Fin du message SysEx
                    };

                    byte[] sysexData = { (byte) 0xF0,
                            0x00, 0x20, 0x29, 0x02, 0x0C,0x03,
                            0x00, 0x0B, 0x2D,
                            (byte) 0xF7 };


                    try {
                        /*byte[] test1234 = utils.convertHexToByte("F0h 00h 20h 29h 02h 0Ch 07h 01h 07h 00h 25h 48h 65h 6Ch 6Ch 6Fh 20h 57h 6Fh 72h 6Ch 64h F7h");
                        SysexMessage testHexTobyte = new SysexMessage();
                        testHexTobyte.setMessage(test1234, test1234.length);
                        receiverOutput1.send(testHexTobyte, 1000);*/

                        // Envois le text de son choix
                        String debut = "F0h 00h 20h 29h 02h 0Ch 07h 01h 07h 00h 05h ";
                        String textAConvertir = utils.changeTextToHex(String.valueOf(data1-10));
                        String fin = " F7h";
                        byte[] bigMessage = utils.convertHexToByte(debut + textAConvertir + fin);
                        SysexMessage bigMessageTest = new SysexMessage();
                        bigMessageTest.setMessage(bigMessage, bigMessage.length);
                        receiverOutput1.send(bigMessageTest, -1);

                        /*SysexMessage sysexMessage2 = new SysexMessage();
                        sysexMessage2.setMessage(sysexData, sysexData.length);
                        receiverOutput1.send(sysexMessage2, -1);*/
                    } catch (InvalidMidiDataException e) {
                        throw new RuntimeException(e);
                    }
                    /*SysexMessage bigMessageTest = new SysexMessage();
                    bigMessageTest.setMessage(note, note.length);
                    this.send(bigMessageTest, -1);*/


                    //System.out.println("Note On - Note : " + data1 + " Vélocité : " + data2 + " Channel : " + channel + " Command : " + command);
                    System.out.println("Note " + data1 + " On");
                }
            } else if (command == ShortMessage.POLY_PRESSURE && channel == 0){
                System.out.println("Note " + data1 + " Poly Pressure");
            }

            if(data2 == 0) {
                System.out.println("Note " + data1 + " Off");
            }

            if (command == ShortMessage.NOTE_OFF && channel == 0) {
                if (data2 != 0) {
                    byte[] note = utils.createSysExMessage(command, data1, 0);
                    try {
                        SysexMessage bigMessageTest = new SysexMessage();
                        bigMessageTest.setMessage(note, note.length);
                        this.send(bigMessageTest, -1);
                    } catch (InvalidMidiDataException e) {
                        throw new RuntimeException(e);
                    }
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
