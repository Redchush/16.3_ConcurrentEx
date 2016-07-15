package root.locks.readWriteLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {
    /*
     There are some cats on food with weight. Only one cat eats food and wherefore the food weight
     decreases. Sentinel of this action is WriteLock.
     But if the cat sees no food more, it goes away. Cat only "read" the data, so
     for this action responsibility lays on ReadLock.
     */

    public static void main(String[] args) {
        int foodWeight = 50;
        Food food = new Food(foodWeight);
        Cat catTom = new Cat("Tom", food);
        Cat catBob = new Cat("Bob", food);
        Cat catIo = new Cat("Io", food);

        ExecutorService service = Executors.newCachedThreadPool();

        service.submit(catTom);
        service.submit(catBob);
        service.submit(catIo);
        service.shutdown();
    }
}
