package root.locks.condition;

import org.apache.log4j.Logger;

import java.util.concurrent.*;

public class Launcher {

    private final static Logger logger = Logger.getRootLogger();

    /*
    There is several cats and one Human, which has a washmashime.
    Cats sleep and when ask food, calling the Human
    Human take the dish, feed cat and then put the dish to washmashine
    Human can't take a dirty dish, he wait until washmashine in separate thread wash
    the dish.  Washmashine notify by Condition object the human when the dish will be clean.

     */

    public static void main(String[] args) throws InterruptedException {

        int dishesCount = 3;
        int cats = 7;
        BlockingQueue<Dish> dishes = new ArrayBlockingQueue<Dish>(dishesCount);
        for (int i = 0; i < dishesCount ; i++) {
            dishes.put(new Dish(i));
        }
        Human human = new Human(dishes);

        ExecutorService service = Executors.newCachedThreadPool();
        String baseName = "Cat-";
        for (int i = 0; i < cats; i++) {
            Cat cat = new Cat(baseName + i, human);
            service.execute(cat);
        }

        long timeToLive = 4000;
        TimeUnit.MILLISECONDS.sleep(timeToLive);
        service.shutdownNow();
        logger.debug("All cats are tired");
    }
}
