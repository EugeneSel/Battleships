package board;
import ships.*;

import java.lang.instrument.IllegalClassFormatException;

public class Board implements IBoard {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final int DEFAULT_BOARD_SIZE = 10;
    protected String name;
    protected int size;
    protected ShipType[][] ships;
    protected HitType[][] hits; 
    
    public Board(String name, int size)
    {
        this.name = name;
        this.size = size;
        ships = new ShipType[size][size];
        hits = new HitType[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                hits[i][j] = HitType.NONE;
            }
        }
    }
    
    public Board(String name)
    {
        this.name = name;
        this.size = DEFAULT_BOARD_SIZE;
        ships = new ShipType[size][size];
        hits = new HitType[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                hits[i][j] = HitType.NONE;
            }
        }
    }

    public void SetShips(ShipType[][] ships){
        this.ships = ships;
    }

    public void SetHits(HitType[][] hits){
        this.hits = hits;
    }

    public ShipType[][] GetShips(){
        return this.ships;
    }

    public HitType[][] GetHits(){
        return this.hits;
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

    public void putShip(AbstractShip ship, int x, int y){
        int size = ship.getSize();
        while (size > 0) {
            ships[x][y] = ship.getType();
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

    
    public void print(){
        //System.out.println(name);
        System.out.println();
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
        spacing = (size > DEFAULT_BOARD_SIZE - 1) ? "   " : "  ";
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
                if (size > DEFAULT_BOARD_SIZE - 1) {
                    spacing = (i+1 > DEFAULT_BOARD_SIZE - 1) ? " " : "  ";
                } else {
                    spacing = " ";
                }
                System.out.print(i + 1);
                System.out.print(spacing);
                for (int j = 0; j < size; j++) {
                    if(k == 0){
                        if (ships[j][i] == null) {
                            System.out.print('.');
                        } else {
                            System.out.print(ships[j][i]);
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
}