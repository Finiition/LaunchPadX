package org.example.test2;

import org.example.test.Pad;
import org.example.test2.utils.Utils;

import javax.sound.midi.*;

public class SimpleTestConnectionLaunchpad {
    public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException {
            DeviceLaunchPadX deviceLaunchPadX = new DeviceLaunchPadX();
            deviceLaunchPadX.runApp();
            deviceLaunchPadX.devMode();
    }
}
