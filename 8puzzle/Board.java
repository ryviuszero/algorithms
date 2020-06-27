/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.LinkedList;
import java.util.List;

public class Board {
    private final int[][] tiles;
    private final int size;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException();

        size = tiles.length;

        this.tiles = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }

    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(" " + tiles[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        int cnt = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i * size + j < size * size - 1 && this.tiles[i][j] != (i * size + j + 1)) {
                    cnt += 1;
                }
            }
        }
        return cnt;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int cnt = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] != 0) {
                    int tar = tiles[i][j] - 1;
                    int g_i = tar / size;
                    int g_j = tar % size;
                    cnt += Math.abs(i - g_i) + Math.abs(j - g_j);
                }
            }
        }
        return cnt;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i * size + j < size * size - 1 && this.tiles[i][j] != (i * size + j + 1)) {
                    return false;
                }
            }
        }
        return true;
        // return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;

        if (y != null && y.getClass() == Board.class) {
            Board obj = (Board) y;
            if (obj.size != size) return false;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (this.tiles[i][j] != obj.tiles[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.tiles[i][j] == 0) {
                    // up
                    if (i > 0) {
                        Board newBoard = swapTiles((i - 1) * size + j, i * size + j);
                        neighbors.add(newBoard);
                    }

                    // right
                    if (j < size - 1) {
                        Board newBoard = swapTiles(i * size + j + 1, i * size + j);
                        neighbors.add(newBoard);
                    }

                    // down
                    if (i < size - 1) {
                        Board newBoard = swapTiles((i + 1) * size + j, i * size + j);
                        neighbors.add(newBoard);
                    }

                    //left
                    if (j > 0) {
                        Board newBoard = swapTiles(i * size + j - 1, i * size + j);
                        neighbors.add(newBoard);
                    }
                    break;
                }
            }
        }
        return neighbors;
    }

    private Board swapTiles(int tar, int src) {
        int[][] newTiles = copyTiles();
        int tmp = newTiles[tar / size][tar % size];
        newTiles[tar / size][tar % size] = newTiles[src / size][src % size];
        newTiles[src / size][src % size] = tmp;
        return new Board(newTiles);
    }

    private int[][] copyTiles() {
        int[][] newTiles = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newTiles[i][j] = tiles[i][j];
            }
        }
        return newTiles;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int tar = 0;
        int src = size * size - 1;

        while (tiles[tar / size][tar % size] == 0) tar++;
        while (tiles[src / size][src % size] == 0) src--;
        return swapTiles(tar, src);
    }


    public static void main(String[] args) {

    }
}
