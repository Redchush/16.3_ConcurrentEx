package root.locks.reentalLock;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Human {

    private final static Logger logger = Logger.getRootLogger();
    private Lock lock;

    public Human() {
        lock = new ReentrantLock();
    }

    public void  doPlaying(Cat cat) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            logger.debug("Human throw the mouse on ." + i + " meter to " + cat.getName()) ;
            Thread.sleep(generatePlayingTime()); // human wait while cat catch the mouse
        }
    }

    private int generatePlayingTime(){
        int playingRate = 3;
        Random random = new Random();
        return random.nextInt(playingRate) + 1;
    }

    public void doLogging(Cat cat) {
        logger.debug("Human complete playing with " + cat.getName());
    }

    public Lock getLock() {
        return lock;
    }
}
