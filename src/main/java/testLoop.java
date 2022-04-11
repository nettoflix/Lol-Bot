import java.util.ArrayList;
import java.util.List;

public class testLoop {

    public static void main(String s[]) {

        long start, end;

        List<Integer> a =  new ArrayList<Integer>();

        for (int i = 0; i < 2500000; i++) {
            a.add(i);
        }

        ///// TESTING FOR : EACH LOOP

        start = System.currentTimeMillis();

        for (Integer j : a) {
            int x = j + 3;
        }

        end = System.currentTimeMillis();

        System.out.println(end - start
                + " milli seconds for [ Integer j : a ] ");

        ////// TESTING DEFAULT LOOP

        start = System.currentTimeMillis();
        for (int i = 0; i < a.size(); i++) {
            int x = a.get(i) + 3;
        }

        end = System.currentTimeMillis();

        System.out.println(end - start
                + " milli seconds for [ int i = 0; i < a.length; i++ ] ");


        ////// TESTING SLIGHTLY OPTIMIZED LOOP

        start = System.currentTimeMillis();
        int size = a.size();
        for (int i = 0; i < size; i++) {
            int x = a.get(i) + 3;
        }

        end = System.currentTimeMillis();

        System.out.println(end - start
                + " milli seconds for [ int i = 0; i < size; i++ ] ");

        //// TESTING MORE OPTIMIZED LOOP

        start = System.currentTimeMillis();
        int i;
        for (i=size; --i >= 0;) {
            int x = a.get(i) + 3;
        }

        end = System.currentTimeMillis();

        System.out.println(end - start
                + " milli seconds for [ int i = size; --i >= 0; ] ");

    }

}