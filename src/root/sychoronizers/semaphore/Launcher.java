package root.sychoronizers.semaphore;

import org.apache.log4j.Logger;
import root.util.CheckDeadlockDeamon;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Launcher {
    /*
  There is one bed with some limited count of pillows, several cats, who
  1) walk random time
  2) become tiered and want to lay on bed. They ask BedManager to get him a pillow.
    If there is free place, cat enjoy the pillow, if no -- return to his usual cat's deal.
  and one man, who
  1) walk random time
  2) become tiered and want to lay on bed. He ask to BedManager to get him a pillows.
  He lay on all free pillows and when the last cat leave the bed don't offer other
   cat's lay on the bed.
        */
    private final static Logger logger = Logger.getRootLogger();

    public static void main(String[] args) throws InterruptedException {
        int catsNum = 6;
        int pillowsForCats = 3;

        ExecutorService service = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(pillowsForCats);
        Bed bed = new Bed(pillowsForCats);
        BedManager manager = new BedManager(bed, semaphore);

        String catBaseName = "Cat-";
        for (int i = 0; i < catsNum; i++) {
            Cat cat = new Cat(catBaseName + i, manager);
            service.submit(cat);
        }
        Man man = new Man(manager);
        service.submit(man);
        int timeToLive = 2000;
        Thread thread = new CheckDeadlockDeamon("pool", catsNum, timeToLive); //the demon, who check if the deadlock occurred

        TimeUnit.MILLISECONDS.sleep(timeToLive);
        service.shutdown();
        service.shutdownNow();
    }
}
