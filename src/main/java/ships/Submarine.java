package ships;
/**
 * Child of Abstract Ship, Saves the information for a Sunmarine
 */
public class Submarine extends AbstractShip {
    /**
     * Default Constructor of Submarine with orientation North
     */
    public Submarine() {
        super(ShipType.S, ShipOrientation.N);
    };
    /**
     * Constructor with orientation specified
     * @param newOrientation orientation of Submarine
     */
    public Submarine(ShipOrientation newOrientation) {
        super(ShipType.S, newOrientation);
    };
}