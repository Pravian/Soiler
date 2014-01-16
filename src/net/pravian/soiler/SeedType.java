package net.pravian.soiler;

import java.util.Random;

public enum SeedType {

    OFF("off", 0, (byte) 0x0),
    WHEAT("wheat", 59, (byte) 0x7),
    CARROT("carrot", 141, (byte) 0x8),
    POTATO("potato", 142, (byte) 0x8),
    RANDOM("random", 0, (byte) 0x0);
    private final String name;
    private final int id;
    private final byte data;

    private SeedType(String type, int id, byte data) {
        this.name = type;
        this.id = id;
        this.data = data;
    }

    public static SeedType fromName(String type) {
        for (SeedType t : SeedType.values()) {
            if (type.equals(t.getName())) {
                return t;
            }
        }
        return SeedType.OFF;
    }

    public static SeedType getRandom() {
        final Random random = new Random();

        switch (random.nextInt(3)) {
            case 0: {
                return SeedType.CARROT;
            }
            case 1: {
                return SeedType.POTATO;
            }
            case 2: {
                return SeedType.WHEAT;
            }
            default: {
                return null;
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public byte getData() {
        return data;
    }
}
