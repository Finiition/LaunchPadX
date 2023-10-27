package org.example.test;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;

public class test {
    public static void main(String[] args) {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();

        for (MidiDevice.Info info : infos) {
            System.out.println("Name: " + info.getName());
            System.out.println("Vendor: " + info.getVendor());
            System.out.println("Description: " + info.getDescription());
            System.out.println("Version: " + info.getVersion());
            System.out.println();
        }
    }
}
