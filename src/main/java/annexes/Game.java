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

    public Game init(boolean load) throws ArrayIndexOutOfBoundsException {
        if (!load) {
            // init attributes

            String playerName1 = "";
            String playerName2 = "AI";
            boolean done = false;

            if (isMultiplayer) {
                System.out.println("Enter the First Player's name (maximum - 30 symbols):");

                do {
                    try {
                        playerName1 = sin.nextLine();

                        if (playerName1.length() <= 0)
                            throw new ArrayIndexOutOfBoundsException("First player's name can't be left in blank. Please, repeat entering:\n");
                        else if (playerName1.length() > 30)
                            throw new ArrayIndexOutOfBoundsException("First player's name is too long (maximum - 30 characters). Please, repeat entering:\n");
                    
                        done = true;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println(e.getMessage());
                    }
                } while (!done);

                done = false;

                System.out.println("Enter the Second Player's name:");

                do {
                    try {
                        playerName2 = sin.nextLine();

                        if (playerName2.length() <= 0)
                            throw new ArrayIndexOutOfBoundsException("Second player's name can't be left in blank. Please, repeat entering:\n");
                        else if (playerName2.length() > 30)
                            throw new ArrayIndexOutOfBoundsException("Second player's name is too long (maximum - 30 characters). Please, repeat entering:\n");
                    
                        done = true;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println(e.getMessage());
                    }
                } while (!done);
            } else {
                System.out.println("Enter your name:");

                do {
                    try {
                        playerName1 = sin.nextLine();

                        if (playerName1.length() <= 0)
                            throw new ArrayIndexOutOfBoundsException("Your name can't be left in blank. Please, repeat entering:\n");
                        else if (playerName1.length() > 30)
                            throw new ArrayIndexOutOfBoundsException("Your name is too long (maximum - 30 characters). Please, repeat entering:\n");
                    
                        done = true;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println(e.getMessage());
                    }
                } while (!done);
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

    public Boolean getIsMultiplayer() { return this.isMultiplayer; };

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
                InputHelper.CoordInput hitInput = InputHelper.readCoordInput(player1.getBoard().getSize());
                
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

            System.out.println("You have reached checkpoint, game is saved.");
            save();

            if (!done && !strike) {
                do {
                    if (isMultiplayer) {
                        player2.getBoard().print(player1.getBoard());
                        doneInput = false;

                        do {
                            System.out.println(player2.getBoard().getName() + ", make your shot:\n");
                            InputHelper.CoordInput hitInput = InputHelper.readCoordInput(player2.getBoard().getSize());
                            
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
                        System.out.println("You have reached checkpoint, game is saved.");
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

    private static List<AbstractShip> createDefaultShips() {
        return Arrays.asList(new AbstractShip[] { new Destroyer(), new Submarine(), new Submarine(), new BattleShip(),
                new Carrier() });
    }
}
