package root.sychoronizers.cyclicBarrier;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class Cat implements Runnable {

    private final static Logger logger = Logger.getRootLogger();

    private String name;
    private CyclicBarrier barrier;

    public Cat(String name, CyclicBarrier barrier) {
        this.name = name;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try{
            sleep();                               //sleep random time
            comeToMeeting();                        //come To meeting
            runToFightingField();                   //run random time
            fight();
        } catch (InterruptedException e){
            logger.debug("Cat " + this.name + " unexpectedly die.");
        } catch (BrokenBarrierException e) {
            logger.debug("Cat group  disintegrate.");
        }
    }

    private void sleep() throws InterruptedException {
        int sleepTime = generateSleepTime();
        logger.debug("Cat " + name + " sleeping " + sleepTime);
        TimeUnit.MILLISECONDS.sleep(sleepTime);
    }

    private int generateSleepTime(){
        int walkingRate = 100;
        Random random = new Random();
        return random.nextInt(walkingRate);
    }

    private void comeToMeeting() throws BrokenBarrierException, InterruptedException {
        logger.debug("Cat " + name + " come to meeting. He wait " + barrier.getNumberWaiting()
        + " cats more.");
        barrier.await();    // Cat wait for other cat to come
     }

    private void runToFightingField() throws InterruptedException {
        int sleepTime = generateSleepTime();
        TimeUnit.MILLISECONDS.sleep(sleepTime);
    }

    private void fight() throws BrokenBarrierException, InterruptedException {
        logger.debug("Cat " + name + " come to fighting field.He wait " + barrier.getNumberWaiting()
                + " cats more.");
        barrier.await();                                     // Cat wait for all cat's come to fighting field
        logger.debug("Cat " + name + " scratch the foe.");
    }

}
