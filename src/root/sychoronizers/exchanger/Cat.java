package root.sychoronizers.exchanger;

import org.apache.log4j.Logger;


import java.util.Arrays;
import java.util.Random;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class Cat<V> implements Runnable{

    private final static Logger logger = Logger.getRootLogger();

    private String name;
    private Exchanger<Prey> exchanger;

    public Cat(String name, Exchanger<Prey>  exchanger) {
        this.name = name;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try{
            sleep();                               //sleep random time
            Prey[] prey = hunt();                   //procure food
            logger.debug("Cat " + name + " come to meeting. He want exchange his " +
                    Arrays.toString(prey));
            Prey[] result = exchangeFood(prey);                        //come to exchanging place and exchange food
            logger.debug("Cat " + name + " exchange his preys. Now he has " +
                    Arrays.toString(result));
         } catch (InterruptedException e){
            logger.debug("Cat " + this.name + " unexpectedly die.");
        }
    }

    private void sleep() throws InterruptedException {
        int sleepTime = generateSleepTime();
        logger.debug("Cat " + name + " sleeping " + sleepTime);
        TimeUnit.MILLISECONDS.sleep(sleepTime);
    }

    private int generateSleepTime(){
        int walkingRate = 120;
        Random random = new Random();
        return random.nextInt(walkingRate);
    }

    private Prey[] hunt() {
        Prey[] result = new Prey[2];               //create array with two pieces of pray
        int ordinalForFood = generateSleepTime()/20;  //produce random ordinal for Preys enum
        result[1] = result[0] = Prey.values()[ordinalForFood];
        return result;
    }

    private Prey[] exchangeFood(Prey[] preys) throws InterruptedException {
        Prey exchangeResult = exchanger.exchange(preys[1]);       //exchange result
        preys[1] = exchangeResult;
        return preys;
    }
}
