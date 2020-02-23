package annexes;

import ships.*;
import java.io.Serializable;
import java.util.List;
import board.*;
/**
 * Player Class used to identify a player with it's board
 */
public class Player implements Serializable {
    public static final int NUMBER_OF_DESTROYERS = 1;
    public static final int NUMBER_OF_SUBMARINES = 2;
    public static final int NUMBER_OF_BATTLESHIPS = 1;
    public static final int NUMBER_OF_CARRIERS = 1;
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
    public void putShips() throws ArrayIndexOutOfBoundsException {
        System.out.println("\n" + board.getName() + ", place Your ships on the board (format: A1 n):\n");

        InputHelper.ShipInput inputResult = null;

        for (int i = 0; i < NUMBER_OF_DESTROYERS + NUMBER_OF_SUBMARINES + NUMBER_OF_BATTLESHIPS + NUMBER_OF_CARRIERS; i++) {
            try {
                if (i < NUMBER_OF_DESTROYERS) {
                    System.out.println("Enter your destroyers (size: " + ShipType.D.getSize() + ". " + Integer.toString(NUMBER_OF_DESTROYERS - i) + " more left):\n");
                    inputResult = InputHelper.readShipInput();
                    this.ships[i] = new Destroyer(inputResult.orientation);
                } else if (i < NUMBER_OF_DESTROYERS + NUMBER_OF_SUBMARINES) {
                    System.out.println("Enter your submarines (size: " + ShipType.S.getSize() + ". " + Integer.toString(NUMBER_OF_DESTROYERS + NUMBER_OF_SUBMARINES - i) + " more left):\n");
                    inputResult = InputHelper.readShipInput();
                    this.ships[i] = new Submarine(inputResult.orientation);
                } else if (i < NUMBER_OF_DESTROYERS + NUMBER_OF_SUBMARINES + NUMBER_OF_BATTLESHIPS) {
                    System.out.println("Enter your battleships (size: " + ShipType.B.getSize() + ". " +  Integer.toString(NUMBER_OF_DESTROYERS + NUMBER_OF_SUBMARINES + NUMBER_OF_BATTLESHIPS - i) + " more left):\n");
                    inputResult = InputHelper.readShipInput();
                    this.ships[i] = new BattleShip(inputResult.orientation);
                } else if (i < NUMBER_OF_DESTROYERS + NUMBER_OF_SUBMARINES + NUMBER_OF_BATTLESHIPS + NUMBER_OF_CARRIERS) {
                    System.out.println("Enter your carriers (size: " + ShipType.C.getSize() + ". " +  Integer.toString(NUMBER_OF_DESTROYERS + NUMBER_OF_SUBMARINES + NUMBER_OF_BATTLESHIPS + NUMBER_OF_CARRIERS - i) + " more left):\n");
                    inputResult = InputHelper.readShipInput();
                    this.ships[i] = new Carrier(inputResult.orientation);
                };

                if (inputResult.x < 0 || inputResult.x >= board.getSize() || inputResult.y < 0 || inputResult.y >= board.getSize())
                    throw new ArrayIndexOutOfBoundsException("Your ship is out of board. Please, repeat entering:\n");    
                
                switch (this.ships[i].getOrientation()) {
                    case N:
                        if (inputResult.y - this.ships[i].getSize() + 1 < 0) {
                            throw new ArrayIndexOutOfBoundsException("Your ship is out of board. Please, repeat entering:\n");
                        }
    
                        for (int j = inputResult.y; j > inputResult.y - this.ships[i].getSize(); j--) {
                            if (board.getShips()[inputResult.x][j] != null) {
                                throw new ArrayIndexOutOfBoundsException("Your ship is on collision course. Please, repeat entering:\n");
                            }
                            
                            if (board.isCloseToAnotherShip(inputResult.x, j)) {
                                throw new ArrayIndexOutOfBoundsException("Current ship is too close to another one. Please, repeat entering:\n");
                            }
    
                            if (j == inputResult.y - this.ships[i].getSize() + 1)
                                board.putShip(this.ships[i], inputResult.x, inputResult.y);
                        }
    
                        break;
                    case S:
                        if (inputResult.y + this.ships[i].getSize() > board.getSize()) {
                            throw new ArrayIndexOutOfBoundsException("Your ship is out of board. Please, repeat entering:\n");
                        }
    
                        for (int j = inputResult.y; j < inputResult.y + this.ships[i].getSize(); j++) {
                            if (board.getShips()[inputResult.x][j] != null) {
                                throw new ArrayIndexOutOfBoundsException("Your ship is on collision course. Please, repeat entering:\n");
                            }
    
                            if (board.isCloseToAnotherShip(inputResult.x, j)) {
                                throw new ArrayIndexOutOfBoundsException("Current ship is too close to another one. Please, repeat entering:\n");
                            }
    
                            if (j == inputResult.y + this.ships[i].getSize() - 1)
                                board.putShip(this.ships[i], inputResult.x, inputResult.y);
                        }
    
                        break;
                    case E:
                        if (inputResult.x + this.ships[i].getSize() > board.getSize()) {
                            throw new ArrayIndexOutOfBoundsException("Your ship is out of board. Please, repeat entering:\n");
                        }
    
                        for (int j = inputResult.x; j < inputResult.x + this.ships[i].getSize(); j++) {
                            if (board.getShips()[j][inputResult.y] != null) {
                                throw new ArrayIndexOutOfBoundsException("Your ship is on collision course. Please, repeat entering:\n");
                            } 
                            
                            if (board.isCloseToAnotherShip(j, inputResult.y)) {
                                throw new ArrayIndexOutOfBoundsException("Current ship is too close to another one. Please, repeat entering:\n");
                            }
    
                            if (j == inputResult.x + this.ships[i].getSize() - 1)
                                board.putShip(this.ships[i], inputResult.x, inputResult.y);
                        }
    
                        break;
                    case W:
                        if (inputResult.x - this.ships[i].getSize() + 1 < 0) {
                            throw new ArrayIndexOutOfBoundsException("Your ship is out of board. Please, repeat entering:\n");
                        }
                        
                        for (int j = inputResult.x; j > inputResult.x - this.ships[i].getSize(); j--) {
                            if (board.getShips()[j][inputResult.y] != null) {
                                throw new ArrayIndexOutOfBoundsException("Your ship is on collision course. Please, repeat entering:\n");
                            }
    
                            if (board.isCloseToAnotherShip(j, inputResult.y)) {
                                throw new ArrayIndexOutOfBoundsException("Current ship is too close to another one. Please, repeat entering:\n");
                            }
    
                            if (j == inputResult.x - this.ships[i].getSize() + 1)
                                board.putShip(this.ships[i], inputResult.x, inputResult.y);
                        }
    
                        break;
                    default:
                        break;
                }
    
                board.print(opponentBoard);   
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
                i--;
            }
        }
    }
    /**
     * send a hit to the enemy in the given coordinates
     * @param coords coordinates
     * @return hit made
     */
    public HitType sendHit(int[] coords) {
        HitType hit = null;

        hit = opponentBoard.sendHit(coords[0], coords[1]);

        return hit;
    }
    /**
     * returns the player's board
     * @return board
     */
    public Board getBoard() { return this.board; }
    /**
     * retunrs the opponent's board
     * @return board
     */
    public Board getOpponentBoard() { return this.opponentBoard; }
    /**
     * returns the amount of ships lost
     * @return Destroyed count
     */
    public int getDestroyedCount() { return this.destroyedCount; }
    /**
     * returns the ships of the player
     * @return list of ships
     */
    public AbstractShip[] getShips() {
        return ships;
    }
    /**
     * changes the board to the given board
     * @param newBoard board to change to
     */
    public void setBoard(Board newBoard) { this.board = newBoard; }
    /**
     * changes the opponent's board to the given board
     * @param newOpponentBoard board to change to
     */
    public void setOpponentBoard(Board newOpponentBoard) { this.opponentBoard = newOpponentBoard; }
    /**
     * changes the destroyed count to the given value
     * @param newDestroyedCount value to change to
     */
    public void setDestroyedCount(int newDestroyedCount) { this.destroyedCount = newDestroyedCount; }
    /**
     * changes the ships list to the given list
     * @param ships list to change to
     */
    public void setShips(AbstractShip[] ships) {
        this.ships = ships;
    }
}
