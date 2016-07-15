package root.locks.reentalLock;


import root.util.CheckDeadlockDeamon;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Launcher {
  /* There are human and several cats. All cats want to play with human.
     But only one cat at one moment play with human, so other cats need to wait
     their time. If they wait too long, they feel hurt and go away from human.
     The Lock object in Human class represent the sentinel who allow only one
     cat play.

     */
    public static void main(String[] args) throws InterruptedException {

        Human human = new Human();
        Cat catTom = new Cat("Tom", human);
        Cat catBob = new Cat("Bob", human);
        Cat catIo = new Cat("Io", human);

        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(catTom);
        service.submit(catBob);
        service.submit(catIo);
        service.shutdown();
        int timeToLive = 1000;
        Thread thread = new CheckDeadlockDeamon("pool", 3, timeToLive); //the demon, who check if the deadlock occurred
                                                                        //and interrupt threads when time expired
        TimeUnit.MILLISECONDS.sleep(timeToLive);
        service.shutdown();
    }
}
