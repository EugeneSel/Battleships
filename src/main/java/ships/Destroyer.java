package ships;

public class Destroyer extends AbstractShip {
    public Destroyer() {
        super(ShipType.D, ShipOrientation.NORTH);
    };

    public Destroyer(ShipOrientation new_orientation) {
        super(ShipType.D, new_orientation);
    };
}