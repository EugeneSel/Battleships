package annexes;

import ships.*;
import java.io.Serializable;
import java.util.List;
import board.*;

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

    public HitType sendHit(int[] coords) {
        HitType hit = null;

        hit = opponentBoard.sendHit(coords[0], coords[1]);

        return hit;
    }

    public Board getBoard() { return this.board; }
    public Board getOpponentBoard() { return this.opponentBoard; }
    public int getDestroyedCount() { return this.destroyedCount; }
    public AbstractShip[] getShips() {
        return ships;
    }

    public void setBoard(Board newBoard) { this.board = newBoard; }
    public void setOpponentBoard(Board newOpponentBoard) { this.opponentBoard = newOpponentBoard; }
    public void setDestroyedCount(int newDestroyedCount) { this.destroyedCount = newDestroyedCount; }
    public void setShips(AbstractShip[] ships) {
        this.ships = ships;
    }
}
