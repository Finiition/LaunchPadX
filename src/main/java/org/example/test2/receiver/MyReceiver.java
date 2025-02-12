package org.example.test2.receiver;

import org.example.test2.enums.EnumNotes;
import org.example.test2.objects.Note;
import org.example.test2.utils.Utils;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyReceiver implements Receiver {
    private Receiver receiverOutput1;
    private String colourChoosen = "0Ch";
    private Transmitter transmitterInput;
    private int layout = 1;
    List<Note> listNotesDraw = new ArrayList<>();
    List<Note> listNotesColour = new ArrayList<>();
    Utils utils = new Utils();
    long timeStamp = 0;
    int lastNote = 0;

    public MyReceiver(Transmitter transmitterInput, Receiver receiverOutput1, String colourChoosen, int layout) throws MidiUnavailableException {
        this.transmitterInput = transmitterInput;
        this.receiverOutput1 = receiverOutput1;
        this.colourChoosen = colourChoosen;
        this.layout = layout;

        // Mise a jour des 2 layout
        // Draw = layout 1 dessin
        // Colour = layout 0 selection couleur
        for (EnumNotes note : EnumNotes.values()) {
            listNotesDraw.add(new Note("00h", note.getNumber(), "00h"));
            listNotesColour.add(new Note("00h", note.getNumber(), note.getColor()));
        }

        // Connecter le transmitter au receiver pour recevoir les données MIDI
        this.transmitterInput.setReceiver(this);
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        if (message instanceof ShortMessage shortMessage) {
            int command = shortMessage.getCommand();
            int channel = shortMessage.getChannel();
            int data1 = shortMessage.getData1();
            int data2 = shortMessage.getData2();

            if((timeStamp + 10000 > this.timeStamp) && (timeStamp != this.timeStamp) ){
                this.timeStamp = timeStamp;
                this.lastNote = data1;
                System.out.println("TOUCHE " + data1 + " timeStamp " + timeStamp );

                if(command == 144){
                    pressKeyChangeColor(command, channel, data1, data2);
                }

                switch (data1) {
                    case 89 -> reset(command, channel, data1, data2);
                    case 91 -> afficheLayout0(command, channel, data1, data2);
                    case 92 -> afficheLayout1(command, channel, data1, data2);
                    default -> {
                        if(data1 < 88){
                            pressKeyChangeColor(command, channel, data1, data2);
                        }
                    }
                }

                System.out.println("TOUCHE " + data1 );

                if(command == 144){
                    pressKeyChangeColor(command, channel, data1, data2);
                }

                switch (data1) {
                    case 89 -> reset(command, channel, data1, data2);
                    case 91 -> afficheLayout0(command, channel, data1, data2);
                    case 92 -> afficheLayout1(command, channel, data1, data2);
                    default -> {
                        if(data1 < 88){
                            pressKeyChangeColor(command, channel, data1, data2);
                        }
                    }
                }
            }
        }
    }

    private void afficheLayout1(int command, int channel, int data1, int data2){
        layout = 1;
        System.out.println("Layout 1");
        if (channel == 0 && data2 != 0) {
            try {
                // Envois le text de son choix
                StringBuilder stringBuilder = new StringBuilder("F0h 00h 20h 29h 02h 0Ch 03h");
                for (Note note : listNotesDraw) {
                    stringBuilder.append(note.toString());
                }
                stringBuilder.append(" F7h");
                byte[] bigMessage = utils.convertHexToByte(stringBuilder.toString());
                SysexMessage bigMessageTest = new SysexMessage();
                bigMessageTest.setMessage(bigMessage, bigMessage.length);
                receiverOutput1.send(bigMessageTest, -1);
            } catch (InvalidMidiDataException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Change la note pressée par la couleur choisie
     *
     * @param command The MIDI command type. This should be equal to ShortMessage.NOTE_ON to trigger note-related actions.
     * @param channel The MIDI channel. Specific actions are triggered only for channel 0.
     * @param data1   The note number associated with the MIDI message. This value is used to determine the target note.
     * @param data2   The velocity of the note. A non-zero value indicates the note is "ON".
     */
    private void pressKeyChangeColor(int command, int channel, int data1, int data2)  {
        if (command == ShortMessage.NOTE_ON && channel == 0 && layout == 1 && data2 != 0) {
            StringBuilder stringBuilder = new StringBuilder("F0h 00h 20h 29h 02h 0Ch 03h");
            String intToHex = intToHex(data1);
            for (Note note : listNotesDraw) {
                if(Objects.equals(note.getNumber(), intToHex)){
                    note.setVelocity(colourChoosen);
                    stringBuilder.append(note);
                }
            }
            stringBuilder.append(" F7h");
            byte[] bigMessage = utils.convertHexToByte(stringBuilder.toString());
            SysexMessage bigMessageTest = new SysexMessage();
            try {
                bigMessageTest.setMessage(bigMessage, bigMessage.length);
            } catch (InvalidMidiDataException e) {
                throw new RuntimeException(e);
            }
            receiverOutput1.send(bigMessageTest, -1);
        } else if (command == ShortMessage.NOTE_ON && channel == 0 && layout == 0 && data2 != 0) {
            for (Note note : listNotesColour) {
                if(Objects.equals(note.getNumber(), intToHex(data1))){
                    colourChoosen = note.getVelocity();
                    afficheLayout1(command, channel, data1, data2);
                }
            }
        }
    }

    /**
     * Supprime toutes les couleurs de la grid principale
     *
     * @param command The MIDI command, used to determine if reset logic should be applied.
     * @param channel The MIDI channel, specifically acting when channel 0 is provided.
     * @param data1   The first data byte of the MIDI message; unused in this method.
     * @param data2   The second data byte of the MIDI message, where a non-zero value can trigger specific behavior.
     */
    private void reset(int command, int channel, int data1, int data2){
        layout = 1;
        System.out.println("Layout 1");
        if (command == 176 && channel == 0) {
            if (data2 != 0) {
                try {
                    String debut = "F0h 00h 20h 29h 02h 0Ch 03h";
                    String notes = "";
                    for (Note note : listNotesDraw) {
                        notes += " 00h " + note.getNumber() + " 00h";
                        note.setVelocity("00h");
                    }
                    String fin = " F7h";
                    String total = debut + notes + fin;
                    byte[] bigMessage = utils.convertHexToByte(debut + notes + fin);
                    SysexMessage bigMessageTest = new SysexMessage();
                    bigMessageTest.setMessage(bigMessage, bigMessage.length);
                    receiverOutput1.send(bigMessageTest, -1);
                } catch (InvalidMidiDataException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Affiche toutes les couleurs afin de les selectionnées pour le layout 0
     *
     * @param command The MIDI command. The method processes only when the command equals 176.
     * @param channel The MIDI channel. The method is activated only for channel 0.
     * @param data1   The first data byte of the MIDI message; unused in this method.
     * @param data2   The second data byte of the MIDI message. A non-zero value triggers the colour configuration logic.
     */
    private void afficheLayout0(int command, int channel, int data1, int data2){
        layout = 0;
        System.out.println("Layout 0");
        if (command == 176 && channel == 0) {
            if (data2 != 0) {
                try {
                    StringBuilder stringBuilder = new StringBuilder("F0h 00h 20h 29h 02h 0Ch 03h");
                    for (Note note : listNotesColour) {
                        stringBuilder.append(note.toString());
                    }
                    stringBuilder.append(" F7h");
                    byte[] bigMessage = utils.convertHexToByte(stringBuilder.toString());
                    SysexMessage bigMessageTest = new SysexMessage();
                    bigMessageTest.setMessage(bigMessage, bigMessage.length);
                    receiverOutput1.send(bigMessageTest, -1);
                } catch (InvalidMidiDataException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private String intToHex(int number){
        String temp = Integer.toHexString(number);
        if(temp.length() == 1){
            temp = "0" + temp;
        }
        return temp.toUpperCase() + "h";
    }

    private int hexToInt(String hex) {
        // Supprimer le "h" à la fin si présent et convertir en entier
        return Integer.parseInt(hex.replace("h", ""), 16);
    }

    private void pressKeyToNumber(int command, int channel, int data1, int data2) {
        // 144 - NOTE_ON
        // 160 - POLY_PRESSURE

        // Prise en compte de la touche pressée
        if (command == ShortMessage.NOTE_ON && channel == 0) {
            if (data2 != 0) {
                try {
                    // Envois le text de son choix
                    String debut = "F0h 00h 20h 29h 02h 0Ch 07h 01h 07h 00h 05h ";
                    String textAConvertir = utils.changeTextToHex(String.valueOf(data1-10));
                    String fin = " F7h";
                    byte[] bigMessage = utils.convertHexToByte(debut + textAConvertir + fin);
                    SysexMessage bigMessageTest = new SysexMessage();
                    bigMessageTest.setMessage(bigMessage, bigMessage.length);
                    receiverOutput1.send(bigMessageTest, -1);
                } catch (InvalidMidiDataException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Note " + data1 + " ON");
            }
        }
    }

    @Override
    public void close() {
        // Fermer le receiver et le transmitter si nécessaire
        if (transmitterInput != null) {
            transmitterInput.close();
        }
        if (receiverOutput1 != null) {
            receiverOutput1.close();
        }
    }
}
