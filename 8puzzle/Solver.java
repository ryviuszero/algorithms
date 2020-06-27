
/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Solver {
    private class Node implements Comparable<Node> {
        private final Board board;
        private final Node pre;
        private int moves;
        private Integer priority;

        public Node(Board board, Node pre, int moves) {
            this.board = board;
            this.pre = pre;
            this.moves = moves;
        }

        public int getPriority() {
            if (this.priority == null) priority = board.manhattan() + moves;
            return priority;
        }

        public int compareTo(Node o) {
            return this.getPriority() - o.getPriority();
        }
    }

    private Node solutionNode;
    private boolean solverable = true;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        Node initialNode = new Node(initial, null, 0);
        MinPQ<Node> gameTree = new MinPQ<>();
        gameTree.insert(initialNode);

        Node initialTwin = new Node(initial.twin(), null, 0);
        MinPQ<Node> twinTree = new MinPQ<>();
        twinTree.insert(initialTwin);

        while (solutionNode == null && isSolvable()) {
            Node currNode = gameTree.delMin();
            if (currNode.board.isGoal()) {
                solutionNode = currNode;
                return;
            }

            for (Board neighbor : currNode.board.neighbors()) {
                if (currNode.pre != null && neighbor.equals(currNode.pre.board)) continue;
                else
                    gameTree.insert(
                            new Node(neighbor, currNode, currNode.moves + 1));
            }

            Node currTwin = twinTree.delMin();
            if (currTwin.board.isGoal()) {
                solverable = false;
                return;
            }

            for (Board neighbor : currTwin.board.neighbors()) {
                if (currTwin.pre != null && neighbor.equals(currTwin.pre.board)) continue;
                else
                    twinTree.insert(
                            new Node(neighbor, currTwin, currTwin.moves + 1));
            }
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solverable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!solverable) {
            return -1;
        }
        return solutionNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        List<Board> list = new LinkedList<>();
        Node tmp = solutionNode;
        while (tmp != null) {
            list.add(tmp.board);
            tmp = tmp.pre;
        }
        Collections.reverse(list);
        return list;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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
    }
}
