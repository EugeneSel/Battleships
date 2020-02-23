package annexes;

import ships.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
/**
 * Class used to make the interface between the information registered by the player and the information that understand the logic
 */
public final class InputHelper {

    /* **
     * Constructeur
     */
    private InputHelper() {}

    /* **
     * Classe ShipInput, interne à InputHelper
     */
    public static class ShipInput {
        public ShipOrientation orientation;
        public int x;
        public int y;
    }

    /* **
     * Classe CoordInput, interne à InputHelper
     */
    public static class CoordInput {
        public int x;
        public int y;
    }

    /* **
     * Méthodes de la classe InputHelper
     */
    public static ShipInput readShipInput() {
        @SuppressWarnings("resource")
        Scanner sin = new Scanner(System.in);
        ShipInput res = new ShipInput();
        boolean done = false;

        do {
            try {
                String[] in = sin.nextLine().toLowerCase().split(" ");
                if (in.length == 2) {
                    String coord = in[0];
                    ShipOrientation orientation = ShipOrientation.valid(in[1]);
                    
                    if (orientation != null) {
                        res.x = coord.charAt(0) - 'a';
                        res.y = Integer.parseInt(coord.substring(1, coord.length())) - 1;
                        res.orientation = orientation;
                        done = true;
                    }
                }
            } catch (Exception e) {
               
            }

            if (!done) {
                System.err.println("Format is incorrect! Please, repeat entering (in the format such as 'A1 n'):\n");
            }
        } while (!done && sin.hasNextLine());

        return res;
    }

    public static CoordInput readCoordInput(int boardSize) throws ArrayIndexOutOfBoundsException {
        @SuppressWarnings("resource")
        Scanner sin = new Scanner(System.in);
        CoordInput res = new CoordInput();
        boolean done = false;

        do {
            try {
                String coord = sin.nextLine().toLowerCase();
                res.x = coord.charAt(0) - 'a';
                res.y = Integer.parseInt(coord.substring(1, coord.length())) - 1;

                if (res.x < 0 || res.x >= boardSize || res.y < 0 || res.y >= boardSize)
                    throw new ArrayIndexOutOfBoundsException("Format is incorrect! Please, repeat entering (in the format such as 'A1'):\n");

                done = true;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                
            }

            if (!done) {
                System.err.println("Format is incorrect! Please, repeat entering (in the format such as 'A1'):");
            }
        } while (!done && sin.hasNextLine());

        return res;
    }
}
