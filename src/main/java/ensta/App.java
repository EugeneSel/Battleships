package ensta;

import annexes.*;
import java.util.Scanner;
/**
 * Manages the game, it contains the main and runs Battleships
 */
public class App {
    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Choose the game mode or load a save:");
        System.out.println("s/S - Singleplayer");
        System.out.println("m/M - Multiplayer");
        System.out.println("l/L - Load game");

        Scanner sin = new Scanner(System.in);
        char gameMode;
        Game newGame = null;
        boolean done = false;
        
        do {
            try {
                gameMode = sin.nextLine().charAt(0);
                if (gameMode == 's' || gameMode == 'S') {
                    newGame = new Game(false);
                    newGame.init(false);
                    done = true;
                } else if (gameMode == 'm' || gameMode == 'M') {
                    newGame = new Game(true);
                    newGame.init(false);
                    done = true;
                } else if (gameMode == 'l' || gameMode == 'L') {
                    newGame = new Game();
                    if (newGame.init(true) == null) {
                        done = false;
                        System.out.println("Please, repeat entering:");
                        System.out.println("s/S - Singleplayer");
                        System.out.println("m/M - Multiplayer");
                        System.out.println("l/L - Load game");
                    } else
                        done = true;
                } else {
                    throw new Exception("Wrong choice. Please, repeat entering");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (!done);

        newGame.run();
    }
}
