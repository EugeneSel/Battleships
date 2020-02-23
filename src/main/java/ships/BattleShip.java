package ships;
/**
 * Child of Abstract Ship, Saves the information for a Battleship
 */
public class BattleShip extends AbstractShip {
    /**
     * Default Constructor of battleship with orientation North
     */
    public BattleShip() {
        super(ShipType.B, ShipOrientation.N);
    };
    /**
     * Constructor with orientation specified
     * @param newOrientation orientation of battleship
     */
    public BattleShip(ShipOrientation newOrientation) {
        super(ShipType.B, newOrientation);
    };
}