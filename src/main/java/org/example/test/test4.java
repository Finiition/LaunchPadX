package org.example.test;

import javax.sound.midi.*;

public class test4 {
    public static void main(String[] args) {
        try {
            MidiDevice device = MidiSystem.getMidiDevice(MidiSystem.getMidiDeviceInfo()[5]);

            // Ouvrez la connexion MIDI-IN
            device.open();

            // Associez le Launchpad X à un récepteur MIDI pour traiter les messages
            Receiver receiver = new MyMIDIReceiver(); // Remplacez MyMIDIReceiver par votre propre implémentation du récepteur MIDI

            // Écoutez les messages MIDI en utilisant le récepteur
            device.getTransmitter().setReceiver(receiver);

            DefaultMidiProtocolClient defaultMidiProtocolClient = new DefaultMidiProtocolClient(receiver);
            byte[] testMsg = {(byte) 0xf0, 0x00, 0x20, 0x29, 0x02, 0x10, 0x0b, 0x63, 0x00, 0x00, 0x3f, (byte) 0xf7 };
            byte[] programmerMode = { (byte) 0xF0, 0x00, 0x20, 0x29, 0x02, 0x0C, 0x00, 0x7F, (byte) 0xF7 };

            // F0 00 20 29 02 0C 00 7F F7 - Developpeur mode
            // F0h 00h 20h 29h 02h 0Ch 03h 00h 0Bh 0Dh 01h 0Ch 15h 17h 02h 0Dh 25h F7h - Couleur jaune en bas a gauche
            // + 2 autre a droite
            // F0h 00h 20h 29h 02h 0Ch 03h 00h 0Bh 25h F7h - Change la touche jaune en bas a gauche en bleu clair
            // 25h = la couleur

            byte[] changeColorMessage = {
                    (byte) 0xF0, 0x00, 0x20, 0x29, 0x02, 0x0C, 0x03, 0x0B, 0x03, (byte) 0xF7
            };

            SysexMessage message = new SysexMessage();
            message.setMessage(programmerMode, programmerMode.length);
            receiver.send(message, -1);

            SysexMessage message3 = new SysexMessage();
            message3.setMessage(changeColorMessage, changeColorMessage.length);
            receiver.send(message3, -1);


            // Définissez les valeurs du message MIDI
            int status = 144;  // Statut (144 pour note ON)
            int note = 11;    // Note (11 pour la touche 11)
            int velocity = 5; // Vitesse (5 pour la couleur)

            // Créez et envoyez le message MIDI de changement de couleur
            ShortMessage message4 = new ShortMessage();
            message4.setMessage(status, note, velocity);
            receiver.send(message4, -1);

            /*defaultMidiProtocolClient.buttonOn(42,1);
            defaultMidiProtocolClient.sendSysExMessage(programmerMode);*/

            // Command, Channel, code pad, couleur, timestamp
            //receiver.send(new ShortMessage(144, 3, 31,2), -1);

            // Gardez l'application en cours d'exécution
            System.out.println("Attente d'événements de bouton... Appuyez sur Ctrl+C pour quitter.");
            while (true) {
                Thread.sleep(1000); // Laissez le programme en cours d'exécution
            }} catch (MidiUnavailableException | InterruptedException e) {
                e.printStackTrace();
        }catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }
    }

    // Méthode pour jouer un son à partir d'un fichier
    private static void playSound(String soundFilePath) {
        // Ajoutez ici le code pour jouer le son depuis le fichier spécifié
        // Vous pouvez utiliser une bibliothèque de lecture audio, comme JavaSound ou JavaZoom, pour jouer le son.
    }

    public void setVolume(double vol, Receiver receiver)
    {
        System.out.println("Midi volume change request: " + vol);
        try {
            ShortMessage volumeMessage = new ShortMessage();
            for ( int i = 0; i < 16; i++ ) {
                volumeMessage.setMessage(ShortMessage.CONTROL_CHANGE, i, 7, (int) (vol * 127));
                receiver.send(volumeMessage, -1);
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
}
