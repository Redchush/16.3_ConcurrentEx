package root.sychoronizers.semaphore;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Cat implements Runnable, Layable {

    private final static Logger logger = Logger.getRootLogger();

    private String name;
    private BedManager bedManager;

    public Cat(String name, BedManager bedManager) {
        this.name = name;
        this.bedManager = bedManager;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()) {
                logger.debug("Cat " + this.name + " going out.");
                walk();                     //walk random time
                logger.debug("Cat " + this.name + " tiered and want to lay on bed.");
                layOnBad();                   //ask BedManager for pillow
            }
        } catch (InterruptedException e){
            logger.debug("Cat " + this.name + " starving for death.");
        }
    }
    
    public String getName() {
        return name;
    }
    
    public void enjoy() throws InterruptedException {
        int rand = generateSleepTime();
        for (int i = 0; i < rand/20; i++) {
            logger.debug("Cat " + this.name + " enjoy " + i + " seconds and turns.");
        }
        Thread.sleep(rand/10);
    }
    
    

    private void layOnBad() throws InterruptedException {
        bedManager.askPillow(this);
    }

    public void walk() throws InterruptedException {
        int sleepTime = generateSleepTime();
        TimeUnit.MILLISECONDS.sleep(sleepTime);
    }
    private int generateSleepTime(){
        int walkingRate = 100;
        Random random = new Random();
        return random.nextInt(walkingRate);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                '}';
    }
}
