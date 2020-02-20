package ships;

public class ShipState {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    private AbstractShip currentShip;
    private boolean struck;

    public ShipState(AbstractShip newShip) {
        this.currentShip = newShip;
        this.struck = false;
    };

    /**
     * @return the currentShip
     */
    public AbstractShip getCurrentShip() {
        return currentShip;
    }
    public boolean isStruck() {
        return struck;
    }

    /**
     * @param currentShip the currentShip to set
     */
    public void setCurrentShip(AbstractShip newShip) {
        this.currentShip = newShip;
    }
    public void addStrike() {
        currentShip.addStrike();
        this.struck = true;
    }

    public boolean isSunk() {
        if (currentShip.getSize() == currentShip.getStrikeCount())
            return true;
        return false;
    }

    public String toString() {
        if (this.struck)
            return ANSI_RED + currentShip.getType().toString() + ANSI_RESET;
        else
            return currentShip.getType().toString();
    }
}