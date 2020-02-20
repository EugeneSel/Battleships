package ships;

public class ShipState {
    private AbstractShip currentShip;
    private boolean struck;

    public ShipState() {
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
            return "The ship is struck";
        else
            return "The ship is OK";
    }
}