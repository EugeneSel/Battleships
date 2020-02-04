package ships;

public class BattleShip extends AbstractShip {
    public BattleShip() {
        super(ShipType.B, ShipOrientation.NORTH);
    };

    public BattleShip(ShipOrientation new_orientation) {
        super(ShipType.B, new_orientation);
    };
}