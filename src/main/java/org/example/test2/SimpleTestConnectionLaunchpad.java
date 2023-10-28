package org.example.test2;

import org.example.test.Pad;
import org.example.test2.receiver.MyReceiver;
import org.example.test2.utils.Utils;

import javax.sound.midi.*;

public class SimpleTestConnectionLaunchpad {


    public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException {
            DeviceLaunchPadX deviceLaunchPadX = new DeviceLaunchPadX();
            deviceLaunchPadX.runApp();
            deviceLaunchPadX.devMode();

            Utils utils = new Utils();

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

            int status = 144; // Statut de la note-on
            int note = 11;    // Numéro de note
            int color = 9; // Vélocité
            byte[] tableauTraduit = utils.createSysExMessage(status, note, color);

            // Developper mode
            /*SysexMessage sysexMessageDev = new SysexMessage();
            sysexMessageDev.setMessage(programmerMode, programmerMode.length);
            receiverOutPut.send(sysexMessageDev, -1);*/

            /*SysexMessage sysexMessageDev2 = new SysexMessage();
            sysexMessageDev2.setMessage(sysexMessage, sysexMessage.length);
            receiver.send(sysexMessageDev2, -1);*/

            // Envois le text hello world
            /*byte[] test1234 = utils.convertHexToByte("F0h 00h 20h 29h 02h 0Ch 07h 01h 07h 00h 25h 48h 65h 6Ch 6Ch 6Fh 20h 57h 6Fh 72h 6Ch 64h F7h");
            SysexMessage testHexTobyte = new SysexMessage();
            testHexTobyte.setMessage(test1234, test1234.length);
            receiverOutPut.send(testHexTobyte, -1);*/

            // Envois le text de son choix
            /*String debut = "F0h 00h 20h 29h 02h 0Ch 07h 01h 07h 00h 05h ";
            String textAConvertir = utils.changeTextToHex("1 2 3 4 5 6 7 8 9 10");
            String fin = " F7h";
            byte[] bigMessage = utils.convertHexToByte(debut + textAConvertir + fin);
            SysexMessage bigMessageTest = new SysexMessage();
            bigMessageTest.setMessage(bigMessage, bigMessage.length);
            receiverOutPut.send(bigMessageTest, -1);*/

            /*Pad one = new Pad(144, 11,5);
            sendMessage(receiver, one);*/

            // Note bleu bas gauche
            /*SysexMessage sysexMessage2 = new SysexMessage();
            sysexMessage2.setMessage(sysexData, sysexData.length);
            receiverOutPut.send(sysexMessage2, -1);*/

            // Note orange bas gauche
           /* SysexMessage sysexMessageDecimalToSysex = new SysexMessage();
            sysexMessageDecimalToSysex.setMessage(tableauTraduit, tableauTraduit.length);
            receiver.send(sysexMessageDecimalToSysex, -1);*/
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

    // Methode pour convertir du Hex en byte

}
