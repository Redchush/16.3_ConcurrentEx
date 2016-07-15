package root.locks.condition.test;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Cat implements Runnable{

   private final static Logger logger = Logger.getRootLogger();
   private String name;
   private Human human;

    public Cat(String name, Human human) {
        this.name = name;
        this.human = human;

    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                logger.debug("Cat " + name + " walking ");
                walk();
                logger.debug("Cat " + name + "ask for eating.");
                askFood();
            }
        } catch (InterruptedException e){
            logger.debug("Cat " + name + " going home.");
        }
    }
    private void walk() throws InterruptedException {
        int sleepTime = generateSleepTime();
        TimeUnit.MILLISECONDS.sleep(sleepTime);
    }
    private void askFood() throws InterruptedException {
        human.feedCat(this);
        logger.debug(this.name + " is fed");
    }

     private int generateSleepTime(){
        int walkingRate = 250;
        Random random = new Random();
        return random.nextInt(walkingRate);
    }

    public String getName() {
        return name;
    }
}
