import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        for (int i = 0; i < num; i++) {
            String s = StdIn.readString();
            randomizedQueue.enqueue(s);
        }
        for (String s:
             randomizedQueue) {
            StdOut.println(s);
        }
    }
}