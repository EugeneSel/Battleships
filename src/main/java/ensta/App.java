package ensta;
import annexes.*;
import ships.*;
import board.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void createShips(ShipOrientation[] listOfOrientations) {
        
    };

    public static void main(String[] args)
    {
        Board testboard = new Board("Ric");
        testboard.putShip(new Submarine (ShipOrientation.S), 0, 0);
        testboard.setHit(HitType.HIT, 0, 0);
        testboard.print();
    }
}
