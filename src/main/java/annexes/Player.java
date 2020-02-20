package annexes;

import ships.*;
import java.io.Serializable;
import java.util.List;
import board.*;

public class Player {
    /* **
     * Attributs
     */
    protected Board board;
    protected Board opponentBoard;
    protected int destroyedCount;
    protected AbstractShip[] ships;
    protected boolean lose;

    /* **
     * Constructeur
     */
    public Player(Board board, Board opponentBoard, List<AbstractShip> ships) {
        this.board = board;
        this.ships = ships.toArray(new AbstractShip[0]);
        this.opponentBoard = opponentBoard;
    }

    /* **
     * Méthodes
     */

    /**
     * Read keyboard input to get ships coordinates. Place ships on given coodrinates.
     */
    public void putShips() {
        boolean done = false;
        int i = 0;

        do {
            AbstractShip s = ships[i];
            String msg = String.format("placer %d : %s(%d)", i + 1, s.getName(), s.getLength());
            System.out.println(msg);
            InputHelper.ShipInput res = InputHelper.readShipInput();
            // TODO set ship orientation
            // TODO put ship at given position

            // TODO when ship placement successful
            ++i;
            done = i == 5;

            board.print();
        } while (!done);
    }

    public HitType sendHit(int[] coords) {
        boolean done = false;
        HitType hit = null;

        do {
            System.out.println("Make your shot:\n");
            InputHelper.CoordInput hitInput = InputHelper.readCoordInput();
            
            if (board.GetHits()[hitInput.x][hitInput.y] == HitType.NONE)
                hit = opponentBoard.sendHit(hitInput.x, hitInput.y);
            else {
                System.out.println("You have already shot there. Please, repeat entering\n");
                continue;
            }

            coords = new int[2];
            coords[0] = hitInput.x;
            coords[1] = hitInput.y;
            done = true;  
        } while (!done);

        return hit;
    }

    public AbstractShip[] getShips() {
        return ships;
    }

    public void setShips(AbstractShip[] ships) {
        this.ships = ships;
    }
}
