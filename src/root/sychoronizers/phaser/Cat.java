package root.sychoronizers.phaser;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class Cat implements Runnable {

    private final static Logger logger = Logger.getRootLogger();

    private String name;
    private String gangName;
    private Phaser phaser;


    public Cat(String name, String gangName, Phaser phaser) {
        this.name = name;
        this.phaser = phaser;
        this.gangName = gangName;
        phaser.register();

    }

    @Override
    public void run() {
        try{
            logger.debug("Cat " + this.name + " hear the call.");
            runToCall();                     //run random time
            comeToMeeting();                        //come To meeting
            fight();
        } catch (InterruptedException e){
            logger.debug("Cat " + this.name + " unexpectedly die.");
        } catch (BrokenBarrierException e) {
            logger.debug("Cat group  disintegrate.");
        }
    }




    private void comeToMeeting() throws BrokenBarrierException, InterruptedException {

        logger.debug("Cat " + name + " come to meeting. He wait " + phaser.getUnarrivedParties()
        + " cats more.");
        phaser.arriveAndAwaitAdvance();    // Cat wait for other cat to come

     }

    protected void runToCall() throws InterruptedException {
        int sleepTime = generateSleepTime();
        TimeUnit.MILLISECONDS.sleep(sleepTime);
    }
    private int generateSleepTime(){
        int walkingRate = 100;
        Random random = new Random();
        return random.nextInt(walkingRate);
    }


    private void fight() throws BrokenBarrierException, InterruptedException {
        logger.debug("Cat " + name + " come to fighting field.He wait "
           + phaser.getUnarrivedParties() + " cats more.");
        phaser.arriveAndAwaitAdvance();                                     // Cat wait for all cat's come to fighting field
        logger.debug("Cat " + name + " scratch the foe.");
        phaser.arriveAndDeregister();
    }

    public String getName() {
        return name;
    }

    public String getGangName() {
        return gangName;
    }

    protected Phaser getPhaser() {
        return phaser;
    }
}
