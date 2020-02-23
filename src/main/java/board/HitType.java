package board;
/**
 * Enum Variable for the Hits Types,
 * None: inexistant, 
 * Hit: ship shot, 
 * Miss: Ship missed, 
 * Kill: Ship Sunked
 */
public enum HitType{
    NONE(0), HIT(1), MISS(2), KILL(3);

    public int index;
    /**
     * sets the index to the value sended
     * @param desiredIndex to save
     */
    private HitType(int desiredIndex) {
        this.index = desiredIndex;
    }
}