package ships;

public enum ShipOrientation {
    N(0, "n"), S(1, "s"), E(2, "e"), W(3, "w");

    private int index;
    private String character;

    private ShipOrientation(int desiredIndex, String charValue) {
        this.index = desiredIndex;

        this.character = charValue;
    };

    public static ShipOrientation valid(String valueToValidate) {
        for (ShipOrientation orientation : ShipOrientation.values())
            if (orientation.getCharacter().equals(valueToValidate)) {
                return orientation;
            }
        
        return null;
    }

    public int getIndex() { return this.index; };
    public String getCharacter() { return this.character; };
    
    public void setIndex(int newIndex) { this.index = newIndex; };
}