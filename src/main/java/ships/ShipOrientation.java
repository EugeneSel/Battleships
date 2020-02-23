package ships;
/**
 * Enum variable to identify the Orientation of a Ship based on the cardinal points North: N, South: S, East: E, West: W
 */
public enum ShipOrientation {
    N(0, "n"), S(1, "s"), E(2, "e"), W(3, "w");

    private int index;
    private String character;
    /**
     * Constructor with index and letter as inputs
     * @param desiredIndex index to change to for the enum
     * @param charValue char value to change to
     */
    private ShipOrientation(int desiredIndex, String charValue) {
        this.index = desiredIndex;

        this.character = charValue;
    };
    /**
     * Checks if the selected orientation is a valid option
     * @param valueToValidate orientation to check it's existance
     * @return if the orientation is a valid option returns the orientation, null otherwise
     */
    public static ShipOrientation valid(String valueToValidate) {
        for (ShipOrientation orientation : ShipOrientation.values())
            if (orientation.getCharacter().equals(valueToValidate)) {
                return orientation;
            }
        
        return null;
    }
    /**
     * returns the index
     * @return index
     */
    public int getIndex() { return this.index; };
    /**
     * returns the character that describes the orientation
     * @return character
     */
    public String getCharacter() { return this.character; };
    /**
     * Changes the index to the given value
     * @param newIndex index to change to
     */
    public void setIndex(int newIndex) { this.index = newIndex; };
}