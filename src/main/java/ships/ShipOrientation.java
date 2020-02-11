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
            if (orientation.get_character().equals(valueToValidate)) {
                return orientation;
            }
        
        return null;
    }

    public int get_index() { return this.index; };
    public String get_character() { return this.character; };
    
    public void set_index(int new_index) { this.index = new_index; };
}