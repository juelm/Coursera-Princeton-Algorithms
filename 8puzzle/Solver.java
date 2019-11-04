/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;


public class Solver {
    //private MinPQ<searchNode> pQ = new MinPQ<searchNode>();
    //private MinPQ<searchNode> unsolvable = new MinPQ<searchNode>();
    private Queue<Board> sequence = new Queue<Board>();
    private int moves = 0;
    private boolean solvable = false;


    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<searchNode> pQ = new MinPQ<searchNode>();
        MinPQ<searchNode> unsolvable = new MinPQ<searchNode>();

        searchNode init = new searchNode(initial, null, 0);
        init.manhattanPriority = initial.manhattan() + init.moves;
        pQ.insert(init);
        moves = pQ.size();

        Board twinsies = initial.twin();
        searchNode twin = new searchNode(twinsies, null, 0);
        twin.manhattanPriority = twinsies.manhattan() + twin.moves;
        unsolvable.insert(twin);

        ///////////////////////////

        // pQ.insert(twin);
        //
        // pQ.size();
        //
        // System.out.println(pQ.size());
        //
        // System.out.println(twin.compareTo(init));
        //
        // searchNode maybeTwin = pQ.delMin();
        //
        // searchNode maybeInit = pQ.delMin();
        //
        // System.out.println(maybeTwin.manhattanPriority);
        //
        // System.out.println(maybeInit.manhattanPriority);
        //
        // System.out.println(maybeTwin.currentBoard.toString());
        //
        // System.out.println(maybeInit.currentBoard.toString());


        // searchNode unMin1 = unsolvable.delMin();
        //
        // for (Board brd : twin.currentBoard.neighbors()) {
        //     if (!unMin1.currentBoard.equals(brd)) {
        //         searchNode newNode = new searchNode(brd, unMin1);
        //         newNode.manhattanPriority = brd.manhattan() + newNode.moves;
        //         unsolvable.insert(newNode);
        //         System.out.println(brd.toString());
        //         System.out.println(brd.toString());
        //     }
        //
        //     else {
        //         System.out.println("This shoule print once");
        //     }
        // }
        //
        // System.out.println(unsolvable.size());

        ///////////////////////////

        while (!pQ.isEmpty() && !unsolvable.isEmpty()) {
            // int Indy = 0;
            // while (Indy < 10) {
            searchNode qMin = pQ.delMin();
            try {
                while (sequence.size() > 1) {
                    if (qMin.previousNode.currentBoard == sequence.peek()) {
                        break;
                    }
                    sequence.dequeue();
                }

            }
            catch (NullPointerException e) {

            }

            sequence.enqueue(qMin.currentBoard);
            moves = qMin.moves;
            // System.out.println("********************************");
            // System.out.println(qMin.currentBoard.toString());
            // System.out.println(qMin.manhattanPriority);
            // System.out.println("********************************");
            searchNode unMin = unsolvable.delMin();
            if (qMin.currentBoard.isGoal()) {
                solvable = true;
                break;
            }
            if (unMin.currentBoard.isGoal()) {
                moves = -1;
                sequence = null;
                break;
            }

            for (Board board : qMin.currentBoard.neighbors()) {
                try {

                    if (!board.equals(qMin.previousNode.currentBoard)) {
                        searchNode newNode = new searchNode(board, qMin);
                        newNode.manhattanPriority = board.manhattan() + newNode.moves;
                        pQ.insert(newNode);

                        // System.out.println("------------------------------");
                        // System.out.println(newNode.manhattanPriority);
                        // System.out.println(board.toString());
                        // System.out.println(moves);

                    }
                }
                catch (NullPointerException e) {
                    searchNode newNode = new searchNode(board, qMin);
                    newNode.manhattanPriority = board.manhattan() + newNode.moves;
                    pQ.insert(newNode);

                    // System.out.println("------------------------------");
                    // System.out.println(newNode.manhattanPriority);
                    // System.out.println(board.toString());
                    // System.out.println(moves);
                }

            }

            for (Board board : unMin.currentBoard.neighbors()) {

                try {

                    if (!board.equals(unMin.previousNode.currentBoard)) {
                        searchNode newNode = new searchNode(board, unMin);
                        newNode.manhattanPriority = board.manhattan() + newNode.moves;
                        unsolvable.insert(newNode);

                    }
                }
                catch (NullPointerException e) {
                    searchNode newNode = new searchNode(board, unMin);
                    newNode.manhattanPriority = board.manhattan() + newNode.moves;
                    unsolvable.insert(newNode);
                }
            }

            //Indy++;
        }
    }           // find a solution to the initial board (using the A* algorithm)

    public boolean isSolvable() {
        return solvable;
    }         // is the initial board solvable?

    public int moves() {
        return moves;
    }                  // min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution() {


        class solutionIterator implements Iterator<Board> {
            private Queue<Board> steps = new Queue<Board>();

            public solutionIterator() {

                int lastIndex = sequence.size();
                for (int i = 0; i < lastIndex; i++) {
                    Board thisBoard = sequence.dequeue();
                    steps.enqueue(thisBoard);
                    sequence.enqueue(thisBoard);
                }
            }

            public boolean hasNext() {
                if (steps.isEmpty()) {
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
                return steps.dequeue();
            }
        }

        class solutionIterable implements Iterable<Board> {
            public Iterator<Board> iterator() {
                return new solutionIterator();
            }
        }

        return new solutionIterable();

    }     // sequence of boards in a shortest solution; null if unsolvable

    private class searchNode implements Comparable<searchNode> {
        private Board currentBoard;
        private searchNode previousNode;
        private int moves;
        private int manhattanPriority;


        public searchNode(Board curr, searchNode prev) {
            this.currentBoard = curr;
            this.previousNode = prev;
            this.moves = prev.moves + 1;
        }

        public searchNode(Board curr, searchNode prev, int priorMoves) {
            this.currentBoard = curr;
            this.previousNode = prev;
            this.moves = priorMoves;
        }

        // public int compareTo(searchNode that) {
        //     return (that.currentBoard.manhattan() + that.moves) - (this.currentBoard.manhattan()
        //             + this.moves);
        // }

        public int compareTo(searchNode that) {
            return this.manhattanPriority - that.manhattanPriority;
        }
    }


    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    } // solve a slider puzzle (given below)

}
