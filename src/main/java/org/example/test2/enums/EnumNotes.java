package org.example.test2.enums;

public enum EnumNotes {
    // Première ligne : 0Bh à 12h
    NOTE1("0Bh", "0h"),
    NOTE2("0Ch", "1h"),
    NOTE3("0Dh", "2h"),
    NOTE4("0Eh", "3h"),
    NOTE5("0Fh", "4h"),
    NOTE6("10h", "5h"),
    NOTE7("11h", "6h"),
    NOTE8("12h", "7h"),

    // Deuxième ligne : 15h à 1Ch
    NOTE9("15h", "8h"),
    NOTE10("16h", "9h"),
    NOTE11("17h", "Ah"),
    NOTE12("18h", "Bh"),
    NOTE13("19h", "Ch"),
    NOTE14("1Ah", "Dh"),
    NOTE15("1Bh", "Eh"),
    NOTE16("1Ch", "Fh"),

    // Troisième ligne : 1Fh à 26h
    NOTE17("1Fh", "10h"),
    NOTE18("20h", "11h"),
    NOTE19("21h", "12h"),
    NOTE20("22h", "13h"),
    NOTE21("23h", "14h"),
    NOTE22("24h", "15h"),
    NOTE23("25h", "16h"),
    NOTE24("26h", "17h"),

    // Quatrième ligne : 29h à 30h
    NOTE25("29h", "18h"),
    NOTE26("2Ah", "19h"),
    NOTE27("2Bh", "1Ah"),
    NOTE28("2Ch", "1Bh"),
    NOTE29("2Dh", "1Ch"),
    NOTE30("2Eh", "1Dh"),
    NOTE31("2Fh", "1Eh"),
    NOTE32("30h", "1Fh"),

    // Cinquième ligne : 33h à 3Ah
    NOTE33("33h", "20h"),
    NOTE34("34h", "21h"),
    NOTE35("35h", "22h"),
    NOTE36("36h", "23h"),
    NOTE37("37h", "24h"),
    NOTE38("38h", "25h"),
    NOTE39("39h", "26h"),
    NOTE40("3Ah", "27h"),

    // Sixième ligne : 3Dh à 44h
    NOTE41("3Dh", "28h"),
    NOTE42("3Eh", "29h"),
    NOTE43("3Fh", "2Ah"),
    NOTE44("40h", "2Bh"),
    NOTE45("41h", "2Ch"),
    NOTE46("42h", "2Dh"),
    NOTE47("43h", "2Eh"),
    NOTE48("44h", "2Fh"),

    // Septième ligne : 47h à 4Eh
    NOTE49("47h", "30h"),
    NOTE50("48h", "31h"),
    NOTE51("49h", "32h"),
    NOTE52("4Ah", "33h"),
    NOTE53("4Bh", "34h"),
    NOTE54("4Ch", "35h"),
    NOTE55("4Dh", "36h"),
    NOTE56("4Eh", "37h"),

    // Huitième ligne : 51h à 58h
    NOTE57("51h", "38h"),
    NOTE58("52h", "39h"),
    NOTE59("53h", "3Ah"),
    NOTE60("54h", "3Bh"),
    NOTE61("55h", "3Ch"),
    NOTE62("56h", "3Dh"),
    NOTE63("57h", "3Eh"),
    NOTE64("58h", "3Fh");

    private final String number;
    private final String color;

    // Constructeur de l'enum
    EnumNotes(String number, String color) {
        this.number = number;
        this.color = color;
    }

    // Getter pour obtenir le numéro hexadécimal
    public String getNumber() {
        return number;
    }

    // Getter pour obtenir la couleur associée
    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return number + " -> [" + color + "]";
    }
}