package org.example.test2.enums;

public enum GridPosition {
    x1y1(0, 0, 11),
    x1y2(0, 1, 12),
    x1y3(0, 2, 13),
    x1y4(0, 3, 14),
    x1y5(0, 4, 15),
    x1y6(0, 5, 16),
    x1y7(0, 6, 17),
    x1y8(0, 7, 18),

    x2y1(1, 0, 21),
    x2y2(1, 1, 22),
    x2y3(1, 2, 23),
    x2y4(1, 3, 24),
    x2y5(1, 4, 25),
    x2y6(1, 5, 26),
    x2y7(1, 6, 27),
    x2y8(1, 7, 28),

    x3y1(2, 0, 31),
    x3y2(2, 1, 32),
    x3y3(2, 2, 33),
    x3y4(2, 3, 34),
    x3y5(2, 4, 35),
    x3y6(2, 5, 36),
    x3y7(2, 6, 37),
    x3y8(2, 7, 38),

    x4y1(3, 0, 41),
    x4y2(3, 1, 42),
    x4y3(3, 2, 43),
    x4y4(3, 3, 44),
    x4y5(3, 4, 45),
    x4y6(3, 5, 46),
    x4y7(3, 6, 47),
    x4y8(3, 7, 48),

    x5y1(4, 0, 51),
    x5y2(4, 1, 52),
    x5y3(4, 2, 53),
    x5y4(4, 3, 54),
    x5y5(4, 4, 55),
    x5y6(4, 5, 56),
    x5y7(4, 6, 57),
    x5y8(4, 7, 58),

    x6y1(5, 0, 61),
    x6y2(5, 1, 62),
    x6y3(5, 2, 63),
    x6y4(5, 3, 64),
    x6y5(5, 4, 65),
    x6y6(5, 5, 66),
    x6y7(5, 6, 67),
    x6y8(5, 7, 68),

    x7y1(6, 0, 71),
    x7y2(6, 1, 72),
    x7y3(6, 2, 73),
    x7y4(6, 3, 74),
    x7y5(6, 4, 75),
    x7y6(6, 5, 76),
    x7y7(6, 6, 77),
    x7y8(6, 7, 78),

    x8y1(7, 0, 81),
    x8y2(7, 1, 82),
    x8y3(7, 2, 83),
    x8y4(7, 3, 84),
    x8y5(7, 4, 85),
    x8y6(7, 5, 86),
    x8y7(7, 6, 87),
    x8y8(7, 7, 88);

    private final int x;
    private final int y;
    private final int value;

    GridPosition(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }
}
