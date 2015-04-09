/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pipepuzzle;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author bLind
 */
public class Card {

    private String name;
    private boolean upSideDown;
    private Puzzle puzzle;
    private ArrayList<Pipe> pipes;
    /**
     * Maps from the direction to the corresponding neighbour card
     */
    private HashMap<Integer, Card> neighbours;
    /**
     * Maps from the direction to the corresponding pipeConnector
     */
    private HashMap<Integer, PipeConnector> pipeConnectors;

    public Card(Puzzle puzzle, String name) {
        this.puzzle = puzzle;
        this.pipes = new ArrayList<>();
        this.neighbours = new HashMap<>();
        this.pipeConnectors = new HashMap<>();
        this.name = name;
        this.upSideDown = false;
    }

    public void setNeighbour(int direction, Card neighbour) {
        int oppositeDirection = puzzle.getOppositeDirection(direction);
        this.neighbours.put(direction, neighbour);
        neighbour.neighbours.put(oppositeDirection, this);

        this.pipeConnectors.get(direction).setNeighbour(neighbour.getPipeConnector(oppositeDirection));
    }

    public Card getNeighbour(int direction) {
        return this.neighbours.get(direction);
    }

    public int getNeighbourCount() {
        return this.neighbours.size();
    }

    public void removeNeighbour(int direction) {
        int oppositeDirection = this.puzzle.getOppositeDirection(direction);
        Card neighbour = this.getNeighbour(direction);

        if (neighbour == null) {
            return;
        }

        this.neighbours.remove(direction);
        neighbour.neighbours.remove(oppositeDirection);
        this.pipeConnectors.get(direction).removeNeighbour();
    }

    private void addPipe(Pipe pipe) {
        pipes.add(pipe);
    }

    public ArrayList<Pipe> getPipes() {
        return this.pipes;
    }

    public void addPipeConnector(PipeConnector pipeConnector) {
        this.pipeConnectors.put(pipeConnector.getDirection(), pipeConnector);
        for (Pipe pipe : pipeConnector.getPipes()) {
            if (this.pipes.contains(pipe)) {
                continue;
            }
            this.addPipe(pipe);
        }
    }

    public PipeConnector getPipeConnector(int direction) {
        return this.pipeConnectors.get(direction);
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void rotate() {
        this.upSideDown = !this.upSideDown;
    }

    public boolean isUpSideDown() {
        return upSideDown;
    }
}
