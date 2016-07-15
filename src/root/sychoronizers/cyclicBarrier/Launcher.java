package root.sychoronizers.cyclicBarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {
    /*
      The cats fight.
      There is group of cats. To organize the fight they need to 1) sleep well
      2) come to meeting and solve important questions together
      3) run to fighting field
      4) together start the fighting;
      So they sleep different time and run with different speed they need to wait
      each other.
      This feature is provided by one Cyclic barrier for all cat's object.

     */

    public static void main(String[] args) {

        int catsNum = 6;

        ExecutorService service = Executors.newCachedThreadPool();
        CyclicBarrier barrier = new CyclicBarrier(catsNum);

        String catBaseName = "Cat-";
        for (int i = 0; i < catsNum; i++) {
            Cat cat = new Cat(catBaseName + i, barrier);
            service.submit(cat);
        }
        service.shutdown();
    }
}
