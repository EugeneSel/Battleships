package annexes;

import ships.*;
import board.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * Class that control the game and runs it
 */
public class Game {
    /*
     * *** Constante
     */
    public static final File SAVE_FILE = new File("savegame.dat");

    /*
     * *** Attributs
     */
    private Player player1;
    private Player player2;
    private Scanner sin;
    private Boolean isMultiplayer;

    /*
     * *** Constructeurs
     */
    public Game() {
        sin = new Scanner(System.in);
    }

    public Game(boolean isMultiplayer) {
        this.isMultiplayer = isMultiplayer;
        sin = new Scanner(System.in);
    }
    /**
     * initialises the game
     * @param load if true runs the saved file
     * @return null if load is true but there is no file, otherwise the initialization
     */
    public Game init(boolean load) {
        if (!load) {
            // init attributes

            String playerName1 = "";
            String playerName2 = "AI";

            if (isMultiplayer) {
                System.out.println("Write down First Player's name:");

                try {
                    playerName1 = sin.nextLine();
                } catch (Exception e) {
                    // nop
                }

                System.out.println("Write down Second Player's name:");

                try {
                    playerName2 = sin.nextLine();
                } catch (Exception e) {
                    // nop
                }
            } else {
                System.out.println("Write down your name:");

                try {
                    playerName1 = sin.nextLine();
                } catch (Exception e) {
                    // nop
                }
            }
            
            Board boardPlayer1 = new Board(playerName1);
            Board boardPlayer2 = new Board(playerName2);

            player1 = new Player(boardPlayer1, boardPlayer2, createDefaultShips());
            if (isMultiplayer)
                player2 = new Player(boardPlayer2, boardPlayer1, createDefaultShips());
            else 
                player2 = new AIPlayer(boardPlayer2, boardPlayer1, createDefaultShips());

            // place player ships:
            boardPlayer1.print(boardPlayer2);
            player1.putShips();
            if (isMultiplayer)
                boardPlayer2.print(boardPlayer1);
            player2.putShips();

            System.out.println("You have reached checkpoint, game is saved.");
            save();
        } else if (load && !loadSave()) {
            System.out.println("There is no any saved game, sorry\n");
            // init attributes

            return null;
        }
        return this;
    }

    /*
     * *** Méthodes
     */
    /**
     * returns the value of multiplayer
     * @return true if multiplayer is selected, false otherwise
     */
    public Boolean getIsMultiplayer() { return this.isMultiplayer; };
    /**
     * Runs the game
     */
    public void run() {
        int[] coords = new int[2];
        HitType hit;

        // main loop
        player1.getBoard().print(player2.getBoard());
        boolean done;

        do {
            boolean doneInput = false;

            do {
                System.out.println(player1.getBoard().getName() + ", make your shot:\n");
                InputHelper.CoordInput hitInput = InputHelper.readCoordInput();
                
                if (player1.getBoard().getHits()[hitInput.x][hitInput.y] != HitType.NONE) {
                    System.out.println("You have already shot there. Please, repeat entering\n");
                    continue;
                }
    
                coords = new int[2];
                coords[0] = hitInput.x;
                coords[1] = hitInput.y;
                doneInput = true;  
            } while (!doneInput);

            hit = player1.sendHit(coords);
            player1.getBoard().setHit(hit, coords[0], coords[1]);
            boolean strike = hit != HitType.MISS;

            done = updateScore();
            System.out.println(makeHitMessage(player1, false /* outgoing hit */, coords, hit));

            System.out.println("You have reached a checkpoint, game is saved.");
            save();

            if (!done && !strike) {
                do {
                    if (isMultiplayer) {
                        player2.getBoard().print(player1.getBoard());
                        doneInput = false;

                        do {
                            System.out.println(player2.getBoard().getName() + ", make your shot:\n");
                            InputHelper.CoordInput hitInput = InputHelper.readCoordInput();
                            
                            if (player2.getBoard().getHits()[hitInput.x][hitInput.y] != HitType.NONE) {
                                System.out.println("You have already shot there. Please, repeat entering\n");
                                continue;
                            }
                
                            coords = new int[2];
                            coords[0] = hitInput.x;
                            coords[1] = hitInput.y;
                            doneInput = true;  
                        } while (!doneInput);
                    }
                    hit = player2.sendHit(coords);
                    if (isMultiplayer)
                        player2.getBoard().setHit(hit, coords[0], coords[1]);

                    strike = hit != HitType.MISS;
                    if (strike) {
                        player1.getBoard().print(player2.getBoard());
                    }
                    System.out.println(makeHitMessage(player2, true /* incoming hit */, coords, hit));
                    done = updateScore();

                    if (!done) {
                        System.out.println("You have reached a checkpoint, game is saved.");
                        save();
                    }
                } while (strike && !done);
            }

            player1.getBoard().print(player2.getBoard());

        } while (!done);

        SAVE_FILE.delete();
        System.out.println(String.format("%s wins", player1.lose ? player2.getBoard().getName() : player1.getBoard().getName()));
        sin.close();
    }
    /**
     * save the game, by serializing all the information and putting it in the "savegame.dat"
     */
    private void save() {
        try {
            if (!SAVE_FILE.exists()) {
                SAVE_FILE.getAbsoluteFile().getParentFile().mkdirs();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(SAVE_FILE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            
            objectOutputStream.writeObject(player1);
            objectOutputStream.writeObject(player2);
            objectOutputStream.writeObject(isMultiplayer);

            objectOutputStream.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * loads the saved game
     * @return true if the game has been loaded succesfully, false otherwise
     */
    private boolean loadSave() {
        if (SAVE_FILE.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(SAVE_FILE);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            
                player1 = (Player) objectInputStream.readObject();
                player2 = (Player) objectInputStream.readObject();
                isMultiplayer = (Boolean) objectInputStream.readObject();
                
                objectInputStream.close();
                fileInputStream.close();

                return true;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * checks if a player has lost
     * @return true if the amount of ships lost is the same as the total amount of ships, false otherwise
     */
    private boolean updateScore() {
        for (Player player : new Player[] { player1, player2 }) {
            int destroyed = 0;
            for (AbstractShip ship : player.getShips()) {
                if (ship.isSunk()) {
                    destroyed++;
                }
            }

            player.destroyedCount = destroyed;
            player.lose = destroyed == player.getShips().length;
            if (player.lose) {
                return true;
            }
        }
        return false;
    }
    /**
     * returns a constructed message with the information of a hit
     * @param player player concerned
     * @param incoming direction of the hit
     * @param coords coordinates of the hit
     * @param hit hit type
     * @return message constructed
     */
    private String makeHitMessage(Player player, boolean incoming, int[] coords, HitType hit) {
        String msg = "";
        ColorUtil.Color color = ColorUtil.Color.RESET;
        switch (hit) {
        case MISS:
            msg = hit.toString();
            break;
        case HIT:
            msg = hit.toString();
            color = ColorUtil.Color.RED;
            break;
        case KILL:
            msg = player.getOpponentBoard().getShips()[coords[0]][coords[1]].getCurrentShip().getName() + " is sunk";
            color = ColorUtil.Color.RED;
            break;
        default:
            break;
        }
        msg = String.format("%s Frappe en %c%d : %s", incoming ? "<=" : "=>", ((char) ('A' + coords[0])),
                (coords[1] + 1), msg);
        return ColorUtil.colorize(msg, color);
    }
    /**
     * default list of ships
     * @return array of ships
     */
    private static List<AbstractShip> createDefaultShips() {
        return Arrays.asList(new AbstractShip[] { new Destroyer(), new Submarine(), new Submarine(), new BattleShip(),
                new Carrier() });
    }
}
