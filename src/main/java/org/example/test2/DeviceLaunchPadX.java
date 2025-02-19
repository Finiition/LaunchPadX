package org.example.test2;

import org.example.test2.receiver.MyReceiver;
import org.example.test2.utils.Utils;

import javax.sound.midi.*;

public class DeviceLaunchPadX {
    Receiver receiverOutPut;
    Utils utils = new Utils();

    /**
     * Lancement de l'application et initialisation des receiver
     * @throws MidiUnavailableException
     */
    public void runApp() throws MidiUnavailableException {

        // Récupère les infos sur les appareils MIDI disponibles
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();

        // Vérifiez et sélectionnez les périphériques nécessaires
        MidiDevice deviceOutPut = null;
        MidiDevice deviceInPut = null;

        // Trouver un périphérique avec un transmitter
        for (MidiDevice.Info info : infos) {
            MidiDevice device = MidiSystem.getMidiDevice(info);
            if (device.getMaxTransmitters() != 0 && deviceOutPut == null) {
                deviceOutPut = device;
            }
            if (device.getMaxReceivers() != 0 && deviceInPut == null) {
                deviceInPut = device;
            }
            if (deviceOutPut != null && deviceInPut != null) break;
        }

        if (deviceOutPut == null || deviceInPut == null) {
            throw new MidiUnavailableException("Impossible de trouver les périphériques compatibles.");
        }

        // Ouvrez le dispositif MIDI de sortie
        deviceOutPut = MidiSystem.getMidiDevice(infos[5]);
        deviceOutPut.open();

        // Ouvrez le dispositif MIDI d'entrée
        deviceInPut = MidiSystem.getMidiDevice(MidiSystem.getMidiDeviceInfo()[7]);
        deviceInPut.open();

        // Obtenir le transmitter (entrée MIDI)
        Transmitter transmitterInput = deviceInPut.getTransmitter();

        // Programme --> Launchpad
        receiverOutPut = deviceOutPut.getReceiver();

        // Configure ton MyReceiver avec le transmitter et le receiver
        String colour = "0Eh";
        int layout = 1;

        // Launchpad --> Programme
        Receiver receiverInput = new MyReceiver(transmitterInput, receiverOutPut, colour, layout);
        deviceInPut.getTransmitter().setReceiver(receiverInput);
    }

    /**
     * Lance le mod developpeur
     * @throws InvalidMidiDataException
     */
    public void devMode() throws InvalidMidiDataException {
        // Dev Mode
        byte[] programmerMode = { (byte) 0xF0, 0x00, 0x20, 0x29, 0x02, 0x0C, 0x00, 0x7F, (byte) 0xF7 };
        SysexMessage sysexMessageDev = new SysexMessage();
        sysexMessageDev.setMessage(programmerMode, programmerMode.length);
        receiverOutPut.send(sysexMessageDev, -1);

        // Ajout des couleurs sur les touches principales
        String debut = "F0h 00h 20h 29h 02h 0Ch 03h 00h 59h 05h 00h 5Bh 15h 00h 5Ch 15h 00h 5Dh 15h F7h";
        byte[] bigMessage = utils.convertHexToByte(debut);
        SysexMessage bigMessageTest = new SysexMessage();
        bigMessageTest.setMessage(bigMessage, bigMessage.length);
        receiverOutPut.send(bigMessageTest, -1);
    }
}
