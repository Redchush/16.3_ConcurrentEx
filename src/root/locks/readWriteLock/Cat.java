package root.locks.readWriteLock;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class Cat implements Runnable{

    private final static Logger logger = Logger.getRootLogger();
    private Food food;
    private String name;

    public Cat(String name, Food food) {
        this.name = name;
        this.food = food;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        try{
            while (checkFood() > 0){  //check if food complete
                logger.debug("Cat " + name + " walking ");
                walk();                     //sleep random time
                eat();                      // eat food (reduce food volume)
            }
            logger.debug("Cat see no food and walk puffed away");
        } catch (InterruptedException e){
            logger.debug("Cat " + name + " going home.");
        }
    }

    private int checkFood() {
        Lock lockRead = food.getLockRead();
        lockRead.lock();
        try{
            return food.getFoodWeight();
        } finally {
            lockRead.unlock();
        }
    }

    private void walk() throws InterruptedException {
        int sleepTime = generateSleepTime();
        TimeUnit.MILLISECONDS.sleep(sleepTime);
    }
    private void eat() throws InterruptedException {
       logger.debug("Cat " + name + " wait for eating.");

       Lock lockWrite = food.getLockWrite();
       lockWrite.lock();   //cat block the operation on food
        try{
            int weight = food.getFoodWeight();
            logger.debug("Cat " + name + " see " + weight + " gramm of food ");
            int volume = generateEatingVolume();
            int remain = weight - volume < 0 ? weight : volume;
            if (remain == 0) return; //if there no food --> back to another cat's deals
            food.setFoodWeight(weight - remain);
            logger.debug("Cat " + name + " eat the " + remain + " gram. Remain " + (weight - remain));
        } finally {
            lockWrite.unlock(); //release food
        }
    }

    private int generateSleepTime(){
        int walkingRate = 100;
        Random random = new Random();
        return random.nextInt(walkingRate);
    }
    private int generateEatingVolume(){
        int eatingRate = 5;
        Random random = new Random();
        return random.nextInt(5) + 1;
    }


}
