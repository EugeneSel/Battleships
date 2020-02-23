package ships;
/**
 * Child of Abstract Ship, Saves the information for a Destroyer
 */
public class Destroyer extends AbstractShip {
    /**
     * Default Constructor of Destroyer with orientation North
     */
    public Destroyer() {
        super(ShipType.D, ShipOrientation.N);
    };
    /**
     * Constructor with orientation specified
     * @param newOrientation orientation of Destroyer
     */
    public Destroyer(ShipOrientation newOrientation) {
        super(ShipType.D, newOrientation);
    };
}