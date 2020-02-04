package annexes;
public enum HitType{
    NONE(0),HIT(1),MISS(2);

    public int index;

    private HitType(int desired_index) {
        this.index = desired_index;
    }
}