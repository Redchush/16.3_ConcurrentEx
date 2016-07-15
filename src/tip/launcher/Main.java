package tip.launcher;

import org.apache.log4j.Logger;
import tip.fork.ForkManager;
import tip.philosopher.Philosopher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private final static Logger logger = Logger.getRootLogger();

    public static void main(String[] args) throws InterruptedException {
        int thinkingPercentage = 1;
        int count = 5;            //how mush forks and philosophers we have
        ExecutorService service = Executors.newCachedThreadPool();
        ForkManager manager = new ForkManager(5);       //the manager who execute actions on appropriate for each
                                                    // philosopher fork
        List<Runnable> philosophers = new ArrayList<>();  //List of philosophers who is implementing Runnable
        for (int i = 0; i < count; i++) {
             philosophers.add(new Philosopher(i, thinkingPercentage, manager));
        }
        for (Runnable phil : philosophers){
            service.execute(phil);
        }
        long timeToLive = 2000;
        Thread thread = new CheckDeadlockDeamon("pool", count, timeToLive); //the demon, who check if the deadlock occurred
        TimeUnit.MILLISECONDS.sleep(timeToLive);
        service.shutdown();
        logger.debug("All philosophers are tired and go home");
    }
}
