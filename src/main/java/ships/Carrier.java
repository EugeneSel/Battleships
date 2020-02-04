package ships;

public class Carrier extends AbstractShip {
    public Carrier() {
        super(ShipType.C, ShipOrientation.NORTH);
    };

    public Carrier(ShipOrientation new_orientation) {
        super(ShipType.C, new_orientation);
    };
}