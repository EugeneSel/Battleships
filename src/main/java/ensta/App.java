package ensta;

import annexes.*;
import java.util.Scanner;

public class App {
    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Choose the game mode:");
        System.out.println("s/S - Singleplayer");
        System.out.println("m/M - Multiplayer");

        Scanner sin = new Scanner(System.in);
        char gameMode;
        Game newGame = null;
        boolean done = false;
        
        do {
            try {
                gameMode = sin.next(".").charAt(0);
                if (gameMode == 's' || gameMode == 'S') {
                    newGame = new Game(false);
                    done = true;
                } else if (gameMode == 'm' || gameMode == 'M') {
                    newGame = new Game(true);
                    done = true;
                } else {
                    throw new Exception("Wrong choice. Please, repeat entering");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                // done = false;
            }
        } while (!done);

        newGame.init();
        newGame.run();
    }
}
