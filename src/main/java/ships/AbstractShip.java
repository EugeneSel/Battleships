package ships;

public abstract class AbstractShip {
    protected final ShipType type;
    protected String name;
    protected int size;
    protected ShipOrientation orientation;

    public AbstractShip(ShipType new_type, ShipOrientation new_orientation) {
        this.type = new_type;
        this.name = new_type.get_name();
        this.size = new_type.get_size();
        this.orientation = new_orientation;
    };

    public ShipType get_type() { return this.type; };
    public String get_name() { return this.name; };
    public int get_size() { return this.size; };
    public ShipOrientation get_orientation() { return this.orientation; };

    public void set_name(String new_name) { this.name = new_name; };
    public void set_size(int new_size) { this.size = new_size; };
    public void set_orientation(ShipOrientation new_orientation) { this.orientation = new_orientation; };
}