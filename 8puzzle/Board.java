import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private int[][] tiles;
    private final int dimension;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){

        dimension = tiles.length;
        this.tiles = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++)
            this.tiles[i] = Arrays.copyOf(tiles[i], dimension);
    }

    // string representation of this board
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dimension);
        stringBuilder.append("\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++)
                stringBuilder.append(" " + tiles[i][j]);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of tiles out of place
    public int hamming(){
        int hammingDistance = -1;
        int temp = 1;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if(tiles[i][j] != temp++)
                    hammingDistance++;
            }
        }
        return hammingDistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int manhattanDistance = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] != 0) {
                    int r = (tiles[i][j] - 1) / dimension;
                    int c = (tiles[i][j] - 1) % dimension;
                    manhattanDistance += Math.abs(i - r) + Math.abs(j - c);
                }
            }
        }
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal(){
        int temp = 1;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if(tiles[i][j] != temp++ && !((i == dimension-1) && (j == dimension-1)) )
                    return false;
            }
        }
        return tiles[dimension-1][dimension-1] == 0;
    }

    // does this board equal y?
    public boolean equals(Object y){
        if (y == null)
            return false;
        else if (y.getClass() != Board.class)
            return false;
        else {
            Board boardy = (Board) y;
            return Arrays.deepEquals(this.tiles, (boardy.tiles));
        }
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        ArrayList<Board> res = new ArrayList<>();
        int zerox = -1;
        int zeroy = -1;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if(tiles[i][j] == 0) {
                    zerox = i;
                    zeroy = j;
                }
            }
        }
        if(zerox+1 < dimension)
        {
            Board boardR = new Board(tiles);
            boardR.tiles[zerox][zeroy] = boardR.tiles[zerox+1][zeroy];
            boardR.tiles[zerox+1][zeroy] = 0;
            res.add(boardR);
        }
        if(zerox-1 > -1)
        {
            Board boardL = new Board(tiles);
            boardL.tiles[zerox][zeroy] = boardL.tiles[zerox-1][zeroy];
            boardL.tiles[zerox-1][zeroy] = 0;
            res.add(boardL);
        }
        if(zeroy+1 < dimension)
        {
            Board boardB = new Board(tiles);
            boardB.tiles[zerox][zeroy] = boardB.tiles[zerox][zeroy+1];
            boardB.tiles[zerox][zeroy+1] = 0;
            res.add(boardB);
        }
        if(zeroy-1 > -1)
        {
            Board boardT = new Board(tiles);
            boardT.tiles[zerox][zeroy] = boardT.tiles[zerox][zeroy-1];
            boardT.tiles[zerox][zeroy-1] = 0;
            res.add(boardT);
        }
        return res;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        int[][] temp = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++)
            temp[i] = Arrays.copyOf(tiles[i], dimension);
        int num1Row = 0,num1Col = 0,num2Row = 1,num2Col = 1;

        if (temp[num1Row][num1Col] == 0)
            num1Row = 1;
        if (temp[num2Row][num2Col] == 0)
            num2Row = 0;

        int tmpNum = temp[num1Row][num1Col];
        temp[num1Row][num1Col] = temp[num2Row][num2Col];
        temp[num2Row][num2Col] = tmpNum;

        return new Board(temp);
    }

    // unit testing (not graded)
    public static void main(String[] args){
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();

        Board board = new Board(tiles);
        StdOut.println(board);
//        StdOut.println(board.manhattan());
//        StdOut.println(board.twin());
        for (Board b:
             board.neighbors()) {
            StdOut.println(b);
        }
    }
}
