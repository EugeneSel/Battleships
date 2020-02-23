package board;
import ships.*;

import java.io.Serializable;

public class Board implements IBoard, Serializable {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final int DEFAULT_BOARD_SIZE = 10;
    protected String name;
    protected int size;
    protected ShipState[][] ships;
    protected HitType[][] hits;
    
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

    public void setShips(ShipState[][] ships){
        this.ships = ships;
    }

    public void setHits(HitType[][] hits){
        this.hits = hits;
    }

    public ShipState[][] getShips(){
        return this.ships;
    }

    public HitType[][] getHits(){
        return this.hits;
    }

    public String getName() {
        return this.name;
    }

    public int getSize(){
        return this.size;
    }

    public boolean hasShip(int x, int y){
        return (ships[x][y] != null);
    }

    public void setHit(HitType hit, int x, int y){
        hits[x][y] = hit;
    }

    public HitType getHit(int x, int y){
        return (hits[x][y]);
    }

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