package net.darthcraft.soiler;


public enum SoilerType {
    OFF("off", 0, (byte) 0x0),
    WHEAT("wheat", 59, (byte) 0x7),
    CARROT("carrot", 141, (byte) 0x8),
    POTATO("potato", 142, (byte) 0x8),
    ALL("all", 0, (byte) 0x0);

    private String type;
    private int id;
    private byte data;

    private SoilerType(String type, int id, byte data) {
        this.type = type;
        this.id = id;
        this.data = data;
    }

    public static SoilerType getType(String type) {
        for (SoilerType t : SoilerType.values()) {
            if (type.equals(t.toString())) {
                return t;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public byte getData() {
        return data;
    }


    @Override
    public String toString() {
        return type;
    }
}
