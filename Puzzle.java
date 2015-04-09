/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pipepuzzle;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author bLind
 */
public class Puzzle {

    private static int TOP = PipePuzzle.DIR_TOP;
    private static int LEFT = PipePuzzle.DIR_LEFT;
    private static int BOTTOM = PipePuzzle.DIR_BOTTOM;
    private static int RIGHT = PipePuzzle.DIR_RIGHT;
    private ArrayList<Card> cards;
    /**
     * the number of possible directions, must be product of 2 because I'm lazy
     * and no genius, I will set this to 4 and implement a 2D grid instead of
     * something crazy
     */
    private int numberOfDirections;
    /**
     * the number of pipe connections per card
     */
    private int numberOfPipeConnections;
    private Card[][] grid;
    private int gridsize;

    public Puzzle(int numberOfCards, int numberOfPipeConnections) {
        this.numberOfDirections = 4;
        this.numberOfPipeConnections = numberOfPipeConnections;
        this.cards = new ArrayList<>();
        this.gridsize = numberOfCards * 2 - 1;
        this.grid = new Card[this.gridsize][this.gridsize];
    }

    public void setInitialCard(Card card) {
        this.grid[this.gridsize / 2][this.gridsize / 2] = card;

        this.cards.add(card);
    }

    public void addCard(Card card, Point location) {
        this.addCard(card, location.x, location.y);
    }

    public void addCard(Card card, int x, int y) {
        if (hasCard(x, y)) {
            return;
        }

        this.cards.add(card);
        this.grid[x][y] = card;

        if (hasCard(x, y - 1)) {
            card.setNeighbour(TOP, this.getCard(x, y - 1));
        }
        if (hasCard(x, y + 1)) {
            card.setNeighbour(BOTTOM, this.getCard(x, y + 1));
        }
        if (hasCard(x - 1, y)) {
            card.setNeighbour(LEFT, this.getCard(x - 1, y));
        }
        if (hasCard(x + 1, y)) {
            card.setNeighbour(RIGHT, this.getCard(x + 1, y));
        }

    }

    public void removeCard(Point location) {
        this.removeCard(location.x, location.y);
    }

    public void removeCard(int x, int y) {
        if (!hasCard(x, y)) {
            System.err.println("Trying to remove card from empty grid cell.");
            return;
        }

        Card card = this.grid[x][y];
        if (hasCard(x, y - 1)) {
            card.removeNeighbour(TOP);
        }
        if (hasCard(x, y + 1)) {
            card.removeNeighbour(BOTTOM);
        }
        if (hasCard(x - 1, y)) {
            card.removeNeighbour(LEFT);
        }
        if (hasCard(x + 1, y)) {
            card.removeNeighbour(RIGHT);
        }

        this.cards.remove(card);
        this.grid[x][y] = null;
    }

    public void hasCard(Point location) {
        this.hasCard(location.x, location.y);
    }

    public boolean hasCard(int x, int y) {
        if (x < 0 || y < 0 || x >= this.gridsize || y >= this.gridsize) {
            return false;
        }

        return (this.grid[x][y] != null);
    }

    public Card getCard(int x, int y) {
        return this.grid[x][y];
    }

    public boolean hasNeighbour(int x, int y) {
        if (hasCard(x, y - 1)) {
            return true;
        }
        if (hasCard(x, y + 1)) {
            return true;
        }
        if (hasCard(x - 1, y)) {
            return true;
        }
        if (hasCard(x + 1, y)) {
            return true;
        }
        return false;
    }

    public ArrayList<Point> getAvailableLocations() {
        ArrayList<Point> locations = new ArrayList<>();

        for (int x = 0; x < this.gridsize; x++) {
            for (int y = 0; y < this.gridsize; y++) {
                if (!hasCard(x, y) && hasNeighbour(x, y)) {
                    locations.add(new Point(x, y));
                }
            }
        }

        return locations;
    }

    public int getCurrentScore() {
        int score = 0;
        int counter = 0;
        for (Card card : this.cards) {
            counter++;
            for (Pipe pipe : card.getPipes()) {
                if (pipe.isEvaluated()) {
                    continue;
                }

                score += pipe.length();
                pipe.setEvaluated(true);
            }
        }

        // reset pipe-evaluation status
        for (Card card : this.cards) {
            for (Pipe pipe : card.getPipes()) {
                pipe.setEvaluated(false);
            }
        }

        return score;
    }

    /**
     * Tells you the id of the opposite of the given direction. if you have a
     * square with directions 0, 1, 2 and 3 getOppositeDirection(0) will return
     * 2 getOppositeDirection(3) will return 1, and so on
     *
     * @param direction the direction you need the opposite of
     * @return id of the opposite direction
     */
    public int getOppositeDirection(int direction) {
        return (direction + this.numberOfDirections / 2) % this.numberOfDirections;
    }

    public int getOppositePipeConnection(int pipeConnection) {
        int offset = (this.numberOfPipeConnections % 2 == 0) ? 1 : 0;
        return (pipeConnection - this.numberOfPipeConnections / 2) * (-1) + (this.numberOfPipeConnections / 2) - offset;
    }

    public String toString() {
        String grid = "";
        for (int y = 0; y < this.gridsize; y++) {
            for (int x = 0; x < this.gridsize; x++) {
                if (hasCard(x, y)) {
                    grid += getCard(x, y).getName() + (getCard(x, y).isUpSideDown() ? ". " : "' ");
                } else {
                    grid += "X  ";
                }
            }
            grid += '\n';
        }
        return grid;
    }
}
