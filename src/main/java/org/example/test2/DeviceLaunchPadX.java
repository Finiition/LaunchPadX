package org.example.test2;

import org.example.test2.receiver.MyReceiver;

import javax.sound.midi.*;

public class DeviceLaunchPadX {
    Receiver receiverOutPut;

    /**
     * Lancement de l'application et initialisation des receiver
     * @throws MidiUnavailableException
     */
    public void runApp() throws MidiUnavailableException {
        // Ouvrez le dispositif MIDI de sortie
        MidiDevice deviceOutPut = MidiSystem.getMidiDevice(MidiSystem.getMidiDeviceInfo()[5]);
        deviceOutPut.open();

        // Ouvrez le dispositif MIDI d'entrée
        MidiDevice deviceInPut = MidiSystem.getMidiDevice(MidiSystem.getMidiDeviceInfo()[7]);
        deviceInPut.open();

        // Programme --> Launchpad

        receiverOutPut = deviceOutPut.getReceiver();

        // Launchpad --> Programme
        Receiver receiverInput = new MyReceiver();// Remplacez MyMIDIReceiver par votre propre implémentation du récepteur MIDI
        deviceInPut.getTransmitter().setReceiver(receiverInput);
    }

    /**
     * Lance le mod developpeur
     * @throws InvalidMidiDataException
     */
    public void devMode() throws InvalidMidiDataException {
        byte[] programmerMode = { (byte) 0xF0, 0x00, 0x20, 0x29, 0x02, 0x0C, 0x00, 0x7F, (byte) 0xF7 };
        SysexMessage sysexMessageDev = new SysexMessage();
        sysexMessageDev.setMessage(programmerMode, programmerMode.length);
        receiverOutPut.send(sysexMessageDev, -1);
    }
}
