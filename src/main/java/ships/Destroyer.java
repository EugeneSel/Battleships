package ships;

public class Destroyer extends AbstractShip {
    public Destroyer() {
        super(ShipType.D, ShipOrientation.N);
    };

    public Destroyer(ShipOrientation new_orientation) {
        super(ShipType.D, new_orientation);
    };
}