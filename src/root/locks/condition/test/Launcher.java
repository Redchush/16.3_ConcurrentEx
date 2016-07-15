package root.locks.condition.test;

import org.apache.log4j.Logger;
import root.util.CheckDeadlockDeamon;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Launcher {

    private final static Logger logger = Logger.getRootLogger();

    public static void main(String[] args) throws InterruptedException {
        int dishes = 3;
        int cats = 7;

        Human human = new Human(new Dish());

        ExecutorService service = Executors.newCachedThreadPool();
        String baseName = "Cat-";
        for (int i = 0; i < cats; i++) {
            Cat cat = new Cat(baseName + i, human);
            service.execute(cat);
        }
        long timeToLive = 4000;
        Thread thread = new CheckDeadlockDeamon("pool", cats, timeToLive); //the demon, who check if the deadlock occurred
        TimeUnit.MILLISECONDS.sleep(timeToLive);
        service.shutdown();
        logger.debug("All cats are tired");
    }
}
