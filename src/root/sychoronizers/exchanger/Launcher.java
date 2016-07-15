package root.sychoronizers.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {
    /*
    There are two cats. They
    1) sleep random time,
    2) hunt for prey, procures two preys.
    3) exchange food with another cat.

     */

    public static void main(String[] args) {

        ExecutorService service = Executors.newCachedThreadPool();
        Exchanger<Prey> preyExchanger = new Exchanger<>();
        Cat catTom = new Cat("Tom", preyExchanger);
        Cat catBob = new Cat("Bob", preyExchanger);
        service.submit(catBob);
        service.submit(catTom);
        service.shutdown();
    }

}
