package ships;

public class Submarine extends AbstractShip {
    public Submarine() {
        super(ShipType.S, ShipOrientation.N);
    };

    public Submarine(ShipOrientation newOrientation) {
        super(ShipType.S, newOrientation);
    };
}