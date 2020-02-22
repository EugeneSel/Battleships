package ensta;

import annexes.*;

public class App {
    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Game newGame = new Game();
        newGame.init();
        newGame.run();
    }
}
