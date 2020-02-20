package ships;

public class Carrier extends AbstractShip {
    public Carrier() {
        super(ShipType.C, ShipOrientation.N);
    };

    public Carrier(ShipOrientation newOrientation) {
        super(ShipType.C, newOrientation);
    };
}