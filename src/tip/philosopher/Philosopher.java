package tip.philosopher;

import org.apache.log4j.Logger;
import tip.fork.ForkManager;


import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Philosopher implements Runnable {

    private final ForkManager manager;
    private final int id;
    /**
     * how much the philosopher spend time on thinking
     */
    private final int thinkingRate;
    private final static Logger logger = Logger.getRootLogger();

    public Philosopher(int id, int thinkingRate, ForkManager manager) {
        this.id = id;
        this.thinkingRate = thinkingRate;
        this.manager = manager;
    }
    /*
    *Used resource hierarchy solution originally proposed by Dijkstra.
    * There is the strict order by which philosophers grabbed the fork and drop it down.
    * Furthermore, there the ForkManager, who distribute the forks and on watch to see which fork belong
     * to each philosopher
     */
    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                logger.debug("Philosopher " + id + " thinking ");
                thinking();
                takeForks();
                logger.debug("Philosopher " + id + " eating.");
                thinking();
                manager.dropFork(this, true);
                manager.dropFork(this, false);
                logger.debug("Philosopher " + id + " drop his forks.");
            }
        } catch (InterruptedException e){
            logger.debug("Philosopher " + id + " going home.");
        }

    }
 /*
 * This method disrupt the circle of threads locking:
 * All philosophers get the right fork at first, but first philosopher grab the left fork at first and only
 * then reach the arm for right fork.
 * This method disrupt the circle of threads waiting -> the important condition of deadlock is deleted .
 *
  */
    private void takeForks() throws InterruptedException {
        if (this.id != 0) {
            logger.debug("Philosopher " + id + " getting the right fork");
            manager.takeFork(this, true);
            logger.debug("Philosopher " + id + " getting the left fork.");
            manager.takeFork(this, false);
        } else {
            logger.debug("Philosopher " + id + " getting the left fork.");
            manager.takeFork(this, false);
            logger.debug("Philosopher " + id + " getting the right fork");
            manager.takeFork(this, true);
        }
    }
    private void thinking() throws InterruptedException {
        int sleepTime = generateSleepTime();
        TimeUnit.MILLISECONDS.sleep(sleepTime);
    }
    private int generateSleepTime(){
        Random random = new Random();
        return random.nextInt(thinkingRate);
    }

    public int getId() {
        return id;
    }
}
