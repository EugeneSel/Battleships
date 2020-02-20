package board;
public enum HitType{
    NONE(0), HIT(1), MISS(2);

    public int index;

    private HitType(int desiredIndex) {
        this.index = desiredIndex;
    }
}