package org.example.test2;

import javax.sound.midi.*;
import java.awt.*;

public class SimpleTestConnectionLaunchpad {
    public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, AWTException {
            DeviceLaunchPadX deviceLaunchPadX = new DeviceLaunchPadX();
            deviceLaunchPadX.runApp();
            deviceLaunchPadX.devMode();
    }
}
