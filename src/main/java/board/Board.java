package board;
import ships.*;

import java.io.Serializable;

/**
 * Board is the entity used by players to save
 * their game in a table form.
 * There are two tables being handled, ships and hits.
 */
public class Board implements IBoard, Serializable {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final int DEFAULT_BOARD_SIZE = 10;
    protected String name;
    protected int size;
    protected ShipState[][] ships;
    protected HitType[][] hits;
    /**
     * Constructor of Board which receives the name of the player and the size of the tables to play
     * @param name of the player
     * @param size of the tables to play in
     */
    public Board(String name, int size) {
        this.name = name;
        this.size = size;
        ships = new ShipState[size][size];
        hits = new HitType[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                hits[i][j] = HitType.NONE;
            }
        }
    }
    /**
     * Constructor of Board which receives the name of the player, and defaults the size of the tables to 10
     * @param name of the player
     */
    public Board(String name) {
        this.name = name;
        this.size = DEFAULT_BOARD_SIZE;
        ships = new ShipState[size][size];
        hits = new HitType[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                hits[i][j] = HitType.NONE;
            }
        }
    }
    /**
     * Changes the saved ships table to the board sended
     * @param ships to send
     */
    public void setShips(ShipState[][] ships){
        this.ships = ships;
    }
    /**
     * Changes the saved hits table to the board sended
     * @param hits to send
     */
    public void setHits(HitType[][] hits){
        this.hits = hits;
    }
    /**
     * Returns the table of Ships
     * @return table of Ships
     */
    public ShipState[][] getShips(){
        return this.ships;
    }
    /**
     * Returns the table of Hits
     * @return table of Hits
     */
    public HitType[][] getHits(){
        return this.hits;
    }
    /**
     * Return the name of the player
     * @return player name
     */
    public String getName() {
        return this.name;
    }
    /**
     * Returns the size of the tables
     * @return size of Tables
     */
    public int getSize() {
        return this.size;
    }
    /**
     * Checks the specified coordinates in the Ships Table for a Ship
     * @param x coordinate
     * @param y coordinate
     * @return true if there is a ship in the coordinates otherwise returns false.
     */
    public boolean hasShip(int x, int y){
        return (ships[x][y] != null);
    }
    /**
     * Set in the Hits Table in the coordinates sended the hit type sended
     * @param hit to send
     * @param x coordinate
     * @param y coordinate
     */
    public void setHit(HitType hit, int x, int y){
        hits[x][y] = hit;
    }
    /**
     * Returns the Hit type saved in the Hits table in the specified coordinates
     * @param x coordinate
     * @param y coordinate
     * @return the Hit Type in the coordinates
     */
    public HitType getHit(int x, int y){
        return (hits[x][y]);
    }
    /**
     * Checks the Ships Table in the specified coordinates to return the correct hit type
     * @param x coordinate
     * @param y coordinate
     * @return Miss if the is no ship, Hit if there is a ship and Kill if there is a ship that has been sunked
     */
    public HitType sendHit(int x, int y) {
        if (ships[x][y] == null) 
            return HitType.MISS;
        else {
            ships[x][y].addStrike();
            if (ships[x][y].isSunk())
                return HitType.KILL;
            else
                return HitType.HIT;
        }
    }
    /**
     * Saves the sended Ship in the Ships Table in the specified coordinates, constructing the ship depending on its orientation and its size
     * @param ship to save
     * @param x coordinate
     * @param y coordinate
     */
    public void putShip(AbstractShip ship, int x, int y){
        int size = ship.getSize();
        while (size > 0) {
            ships[x][y] = new ShipState(ship);
            switch (ship.getOrientation()) {
                case N:
                    y--;
                    break;

                case S:
                    y++;
                    break;

                case E:
                    x++; 
                    break;

                case W:
                    x--;
                    break; 
                           
                default:
                    break;
            }
            size--;
        }       
    }
    /**
     * Prints the Tables Ships and Hits of the Board sended
     * @param opponentBoard Board to Print
     */
    public void print(Board opponentBoard) {
        System.out.println(name);
        System.out.println();
        System.out.print("SHIPS :");
        for (int i = 0; i < size * 2 - 7; i++) {
            System.out.print(" "); 
        }
        System.out.print('\t');
        System.out.print('\t');
        System.out.print("HITS :");
        System.out.println();
        String spacing;
        spacing = (size > 9) ? "   " : "  ";
        for (int i = 0; i < 2; i++) {
            char c = 'A';
            System.out.print(spacing);
            for (int j = 0; j < size; j++) {
                System.out.print(c);
                System.out.print(" ");
                c++;
            }
            System.out.print('\t');
            System.out.print('\t');
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            for (int k = 0; k < 2; k++) {
                if (size > 9) {
                    spacing = (i + 1 > 9) ? " " : "  ";
                } else {
                    spacing = " ";
                }
                System.out.print(i + 1);
                System.out.print(spacing);
                for (int j = 0; j < size; j++) {
                    if(k == 0) {
                        if (ships[j][i] == null && opponentBoard.getHits()[j][i] == HitType.NONE) {
                            System.out.print('.');
                        } else if (opponentBoard.getHits()[j][i] == HitType.MISS) {
                            System.out.print('o');
                        } else {
                            System.out.print(ships[j][i].toString());
                        }
                    } else {
                        if (hits[j][i] == HitType.NONE) {
                            System.out.print('.');
                        } else if (hits[j][i] == HitType.MISS){
                            System.out.print('X');
                        } else {
                            System.out.print(ANSI_RED + 'X' + ANSI_RESET);
                        }
                    }
                    System.out.print(' '); 
                }
                System.out.print('\t');
                System.out.print('\t');       
            }
            System.out.println();
        }
    }
    /**
     * Checks the neighbouring tiles for another ship, to ensure the rule of no touching between ships
     * @param x coordinate
     * @param y coordinate
     * @return true if there is another ship in the neighbouring tiles, false otherwise
     */
    public boolean isCloseToAnotherShip(int x, int y) {
        if ((x - 1 >= 0 && this.getShips()[x - 1][y] != null) ||
            (y - 1 >= 0 && this.getShips()[x][y - 1] != null) ||
            (x + 1 < this.getSize() && this.getShips()[x + 1][y] != null) ||
            (y + 1 < this.getSize() && this.getShips()[x][y + 1] != null) || 
            (x - 1 >= 0 && y - 1 >= 0 && this.getShips()[x - 1][y - 1] != null) ||
            (x + 1 < this.getSize() && y - 1 >= 0 && this.getShips()[x + 1][y - 1] != null) ||
            (x - 1 >= 0 && y + 1 < this.getSize() && this.getShips()[x - 1][y + 1] != null) ||
            (x + 1 < this.getSize() && y + 1 < this.getSize() && this.getShips()[x + 1][y + 1] != null))
            return true;
        return false;
    } 
}