/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pipepuzzle;

/**
 *
 * @author bLind
 */
public class Pipe {

    public enum Color {

        Black, Blue, Green, Red, Yellow
    }
    private Position end1;
    private Position end2;
    private Color color;
    private boolean evaluated;

    public Pipe(PipeConnector connector1, int pipeConnection1, PipeConnector connector2, int pipeConnection2, Color color) {
        this(new Position(connector1.getDirection(), pipeConnection1, connector1), new Position(connector2.getDirection(), pipeConnection2, connector2), color);
        connector1.addPipe(pipeConnection1, this);
        connector2.addPipe(pipeConnection2, this);
    }

    public Pipe(Position end1, Position end2, Color color) {
        this.end1 = end1;
        this.end2 = end2;
        this.color = color;
        this.evaluated = false;
    }

    public int length() {
        int length = 0;

        length += follow(end1);
        length += follow(end2);

        this.setEvaluated(true);

        if (length == 0) {
            return 0;
        }

        return length + 1;
    }

    public int follow(Position end) {
        Puzzle puzzle = end.connector.getCard().getPuzzle();
        int oppositePipeConnection = puzzle.getOppositePipeConnection(end.connection);
        int oppositePipeDirection = puzzle.getOppositeDirection(end.direction);

        PipeConnector neighbourConnector = end.connector.getNeighbour();
        if (neighbourConnector == null) {
            return 0;
        }

        Pipe nextPipe = neighbourConnector.getPipe(oppositePipeConnection);
        if (nextPipe == null) {
            return 0;
        }
        if (nextPipe.getColor() != this.getColor()) {
            return 0;
        } else {
            nextPipe.setEvaluated(true);
            return 1 + nextPipe.follow(nextPipe.otherEnd(nextPipe.getEnd(oppositePipeDirection)));
        }
    }

    public Position getEnd(int direction) {
        if (this.end1.direction == direction) {
            return this.end1;
        }
        if (this.end2.direction == direction) {
            return this.end2;
        }
        return null;
    }

    public Position otherEnd(Position end) {
        if (this.end1.equals(end)) {
            return this.end2;
        } else {
            return this.end1;
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isEvaluated() {
        return evaluated;
    }

    public void setEvaluated(boolean evaluated) {
        this.evaluated = evaluated;
    }

    public String toString() {
        return this.getColor().name() + " from " + PipePuzzle.getDirName(this.end1.direction) + " to " + PipePuzzle.getDirName(this.end2.direction);
    }

    public static class Position {

        public int direction;
        public int connection;
        public PipeConnector connector;

        public boolean equals(Position other) {
            if (this.direction != other.direction) {
                return false;
            }
            if (this.connection != other.connection) {
                return false;
            }
            return true;
        }

        public Position(int direction, int connection, PipeConnector connector) {
            this.direction = direction;
            this.connection = connection;
            this.connector = connector;
        }
    }
}
