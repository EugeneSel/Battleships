package ships;

public class Destroyer extends AbstractShip {
    public Destroyer() {
        super(ShipType.D, ShipOrientation.N);
    };

    public Destroyer(ShipOrientation newOrientation) {
        super(ShipType.D, newOrientation);
    };
}