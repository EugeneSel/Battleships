package annexes;

import ships.*;
import board.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
    public Game(boolean isMultiplayer) {
        this.isMultiplayer = isMultiplayer;
        sin = new Scanner(System.in);
    }

    public Game init() {
        if (!loadSave()) {
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
        }
        return this;
    }

    /*
     * *** Méthodes
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
            boolean strike = hit != HitType.MISS; // TODO set this hit on his board (player1.getBoard())

            done = updateScore();
            System.out.println(makeHitMessage(player1, false /* outgoing hit */, coords, hit));

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
                    hit = player2.sendHit(coords); // TODO player2 send a hit.
                    if (isMultiplayer)
                        player2.getBoard().setHit(hit, coords[0], coords[1]);

                    strike = hit != HitType.MISS;
                    if (strike) {
                        player1.getBoard().print(player2.getBoard());
                    }
                    System.out.println(makeHitMessage(player2, true /* incoming hit */, coords, hit));
                    done = updateScore();

                    if (!done) {
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
        // try {
        //     // TODO bonus 2 : uncomment
        //     // if (!SAVE_FILE.exists()) {
        //     // SAVE_FILE.getAbsoluteFile().getParentFile().mkdirs();
        //     // }

        //     // TODO bonus 2 : serialize players

        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }

    private boolean loadSave() {
        if (SAVE_FILE.exists()) {
            // try {
            //     // TODO bonus 2 : deserialize players

            //     return true;
            // } catch (IOException | ClassNotFoundException e) {
            //     e.printStackTrace();
            // }
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
