package ships;

public abstract class AbstractShip {
    protected final ShipType type;
    protected String name;
    protected int size;
    protected ShipOrientation orientation;
    protected int strikeCount;

    public AbstractShip(ShipType newType, ShipOrientation newOrientation) {
        this.type = newType;
        this.name = newType.getName();
        this.size = newType.getSize();
        this.orientation = newOrientation;
        this.strikeCount = 0;
    };

    public ShipType getType() { return this.type; };
    public String getName() { return this.name; };
    public int getSize() { return this.size; };
    public ShipOrientation getOrientation() { return this.orientation; };
    public int getStrikeCount () { return this.strikeCount; };

    public void setName(String newName) { this.name = newName; };
    public void setSize(int newSize) { this.size = newSize; };
    public void setOrientation(ShipOrientation newOrientation) { this.orientation = newOrientation; };
    public void addStrike() { this.strikeCount++; }

    public boolean isSunk() {
        if (this.strikeCount == this.size)
            return true;
        return false;
    }
}