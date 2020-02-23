package ships;

import java.io.Serializable;
/**
 * General Class of ships, it has all the parameters necessary to add a ship to the game. All ships inherit from this class.
 */
public abstract class AbstractShip implements Serializable {
    protected final ShipType type;
    protected String name;
    protected int size;
    protected ShipOrientation orientation;
    protected int strikeCount;
    /**
     * Constructor of Ship, it saves the ship type and its orientation
     * @param newType type of ship
     * @param newOrientation orientation of the ship
     */
    public AbstractShip(ShipType newType, ShipOrientation newOrientation) {
        this.type = newType;
        this.name = newType.getName();
        this.size = newType.getSize();
        this.orientation = newOrientation;
        this.strikeCount = 0;
    };
    /**
     * returns the ship type
     * @return ship type
     */
    public ShipType getType() { return this.type; };
    /**
     * returns the name of the ship associated with its name
     * @return name of the ship
     */
    public String getName() { return this.name; };
    /**
     * returns the size of the ship
     * @return ship size
     */
    public int getSize() { return this.size; };
    /**
     * returns the orientation of the ship
     * @return orientation
     */
    public ShipOrientation getOrientation() { return this.orientation; };
    /**
     * returns the amount of hits the ship has received
     * @return hits count
     */
    public int getStrikeCount () { return this.strikeCount; };
    /**
     * changes the name of the ship to the given value
     * @param newName name to change to
     */
    public void setName(String newName) { this.name = newName; };
    /**
     * changes the size of the ship to the given value
     * @param newSize size to change to
     */
    public void setSize(int newSize) { this.size = newSize; };
    /**
     * changes the orientation of the ship to the given value
     * @param newOrientation orientation to change to
     */
    public void setOrientation(ShipOrientation newOrientation) { this.orientation = newOrientation; };
    /**
     * add a hit to the ship
     */
    public void addStrike() { this.strikeCount++; }
    /**
     * checks if the ship has been sunked
     * @return true if the amount of hits is equal to the size, false otherwise
     */
    public boolean isSunk() {
        if (this.strikeCount == this.size)
            return true;
        return false;
    }
}