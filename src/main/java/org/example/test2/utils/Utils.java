package org.example.test2.utils;

public class Utils {
    public byte[] createSysExMessage(int status, int note, int color) {
        byte[] sysexMessage = {
                (byte) 0xF0,  // Début du message SysEx
                (byte) 0x00, (byte) 0x20, (byte) 0x29, (byte) 0x02, (byte) 0x0C, (byte) 0x03,  // ID du fabricant
                (byte) status, (byte) note, (byte) color,
                (byte) 0xF7   // Fin du message SysEx
        };

        return sysexMessage;
    }


    // F0H --> 0xF0
    public byte[] convertHexToByte(String hexa){
        // Divisez la chaîne en tokens à l'aide de l'espace comme séparateur
        String[] tokens = hexa.split(" ");

        // Créez un tableau de bytes
        byte[] byteArray = new byte[tokens.length];

        for (int i = 0; i < tokens.length; i++) {
            // Supprimez "h" et convertissez en valeur hexadécimale
            int value = Integer.parseInt(tokens[i].substring(0, tokens[i].length() - 1), 16);

            // Convertissez la valeur en byte
            byteArray[i] = (byte) value;
        }

        // Affichez le tableau de bytes
        for (byte b : byteArray) {
            System.out.print(b + " ");
        }

        return byteArray;
    }


    // Change du text en code ascii
    public String changeTextToHex(String text) {
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int asciiValue = (int) c;

            String hexValue = Integer.toHexString(asciiValue);
            hexString.append(hexValue);

            if (i < text.length() - 1) {
                hexString.append("h ");
            }
        }
        return hexString.append("h").toString();
    }
}
