/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pipepuzzle;

import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author bLind
 */
class PipeConnector {

    private Card card;
    private int direction;
    /**
     * if the connectors card has a neighbour, this is the pipeConnectors
     * adjacent pipeconnector
     */
    private PipeConnector neighbour;
    /**
     * Maps from the pipeConnection to the corresponding pipe
     */
    private HashMap<Integer, Pipe> pipes;

    public PipeConnector(Card card, int direction) {
        this.card = card;
        this.direction = direction;
        this.pipes = new HashMap<>();
    }

    
    public void removeNeighbour() {
        this.neighbour.neighbour = null;
        this.neighbour = null;
    }
    
    public void setNeighbour(PipeConnector neighbour) {
        this.neighbour = neighbour;
        neighbour.neighbour = this;
    }

    public PipeConnector getNeighbour() {
        return neighbour;
    }

    public void addPipe(int pipeConnection, Pipe pipe) {
        this.pipes.put(pipeConnection, pipe);
    }

    public Pipe getPipe(int pipeConnection) {
        return this.pipes.get(pipeConnection);
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    public Collection<Pipe> getPipes() {
        return this.pipes.values();
    }
}
