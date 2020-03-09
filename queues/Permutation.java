/* *****************************************************************************
 *  Name:   ryviuszero
 *  Date:   2020/3/9
 *  Description:
 *****************************************************************************/

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (k == 0) {
                return;
            }
            else {
                rq.enqueue(s);
            }
        }

        while (k-- > 0) {
            System.out.println(rq.dequeue());
        }
    }
}
