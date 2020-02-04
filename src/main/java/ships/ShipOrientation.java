package ships;

public enum ShipOrientation {
    NORTH(0), SOUTH(1), EAST(2), WEST(3);

    private int index;

    private ShipOrientation(int desired_index) {
        this.index = desired_index;
    };

    public int get_index() { return this.index; };
    
    public void set_index(int new_index) { this.index = new_index; };
}