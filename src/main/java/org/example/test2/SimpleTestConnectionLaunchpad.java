package org.example.test2;

import org.example.Pad;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.Receiver;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.InvalidMidiDataException;
import java.math.BigInteger;

public class SimpleTestConnectionLaunchpad {
    public static void main(String[] args) {
        try {
            // Ouvrez le dispositif MIDI de sortie
            MidiDevice device = MidiSystem.getMidiDevice(MidiSystem.getMidiDeviceInfo()[5]); // Remplacez par le dispositif approprié

            // Ouvrez le dispositif MIDI
            device.open();
            Receiver receiver = device.getReceiver();

            // Créez un message SysEx
            byte[] programmerMode = { (byte) 0xF0, 0x00, 0x20, 0x29, 0x02, 0x0C, 0x00, 0x7F, (byte) 0xF7 };
            byte[] sysexData = { (byte) 0xF0,
                    0x00, 0x20, 0x29, 0x02, 0x0C,0x03,
                    0x00, 0x0B, 0x2D,
                    (byte) 0xF7 };

            // Change La couleur du logo
            byte[] sysexMessage = {
                    (byte) 0xF0,  // Début du message SysEx
                    (byte) 0x00, (byte) 0x20, (byte) 0x29, (byte) 0x02, (byte) 0x0C, (byte) 0x03,// Id du fabricant
                    (byte) 0x00, (byte) 0x63, (byte) 0x05, // Note
                    (byte) 0xF7   // Fin du message SysEx
            };

            // 2D

            // 0x3D,0x3E,0x3F  0x40,0x41  0x42,    ,0x44
            //      0x34       0x36,0x37  0x38,0x39,0x3A
            // 0x29,0x2a       0x2c,0x2D  0x2E,    ,0x30

            byte[] listeNotes = {(byte) 0x3D, 0x3E, 0x34, 0x29, 0x2a,
                    0x40, 0x41, 0x36, 0x37, 0x2c, 0x2D,
                    0x42, 0x44, 0x38, 0x39, 0x3A, 0x2E, 0x30};

            int status = 144; // Statut de la note-on
            int note = 11;    // Numéro de note
            int color = 9; // Vélocité
            byte[] tableauTraduit = createSysExMessage(status, note, color);

            // Developper mode
            SysexMessage sysexMessageDev = new SysexMessage();
            sysexMessageDev.setMessage(programmerMode, programmerMode.length);
            receiver.send(sysexMessageDev, -1);

            /*SysexMessage sysexMessageDev2 = new SysexMessage();
            sysexMessageDev2.setMessage(sysexMessage, sysexMessage.length);
            receiver.send(sysexMessageDev2, -1);*/

            // Envois le text hello world
            /*byte[] test1234 = convertHexToByte("F0h 00h 20h 29h 02h 0Ch 07h 01h 07h 00h 25h 48h 65h 6Ch 6Ch 6Fh 20h 57h 6Fh 72h 6Ch 64h F7h");
            SysexMessage testHexTobyte = new SysexMessage();
            testHexTobyte.setMessage(test1234, test1234.length);
            receiver.send(testHexTobyte, -1);*/

            // Envois le text de son choix
            String debut = "F0h 00h 20h 29h 02h 0Ch 07h 01h 07h 00h 05h ";
            String textAConvertir = changeTextToHex("Jin");
            String fin = " F7h";
            byte[] bigMessage = convertHexToByte(debut + textAConvertir + fin);
            SysexMessage bigMessageTest = new SysexMessage();
            bigMessageTest.setMessage(bigMessage, bigMessage.length);
            receiver.send(bigMessageTest, -1);

            /*Pad one = new Pad(144, 11,5);
            sendMessage(receiver, one);*/

            // Note orange bas gauche
            /*SysexMessage sysexMessage2 = new SysexMessage();
            sysexMessage2.setMessage(sysexData, sysexData.length);
            receiver.send(sysexMessage2, -1);*/

            // Note orange bas gauche
           /* SysexMessage sysexMessageDecimalToSysex = new SysexMessage();
            sysexMessageDecimalToSysex.setMessage(tableauTraduit, tableauTraduit.length);
            receiver.send(sysexMessageDecimalToSysex, -1);*/


            // Fermez le dispositif MIDI
            device.close();
        } catch (MidiUnavailableException | InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }



    public static byte[] createSysExMessage(int status, int note, int color) {
        byte[] sysexMessage = {
                (byte) 0xF0,  // Début du message SysEx
                (byte) 0x00, (byte) 0x20, (byte) 0x29, (byte) 0x02, (byte) 0x0C, (byte) 0x03,  // ID du fabricant
                (byte) status, (byte) note, (byte) color,
                (byte) 0xF7   // Fin du message SysEx
        };

        return sysexMessage;
    }

    // Change du text en code ascii
    public static String changeTextToHex(String text) {
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int asciiValue = (int) c;

            String hexValue = Integer.toHexString(asciiValue);

            hexString.append(hexValue);

            if (i < text.length() - 1) {
                hexString.append("h ");
            }
        }

        return hexString.append("h").toString();
    }

    public static byte[] createPadSysExMessage(Pad pad){
        byte[] sysexMessage = {
                (byte) 0xF0,  // Début du message SysEx
                (byte) 0x00, (byte) 0x20, (byte) 0x29, (byte) 0x02, (byte) 0x0C, (byte) 0x03, // ID du fabricant
                (byte) pad.getChannel(), (byte) pad.getNote(), (byte) pad.getCouleur(),
                (byte) 0xF7   // Fin du message SysEx
        };

        return sysexMessage;
    }

    public static void sendMessage(Receiver receiver, Pad pad) throws InvalidMidiDataException {
        byte[] padToByteSysex = createPadSysExMessage(pad);
        SysexMessage sysexMessageDev = new SysexMessage();
        sysexMessageDev.setMessage(padToByteSysex, padToByteSysex.length);
        receiver.send(sysexMessageDev, -1);
    }

    // Methode pour convertir du text en Hex


    // Methode pour convertir du Hex en byte
    public static byte[] convertHexToByte(String hexa){
        // Divisez la chaîne en tokens à l'aide de l'espace comme séparateur
        String[] tokens = hexa.split(" ");

        // Créez un tableau de bytes
        byte[] byteArray = new byte[tokens.length];

        for (int i = 0; i < tokens.length; i++) {
            // Supprimez "h" et convertissez en valeur hexadécimale
            int value = Integer.parseInt(tokens[i].substring(0, tokens[i].length() - 1), 16);

            // Convertissez la valeur en byte
            byteArray[i] = (byte) value;
        }

        // Affichez le tableau de bytes
        for (byte b : byteArray) {
            System.out.print(b + " ");
        }

        return byteArray;
    }
}
