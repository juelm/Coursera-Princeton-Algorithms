/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

import java.util.Iterator;

public class Board {

    private int[][] current;
    private int dim;


    public Board(int[][] blocks) {
        if (blocks == null) throw new IllegalArgumentException();
        //current = blocks;
        dim = blocks.length;
        int[][] initialize = new int[blocks.length][blocks.length];

        for (int i = 0; i < blocks.length; i++) {
            int[] tempo = new int[blocks.length];
            for (int j = 0; j < blocks.length; j++) {

                tempo[j] = blocks[i][j];
            }
            initialize[i] = tempo;
        }

        current = initialize;

    }          // construct a board from an n-by-n array of blocks

    // (where blocks[i][j] = block in row i, column j)
    public int dimension() {
        return dim;                // board dimension n
    }

    public int hamming() {
        int misplaced = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (current[i][j] == 0) {
                    continue;
                }
                else if (current[i][j] != (i * dim) + (j + 1)) {
                    misplaced++;
                }
            }
        }
        return misplaced;
    }                // number of blocks out of place

    public int manhattan() {
        int manny = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {

                if (current[i][j] == 0) {
                    manny = manny + 0;
                }
                else if (current[i][j] % dim == 0) {
                    manny = manny + Math.abs(((current[i][j] / dim) - 1) - i) + dim - (j + 1);
                }
                else if (current[i][j] < dim) {
                    manny = manny + (i - 0) + Math.abs((j + 1) - current[i][j]);
                }
                else if (Math.floor(current[i][j] / dim) != i || current[i][j] % dim != (j + 1)) {
                    manny = manny + Math.abs((current[i][j] / dim) - i) + Math
                            .abs((current[i][j] % dim) - (j + 1));
                }
            }
        }
        return manny;
    }                // sum of Manhattan distances between blocks and goal

    public boolean isGoal() {
        if (this.manhattan() == 0) {
            return true;
        }

        return false;
    }               // is this board the goal board?

    public Board twin() {
        int[][] twinsies = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            int[] tempo = new int[dim];
            for (int j = 0; j < dim; j++) {

                tempo[j] = current[i][j];
            }
            twinsies[i] = tempo;
        }

        // double firstX = Math.random() * dim;
        // double firstY = Math.random() * dim;
        // double secondX = Math.random() * dim;
        // double secondY = Math.random() * dim;
        // int temp = twinsies[(int) firstX][(int) firstY];
        // twinsies[(int) firstX][(int) firstY]
        //         = twinsies[(int) secondX][(int) secondY];
        // twinsies[(int) secondX][(int) secondY] = temp;

        if (twinsies[0][0] != 0) {
            if (twinsies[dim - 1][dim - 1] != 0) {
                int temp = twinsies[0][0];
                twinsies[0][0] = twinsies[dim - 1][dim - 1];
                twinsies[dim - 1][dim - 1] = temp;
            }
            else {
                int temp = twinsies[0][0];
                twinsies[0][0] = twinsies[dim - 1][dim - 2];
                twinsies[dim - 1][dim - 2] = temp;
            }

        }
        else {
            int temp = twinsies[0][1];
            twinsies[0][1] = twinsies[dim - 1][dim - 1];
            twinsies[dim - 1][dim - 1] = temp;
        }

        Board twinning = new Board(twinsies);
        return twinning;
    }                   // a board that is obtained by exchanging any pair of blocks

    public boolean equals(Object y) {
        boolean isEqual = true;
        if (y == this) return true;
        if (!(y instanceof Board)) return false;
        Board brd = (Board) y;
        if (this.dim != brd.dim) return false;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (this.current[i][j] != brd.current[i][j]) {
                    isEqual = false;
                }
            }
        }

        return isEqual;

    }       // does this board equal y?

    public Iterable<Board> neighbors() {

        class boardIterator implements Iterator<Board> {
            private Queue<Board> neighborBoards = new Queue<Board>();

            public boardIterator() {
                int[][] temp1 = new int[dim][dim];
                int[][] temp2 = new int[dim][dim];
                int[][] temp3 = new int[dim][dim];
                int[][] temp4 = new int[dim][dim];

                int emptyX = 0;
                int emptyY = 0;

                for (int i = 0; i < dim; i++) {
                    int[] tempo1 = new int[dim];
                    int[] tempo2 = new int[dim];
                    int[] tempo3 = new int[dim];
                    int[] tempo4 = new int[dim];
                    for (int j = 0; j < dim; j++) {
                        if (current[i][j] == 0) {
                            emptyX = j;
                            emptyY = i;
                        }
                        tempo1[j] = current[i][j];
                        tempo2[j] = current[i][j];
                        tempo3[j] = current[i][j];
                        tempo4[j] = current[i][j];

                    }
                    temp1[i] = tempo1;
                    temp2[i] = tempo2;
                    temp3[i] = tempo3;
                    temp4[i] = tempo4;
                }

                if (emptyY > 0) {
                    Board neighborBoard = new Board(temp1);
                    neighborBoard.current[emptyY][emptyX] = neighborBoard.current[emptyY
                            - 1][emptyX];
                    neighborBoard.current[emptyY - 1][emptyX] = 0;
                    neighborBoards.enqueue(neighborBoard);
                }

                if (emptyY < dim - 1) {
                    Board neighborBoard = new Board(temp2);
                    neighborBoard.current[emptyY][emptyX] = neighborBoard.current[emptyY
                            + 1][emptyX];
                    neighborBoard.current[emptyY + 1][emptyX] = 0;
                    neighborBoards.enqueue(neighborBoard);
                }

                if (emptyX > 0) {
                    Board neighborBoard = new Board(temp3);
                    neighborBoard.current[emptyY][emptyX] = neighborBoard.current[emptyY][emptyX
                            - 1];
                    neighborBoard.current[emptyY][emptyX - 1] = 0;
                    neighborBoards.enqueue(neighborBoard);
                }

                if (emptyX < dim - 1) {
                    Board neighborBoard = new Board(temp4);
                    neighborBoard.current[emptyY][emptyX] = neighborBoard.current[emptyY][emptyX
                            + 1];
                    neighborBoard.current[emptyY][emptyX + 1] = 0;
                    neighborBoards.enqueue(neighborBoard);
                }

            }

            public boolean hasNext() {
                if (neighborBoards.isEmpty()) {
                    return false;
                }
                else {
                    return true;
                }
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public Board next() {
                return neighborBoards.dequeue();
            }
        }

        class neighborIterable implements Iterable<Board> {
            public Iterator<Board> iterator() {
                return new boardIterator();
            }
        }

        return new neighborIterable();

    }    // all neighboring boards

    public String toString() {
        String outString = Integer.toString(dim) + "\n";
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                outString = outString + Integer.toString(current[i][j]) + " ";
            }

            outString = outString + "\n";
        }
        return outString;
    }              // string representation of this board (in the output format specified below)


    //     public static void main(String[] args) // unit tests (not graded)
    // }
}
