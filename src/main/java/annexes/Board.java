package annexes;

import java.lang.instrument.IllegalClassFormatException;

public class Board {
    protected String name;
    protected int size;
    protected char[][] ships;
    protected HitType[][] hits; 

    public Board(String name, int size)
    {
        this.name = name;
        this.size = size;
        ships = new char[size][size];
        hits = new HitType[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                hits[i][j] = HitType.NONE;
            }
        }
    }

    public void SetShips(char[][] ships){
        this.ships = ships;
    }

    public void SetHits(HitType[][] hits){
        this.hits = hits;
    }

    public char[][] GetShips(){
        return this.ships;
    }

    public HitType[][] GetHits(){
        return this.hits;
    }

    public int GetSize(){
        return this.size;
    }

    public Board(String name)
    {
        this.name = name;
        this.size = 10;
        ships = new char[size][size];
        hits = new HitType[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                hits[i][j] = HitType.NONE;
            }
        }
    }
    
    public void print(){
        //System.out.println(name);
        System.out.print("SHIPS :");
        for (int i = 0; i < size*2 - 7; i++) {
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
                    spacing = (i+1 > 9) ? " " : "  ";
                } else {
                    spacing = " ";
                }
                System.out.print(i+1);
                System.out.print(spacing);
                for (int j = 0; j < size; j++) {
                    if(k == 0){
                        if (ships[i][j] == '\u0000') {
                            System.out.print('.');
                        } else {
                            System.out.print(ships[i][j]);
                        }
                    } else {
                        if (hits[i][j] == HitType.NONE) {
                            System.out.print('.');
                        } else if (hits[i][j] == HitType.NONE){
                            System.out.print('O');
                        } else {
                            System.out.print('X');
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