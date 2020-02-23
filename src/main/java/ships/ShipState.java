package ships;

import java.io.Serializable;
/**
 * Class that implemtents the possible states for the ships
 */
public class ShipState implements Serializable {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    private AbstractShip currentShip;
    private boolean struck;
    /**
     * Constructor that assigns the given ship an status of not strucked.
     * @param newShip
     */
    public ShipState(AbstractShip newShip) {
        this.currentShip = newShip;
        this.struck = false;
    };

    /**
     * returns the ship that's been used
     * @return the currentShip
     */
    public AbstractShip getCurrentShip() {
        return currentShip;
    }
    /**
     * returns the value of struck
     * @return struck
     */
    public boolean isStruck() {
        return struck;
    }

    /**
     * @param currentShip the currentShip to set
     */
    public void setCurrentShip(AbstractShip newShip) {
        this.currentShip = newShip;
    }
    /**
     * adds a hit to the ship and change the state to being struck
     */
    public void addStrike() {
        currentShip.addStrike();
        this.struck = true;
    }
    /**
     * checks if the ship has been sunked
     * @return true if the amount of hits is equal to the size, false otherwise
     */
    public boolean isSunk() {
        if (currentShip.getSize() == currentShip.getStrikeCount())
            return true;
        return false;
    }
    /**
     * serializes the ship
     */
    public String toString() {
        if (this.struck)
            return ANSI_RED + currentShip.getType().toString() + ANSI_RESET;
        else
            return currentShip.getType().toString();
    }
}