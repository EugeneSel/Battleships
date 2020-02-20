package ensta;

import java.util.List;

import annexes.*;
import ships.*;
import board.*;
import annexes.InputHelper.CoordInput;
import annexes.InputHelper.ShipInput;

public class App {
    public static final int NUMBER_OF_DESTROYERS = 1;
    public static final int NUMBER_OF_SUBMARINES = 2;
    public static final int NUMBER_OF_BATTLESHIPS = 1;
    public static final int NUMBER_OF_CARRIERS = 1;

    public static boolean isCloseToAnotherShip(Board board, int x, int y) {
        if ((x - 1 >= 0 && board.GetShips()[x - 1][y] != null) ||
            (y - 1 >= 0 && board.GetShips()[x][y - 1] != null) ||
            (x + 1 < board.getSize() && board.GetShips()[x + 1][y] != null) ||
            (y + 1 < board.getSize() && board.GetShips()[x][y + 1] != null) || 
            (x - 1 >= 0 && y - 1 >= 0 && board.GetShips()[x - 1][y - 1] != null) ||
            (x + 1 < board.getSize() && y - 1 >= 0 && board.GetShips()[x + 1][y - 1] != null) ||
            (x - 1 >= 0 && y + 1 < board.getSize() && board.GetShips()[x - 1][y + 1] != null) ||
            (x + 1 < board.getSize() && y + 1 < board.getSize() && board.GetShips()[x + 1][y + 1] != null))
            return true;
        return false;
    } 

    public static void createShips(Board board) {
        System.out.println("\nPlace Your ships on the board (format: A1 n):\n");

        ShipInput inputResult = null;

        AbstractShip enteredShip = null;

        for (int i = 0; i < NUMBER_OF_DESTROYERS + NUMBER_OF_SUBMARINES + NUMBER_OF_BATTLESHIPS + NUMBER_OF_CARRIERS; i++) {
            if (i < NUMBER_OF_DESTROYERS) {
                System.out.println("Enter your destroyers (size: " + ShipType.D.getSize() + ". " + Integer.toString(NUMBER_OF_DESTROYERS - i) + " more left):\n");
                inputResult = InputHelper.readShipInput();
                enteredShip = new Destroyer(inputResult.orientation);
            } else if (i < NUMBER_OF_DESTROYERS + NUMBER_OF_SUBMARINES) {
                System.out.println("Enter your submarines (size: " + ShipType.S.getSize() + ". " + Integer.toString(NUMBER_OF_DESTROYERS + NUMBER_OF_SUBMARINES - i) + " more left):\n");
                inputResult = InputHelper.readShipInput();
                enteredShip = new Submarine(inputResult.orientation);
            } else if (i < NUMBER_OF_DESTROYERS + NUMBER_OF_SUBMARINES + NUMBER_OF_BATTLESHIPS) {
                System.out.println("Enter your battleships (size: " + ShipType.B.getSize() + ". " +  Integer.toString(NUMBER_OF_DESTROYERS + NUMBER_OF_SUBMARINES + NUMBER_OF_BATTLESHIPS - i) + " more left):\n");
                inputResult = InputHelper.readShipInput();
                enteredShip = new BattleShip(inputResult.orientation);
            } else if (i < NUMBER_OF_DESTROYERS + NUMBER_OF_SUBMARINES + NUMBER_OF_BATTLESHIPS + NUMBER_OF_CARRIERS) {
                System.out.println("Enter your carriers (size: " + ShipType.C.getSize() + ". " +  Integer.toString(NUMBER_OF_DESTROYERS + NUMBER_OF_SUBMARINES + NUMBER_OF_BATTLESHIPS + NUMBER_OF_CARRIERS - i) + " more left):\n");
                inputResult = InputHelper.readShipInput();
                enteredShip = new Carrier(inputResult.orientation);
            };
            
            switch (enteredShip.getOrientation()) {
                case N:
                    if (inputResult.y - enteredShip.getSize() + 1 < 0) {
                        System.out.println("Your ship is out of board. Please, repeat entering:\n");
                        i--;
                        break;
                    }

                    for (int j = inputResult.y; j > inputResult.y - enteredShip.getSize(); j--) {
                        if (board.GetShips()[inputResult.x][j] != null) {
                            System.out.println("Your ship is on collision course. Please, repeat entering:\n");
                            i--;
                            break;
                        }
                        
                        if (isCloseToAnotherShip(board, inputResult.x, j)) {
                            System.out.println("Current ship is too close to another one. Please, repeat entering:\n");
                            i--;
                            break;
                        }

                        if (j == inputResult.y - enteredShip.getSize() + 1)
                            board.putShip(enteredShip, inputResult.x, inputResult.y);
                    }

                    break;
                case S:
                    if (inputResult.y + enteredShip.getSize() > board.getSize()) {
                        System.out.println("Your ship is out of board. Please, repeat entering:\n");
                        i--;
                        break;
                    }

                    for (int j = inputResult.y; j < inputResult.y + enteredShip.getSize(); j++) {
                        if (board.GetShips()[inputResult.x][j] != null) {
                            System.out.println("Your ship is on collision course. Please, repeat entering:\n");
                            i--;
                            break;
                        }

                        if (isCloseToAnotherShip(board, inputResult.x, j)) {
                            System.out.println("Current ship is too close to another one. Please, repeat entering:\n");
                            i--;
                            break;
                        }

                        if (j == inputResult.y + enteredShip.getSize() - 1)
                            board.putShip(enteredShip, inputResult.x, inputResult.y);
                    }

                    break;
                case E:
                    if (inputResult.x + enteredShip.getSize() > board.getSize()) {
                        System.out.println("Your ship is out of board. Please, repeat entering:\n");
                        i--;
                        break;
                    }

                    for (int j = inputResult.x; j < inputResult.x + enteredShip.getSize(); j++) {
                        if (board.GetShips()[j][inputResult.y] != null) {
                            System.out.println("Your ship is on collision course. Please, repeat entering:\n");
                            i--;
                            break;
                        } 
                        
                        if (isCloseToAnotherShip(board, j, inputResult.y)) {
                            System.out.println("Current ship is too close to another one. Please, repeat entering:\n");
                            i--;
                            break;
                        }

                        if (j == inputResult.x + enteredShip.getSize() - 1)
                            board.putShip(enteredShip, inputResult.x, inputResult.y);
                    }

                    break;
                case W:
                    if (inputResult.x - enteredShip.getSize() + 1 < 0) {
                        System.out.println("Your ship is out of board. Please, repeat entering:\n");
                        i--;
                        break;
                    }
                    
                    for (int j = inputResult.x; j > inputResult.x - enteredShip.getSize(); j--) {
                        if (board.GetShips()[j][inputResult.y] != null) {
                            System.out.println("Your ship is on collision course. Please, repeat entering:\n");
                            i--;
                            break;
                        }

                        if (isCloseToAnotherShip(board, j, inputResult.y)) {
                            System.out.println("Current ship is too close to another one. Please, repeat entering:\n");
                            i--;
                            break;
                        }

                        if (j == inputResult.x - enteredShip.getSize() + 1)
                            board.putShip(enteredShip, inputResult.x, inputResult.y);
                    }

                    break;
                default:
                    break;
            }

            board.print();
        }


    };

    public static void main(String[] args)
    {
        Board testboard = new Board("Ric");
        // testboard.putShip(new Submarine (ShipOrientation.S), 0, 0);
        // testboard.setHit(HitType.HIT, 0, 0);
        testboard.print();

        createShips(testboard);

        CoordInput inputResult;
        HitType newHit;
        while (true) {
            inputResult = InputHelper.readCoordInput();
            newHit = testboard.sendHit(inputResult.x, inputResult.y);
            testboard.setHit(newHit, inputResult.x, inputResult.y);
            testboard.print();
            if (newHit == HitType.KILL) {
                System.out.println(testboard.GetShips()[inputResult.x][inputResult.y].getCurrentShip().getName() + " is Sunk\n");
            }
        }
    }
}
