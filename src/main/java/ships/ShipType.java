package ships;
/**
 * Enum variable with the different Ships
 */
public enum ShipType {
    D(0), S(1), B(2), C(3);

    private int index;
    private String name;
    private int size;
    /**
     * Select a ship
     * @param desiredIndex value to select a ship, 0: Destroyer, 1: Submarine, 2: Battleship, 3: Carrier
     */
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
    /**
     * returns the index
     * @return index
     */
    public int getIndex() { return this.index; };
    /**
     * returns the name of the ship
     * @return name
     */
    public String getName() { return this.name; };
    /**
     * returns the size of the ship
     * @return size
     */
    public int getSize() { return this.size; };
    /**
     * sets the index to the given value
     * @param newIndex index to change to
     */
    public void setIndex(int newIndex) { this.index = newIndex; };
    /**
     * sets the name to the given value
     * @param newName name to change to
     */
    public void setName(String newName) { this.name = newName; };
    /**
     * sets the size to the given value
     * @param newSize size to change to
     */
    public void setSize(int newSize) { this.size = newSize; };
}