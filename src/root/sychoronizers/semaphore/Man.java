package root.sychoronizers.semaphore;

import org.apache.log4j.Logger;

import java.util.Random;

public class Man implements Runnable, Layable{

    private final static Logger logger = Logger.getRootLogger();
    private BedManager manager;

    public Man(BedManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()) {
                logger.debug("Man going out");
                walk();
                                 //walk random time
                logger.debug("Man tiered and want to lay on bedManager.");
                layOnBad();
            }
        } catch (InterruptedException e){
            logger.debug("Man starving for death.");
        }

    }

    public void walk() throws InterruptedException {
        Thread.sleep(generateSleepTime());
    }

    private void layOnBad() throws InterruptedException {
        manager.askPillow(this);
    }

    public void enjoy() throws InterruptedException {
        int rand = generateSleepTime();
        for (int i = 0; i < rand/10; i++) {
            logger.debug("Man enjoing ." + i + " seconds ");
        }
        Thread.sleep(rand/5);
    }

    private int generateSleepTime(){
        int walkingRate = 200;
        Random random = new Random();
        return random.nextInt(walkingRate);
    }
}
