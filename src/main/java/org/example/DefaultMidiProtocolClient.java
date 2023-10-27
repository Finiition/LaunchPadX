package org.example;

import javax.sound.midi.*;

public class DefaultMidiProtocolClient {

    /**
     * The Launchpad's Receiver, to which commands are sent.
     */
    private final Receiver receiver;

    public DefaultMidiProtocolClient(Receiver receiver) {
        if (receiver == null) {
            throw new IllegalArgumentException("Receiver must not be null.");
        }
        this.receiver = receiver;
    }

    public void reset() throws InvalidMidiDataException {
        sendShortMessage(ShortMessage.CONTROL_CHANGE, 0, 0);
    }

    public void buttonOn(int button, int color) throws InvalidMidiDataException {
        sendShortMessage(ShortMessage.CONTROL_CHANGE, button, color);
    }

    // ================================================================================
    // Utils
    // ================================================================================

    private void sendShortMessage(int command, int controller, int data) throws InvalidMidiDataException {
        ShortMessage message = new ShortMessage();
        message.setMessage(command, controller, data);
        send(message);
    }

    private void sendShortMessage(int command, int channel, int controller, int data) throws InvalidMidiDataException {
        ShortMessage message = new ShortMessage();
        message.setMessage(command, channel, controller, data);
        send(message);
    }

    public void sendSysExMessage(byte[] data) throws InvalidMidiDataException {
        SysexMessage message = new SysexMessage();
        message.setMessage(data, data.length);
        send(message);
    }

    private void send(MidiMessage message) {
        this.receiver.send(message, -1);
    }
}