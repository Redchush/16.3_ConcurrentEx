package root.locks.reentalLock;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class Cat implements Runnable{

    private final static Logger logger = Logger.getRootLogger();
    private Human human;
    private String name;


    public Cat(String name, Human human) {
        this.name = name;
        this.human = human;

    }
    public String getName() {
        return name;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()) {  //check if food complete
                logger.debug("Cat " + name + " sleeping ");
                sleep();                     //sleep random time
                play();                      // eat food (reduce food volume)
            }
            logger.debug("Cat see empty food and sleep puffed away");
        } catch (InterruptedException e){
            logger.debug("Cat " + name + " going home.");
        }
    }

    private void play() throws InterruptedException {
        Lock lock = human.getLock();
        try{
            boolean isLocked = lock.tryLock(10, TimeUnit.MILLISECONDS);         //cat try to human payed attention on him
            if (isLocked) {                                                     //if human is free, play with him
                human.doPlaying(this); //cat compel human to play with him
            } else logger.debug("Human to busy!. Cat going puffing away.");   //if human is busy to long, back to
                                                                                //it's deals
        } finally {
            lock.unlock();                            //at any rate free human
        }
        human.doLogging(this);
    }

    private void sleep() throws InterruptedException {
        int sleepTime = generateSleepTime();
        TimeUnit.MILLISECONDS.sleep(sleepTime);
    }

    private int generateSleepTime(){
        int walkingRate = 100;
        Random random = new Random();
        return random.nextInt(walkingRate);
    }



}
