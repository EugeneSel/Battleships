package ships;

public enum ShipType {
    D(0), S(1), B(2), C(3);

    private int index;
    private String name;
    private int size;

    private ShipType(int desired_index) {
        this.index = desired_index;

        switch (index) {
            case 0:
                this.name = "Destroyer";
                this.size = 2;
                break;
            case 1:
                this.name = "Submarine";
                this.size = 3;
                break;
            case 2:
                this.name = "BattleShip";
                this.size = 4;
                break;
            case 3:
                this.name = "Aircraft-Carrier";
                this.size = 5;
                break;
            default:
                break;
        }
    };

    public int get_index() { return this.index; };
    public String get_name() { return this.name; };
    public int get_size() { return this.size; };

    public void set_index(int new_index) { this.index = new_index; };
    public void set_name(String new_name) { this.name = new_name; };
    public void set_size(int new_size) { this.size = new_size; };
}