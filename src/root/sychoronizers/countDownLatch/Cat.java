package root.sychoronizers.countDownLatch;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class Cat implements Runnable {

    private final static Logger logger = Logger.getRootLogger();

    private String name;
    private CountDownLatch patienceLatch;

    public Cat(String name, CountDownLatch latch) {
        this.name = name;
        this.patienceLatch = latch;             //patience of the dog
    }


    @Override
    public void run() {
        try{
            sleep();                               //sleep random time
            boolean isDogAngry = teaseTheDog();    // cat tease the dog
            if (isDogAngry) {                       // if latch come to end
                logger.debug("Cat " + this.name + " scared and jump away");
            }  else {
                logger.debug("Cat " + this.name + "done his dirty deal and go home");
            }
           } catch (InterruptedException e){
            logger.debug("Cat " + this.name + " unexpectedly die.");
        }
    }

    private boolean teaseTheDog() throws InterruptedException {
        if (patienceLatch.getCount() == 0){
            return  true;                      //return if dog's patience come to end
        } else {
            logger.debug("Mew-mew-mew,- " + this.name + " said.");
            patienceLatch.countDown();
            return false;
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
}
