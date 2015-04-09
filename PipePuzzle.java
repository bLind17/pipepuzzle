/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pipepuzzle;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author bLind
 */
public class PipePuzzle {
    
    public static final int DIR_TOP = 0;
    public static final int DIR_LEFT = 1;
    public static final int DIR_BOTTOM = 2;
    public static final int DIR_RIGHT = 3;
    public static final int CON_LEFT = 0;
    public static final int CON_CENTER = 1;
    public static final int CON_RIGHT = 2;
    public static int RED = Pipe.Color.Red.ordinal();
    public static int YELLOW = Pipe.Color.Yellow.ordinal();
    public static int BLUE = Pipe.Color.Blue.ordinal();
    public static int GREEN = Pipe.Color.Green.ordinal();
    public static int BLACK = Pipe.Color.Black.ordinal();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle(9, 3);
        
        Card card1 = new Card(puzzle, "1");
        
        PipeConnector connectorTop = new PipeConnector(card1, DIR_TOP);
        PipeConnector connectorLeft = new PipeConnector(card1, DIR_LEFT);
        PipeConnector connectorBottom = new PipeConnector(card1, DIR_BOTTOM);
        PipeConnector connectorRight = new PipeConnector(card1, DIR_RIGHT);
        
        Pipe pipe1 = new Pipe(connectorLeft, CON_RIGHT, connectorRight, CON_LEFT, Pipe.Color.Green);
        Pipe pipe2 = new Pipe(connectorLeft, CON_CENTER, connectorRight, CON_CENTER, Pipe.Color.Yellow);
        Pipe pipe3 = new Pipe(connectorTop, CON_CENTER, connectorBottom, CON_CENTER, Pipe.Color.Blue);
        Pipe pipe4 = new Pipe(connectorBottom, CON_LEFT, connectorRight, CON_RIGHT, Pipe.Color.Red);
        
        card1.addPipeConnector(connectorTop);
        card1.addPipeConnector(connectorLeft);
        card1.addPipeConnector(connectorBottom);
        card1.addPipeConnector(connectorRight);
        
        Card card2 = getCard("2", puzzle,
                DIR_LEFT, CON_LEFT, DIR_BOTTOM, CON_RIGHT, GREEN,
                DIR_LEFT, CON_CENTER, DIR_RIGHT, CON_CENTER, YELLOW,
                DIR_TOP, CON_CENTER, DIR_BOTTOM, CON_CENTER, BLUE,
                DIR_TOP, CON_RIGHT, DIR_RIGHT, CON_LEFT, RED);
        Card card3 = getCard("3", puzzle,
                DIR_LEFT, CON_LEFT, DIR_RIGHT, CON_RIGHT, BLACK,
                DIR_LEFT, CON_CENTER, DIR_RIGHT, CON_CENTER, YELLOW,
                DIR_LEFT, CON_RIGHT, DIR_RIGHT, CON_LEFT, GREEN,
                DIR_TOP, CON_CENTER, DIR_BOTTOM, CON_CENTER, BLUE);
        Card card4 = getCard("4", puzzle,
                DIR_LEFT, CON_LEFT, DIR_RIGHT, CON_RIGHT, BLUE,
                DIR_TOP, CON_CENTER, DIR_RIGHT, CON_LEFT, BLACK,
                DIR_TOP, CON_RIGHT, DIR_BOTTOM, CON_LEFT, GREEN,
                DIR_BOTTOM, CON_CENTER, DIR_RIGHT, CON_CENTER, YELLOW);
        
        Card card5 = getCard("5", puzzle,
                DIR_LEFT, CON_LEFT, DIR_RIGHT, CON_RIGHT, RED,
                DIR_TOP, CON_CENTER, DIR_RIGHT, CON_LEFT, BLUE,
                DIR_TOP, CON_RIGHT, DIR_BOTTOM, CON_LEFT, YELLOW,
                DIR_BOTTOM, CON_CENTER, DIR_RIGHT, CON_CENTER, BLACK);
        
        Card card6 = getCard("6", puzzle,
                DIR_TOP, CON_LEFT, DIR_BOTTOM, CON_RIGHT, GREEN,
                DIR_LEFT, CON_CENTER, DIR_RIGHT, CON_CENTER, BLACK,
                DIR_LEFT, CON_RIGHT, DIR_RIGHT, CON_LEFT, BLUE,
                DIR_TOP, CON_CENTER, DIR_BOTTOM, CON_CENTER, YELLOW);
        
        Card card7 = getCard("7", puzzle,
                DIR_LEFT, CON_LEFT, DIR_RIGHT, CON_RIGHT, YELLOW,
                DIR_LEFT, CON_CENTER, DIR_RIGHT, CON_CENTER, BLACK,
                DIR_LEFT, CON_RIGHT, DIR_RIGHT, CON_LEFT, BLUE,
                DIR_TOP, CON_CENTER, DIR_BOTTOM, CON_CENTER, RED);
        
        Card card8 = getCard("8", puzzle,
                DIR_LEFT, CON_CENTER, DIR_BOTTOM, CON_RIGHT, BLUE,
                DIR_TOP, CON_RIGHT, DIR_RIGHT, CON_LEFT, YELLOW,
                DIR_LEFT, CON_LEFT, DIR_RIGHT, CON_RIGHT, GREEN,
                DIR_BOTTOM, CON_CENTER, DIR_RIGHT, CON_CENTER, RED);
        
        Card card9 = getCard("9", puzzle,
                DIR_LEFT, CON_CENTER, DIR_BOTTOM, CON_RIGHT, GREEN,
                DIR_TOP, CON_RIGHT, DIR_RIGHT, CON_LEFT, RED,
                DIR_LEFT, CON_LEFT, DIR_RIGHT, CON_RIGHT, BLACK,
                DIR_BOTTOM, CON_CENTER, DIR_RIGHT, CON_CENTER, YELLOW);
        
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);
        cards.add(card6);
        cards.add(card7);
        cards.add(card8);
        cards.add(card9);
        
        puzzle.setInitialCard(card1);
        permutate(puzzle, cards);
    }
    
    public static Card getCard(String name, Puzzle puzzle, int... params) {
        Card card = new Card(puzzle, name);
        
        ArrayList<PipeConnector> connectors = new ArrayList<>();
        
        connectors.add(new PipeConnector(card, DIR_TOP));
        connectors.add(new PipeConnector(card, DIR_LEFT));
        connectors.add(new PipeConnector(card, DIR_BOTTOM));
        connectors.add(new PipeConnector(card, DIR_RIGHT));
        
        for (int i = 0; i < params.length; i = i + 5) {
            Pipe pipe = new Pipe(connectors.get(params[i + 0]), params[i + 1], connectors.get(params[i + 2]), params[i + 3], Pipe.Color.values()[params[i + 4]]);
        }
        
        for (PipeConnector connector : connectors) {
            card.addPipeConnector(connector);
        }
        
        return card;
    }
    
    public static String getDirName(int direction) {
        String directionStr;
        switch (direction) {
            case PipePuzzle.DIR_TOP:
                directionStr = "top";
                break;
            case PipePuzzle.DIR_LEFT:
                directionStr = "left";
                break;
            case PipePuzzle.DIR_BOTTOM:
                directionStr = "bottom";
                break;
            case PipePuzzle.DIR_RIGHT:
                directionStr = "right";
                break;
            default:
                directionStr = "none";
        }
        return directionStr;
    }
    public static int bestScore = 0;
    public static long calculations = 0;
    public static Random random = new Random();
    
    public static void permutate(Puzzle puzzle, ArrayList<Card> cards) {
        if (cards.isEmpty()) {
            calculations++;
            int score = puzzle.getCurrentScore();
            if (score > bestScore) {
                bestScore = score;
                System.out.println("#" + calculations + " Score reached: " + score);
                System.out.println(puzzle.toString());
            }
            return;
        }
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            ArrayList<Point> possibleLocations = puzzle.getAvailableLocations();
            while (possibleLocations.size() > 0) {
                Point location = possibleLocations.remove(random.nextInt(possibleLocations.size()));
                puzzle.addCard(card, location);
                cards.remove(card);
                
                permutate(puzzle, cards);
                card.rotate();
                permutate(puzzle, cards);
                card.rotate();
                
                cards.add(card);
                puzzle.removeCard(location);
            }
        }
    }
}
