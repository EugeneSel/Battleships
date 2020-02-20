package ships;

public class BattleShip extends AbstractShip {
    public BattleShip() {
        super(ShipType.B, ShipOrientation.N);
    };

    public BattleShip(ShipOrientation newOrientation) {
        super(ShipType.B, newOrientation);
    };
}