package clashroyale.models;

/**
 * The type Rage.
 */
public class Rage extends Spells{
    private double duration;

    /**
     * Instantiates a new Card.
     *
     * @param level the level
     */
    public Rage(int level) {
        super(3, 5);
        if (level==1){duration=6;}
        else if (level==2){duration=6.5;}
        else if (level==3){duration=7;}
       else if (level==4){duration=7.5;}
       else if (level==5){duration=8;}
    }
}
