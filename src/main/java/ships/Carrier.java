package ships;
/**
 * Child of Abstract Ship, Saves the information for a Carrier
 */
public class Carrier extends AbstractShip {
    /**
     * Default Constructor of carrier with orientation North
     */
    public Carrier() {
        super(ShipType.C, ShipOrientation.N);
    };
    /**
     * Constructor with orientation specified
     * @param newOrientation orientation of carrier
     */
    public Carrier(ShipOrientation newOrientation) {
        super(ShipType.C, newOrientation);
    };
}