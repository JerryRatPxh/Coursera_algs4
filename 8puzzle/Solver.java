import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private static class SearchNode implements Comparable<SearchNode> {
        private final int CurrentMoves;
        private final SearchNode prevNode;
        private final Board board;
        private final int manhattanDistance;

        SearchNode(Board board, SearchNode prevNode){
            this.board = board;
            this.prevNode = prevNode;
            if (prevNode != null)
                CurrentMoves = prevNode.CurrentMoves + 1;
            else
                CurrentMoves = 0;
            manhattanDistance = board.manhattan();
        }

        @Override
        public int compareTo(SearchNode node) {
            if (this.CurrentMoves + this.manhattanDistance > node.CurrentMoves + node.manhattanDistance)
                return 1;
            else if (this.CurrentMoves + this.manhattanDistance < node.CurrentMoves + node.manhattanDistance)
                return -1;
            return 0;
        }
    }


    private Boolean hasSolution;
    private SearchNode node;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        if(initial == null)
            throw new IllegalArgumentException("initial board can't be null");
        MinPQ<SearchNode> minPQ = new MinPQ<>();
        MinPQ<SearchNode> twinMinPQ = new MinPQ<>();
        hasSolution = false;
        SearchNode initialNode = new SearchNode(initial, null);
        minPQ.insert(initialNode);
        SearchNode twinInitialNode = new SearchNode(initial.twin(), null);
        twinMinPQ.insert(twinInitialNode);
        while(true){
            node = minPQ.delMin();
            Board currentBoard = node.board;
            if (node.board.isGoal()) {
                hasSolution = true;
                break;
            }
            for (Board board:
                 currentBoard.neighbors()) {
                if (node.prevNode != null)
                    if (board.equals(node.prevNode.board))
                        continue;
                SearchNode tmpNode = new SearchNode(board, node);
                minPQ.insert(tmpNode);
            }

            //检查twin board是否有解，有解则说明原board无解
            node = twinMinPQ.delMin();
            Board currentTwinBoard = node.board;
            if (node.board.isGoal()) {
                hasSolution = false;
                break;
            }
            for (Board board:
                    currentTwinBoard.neighbors()) {
                if (node.prevNode != null)
                    if (board.equals(node.prevNode.board))
                        continue;
                SearchNode tmpNode = new SearchNode(board, node);
                twinMinPQ.insert(tmpNode);
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return hasSolution;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        if(!hasSolution)
            return -1;
        SearchNode currentNode = node;
        int cnt = -1;
        while(currentNode!= null){
            currentNode = currentNode.prevNode;
            cnt++;
        }
        return cnt;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
        if(!hasSolution)
            return null;
        Stack<Board> res = new Stack<>();
        SearchNode currentNode = node;
        while(currentNode != null){
            res.push(currentNode.board);
            currentNode = currentNode.prevNode;
        }
        return res;
    }

    // test client (see below)
    public static void main(String[] args){
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
