package org.example.test2;

import javax.sound.midi.*;

public class SimpleTestConnectionLaunchpad {
    public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException {
            DeviceLaunchPadX deviceLaunchPadX = new DeviceLaunchPadX();
            deviceLaunchPadX.runApp();
            deviceLaunchPadX.devMode();
    }
}
