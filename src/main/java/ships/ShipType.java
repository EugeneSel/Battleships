package ships;

public enum ShipType {
    D(0), S(1), B(2), C(3);

    private int index;
    private String name;
    private int size;

    private ShipType(int desiredIndex) {
        this.index = desiredIndex;

        switch (index) {
            case 0:
                this.name = "Destroyer";
                this.size = 2;
                break;
            case 1:
                this.name = "Submarine";
                this.size = 3;
                break;
            case 2:
                this.name = "BattleShip";
                this.size = 4;
                break;
            case 3:
                this.name = "Aircraft-Carrier";
                this.size = 5;
                break;
            default:
                break;
        }
    };

    public int getIndex() { return this.index; };
    public String getName() { return this.name; };
    public int getSize() { return this.size; };

    public void setIndex(int newIndex) { this.index = newIndex; };
    public void setName(String newName) { this.name = newName; };
    public void setSize(int newSize) { this.size = newSize; };
}