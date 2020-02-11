package ships;

public class Submarine extends AbstractShip {
    public Submarine() {
        super(ShipType.S, ShipOrientation.N);
    };

    public Submarine(ShipOrientation new_orientation) {
        super(ShipType.S, new_orientation);
    };
}