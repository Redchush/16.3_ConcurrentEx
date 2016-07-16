package root.locks.condition;

import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Human {


    private final static Logger logger = Logger.getRootLogger();
    private BlockingQueue<Dish> dishes;

    public Human(BlockingQueue<Dish> dishes) {
        this.dishes = dishes;
    }

    public void feedCat(Cat cat) throws InterruptedException {
        logger.debug("Human start feeding the cat " + cat.getName());
        Dish dish = takeDish();
        new Washmashine(dish).start();              //launch the washmashine
    }

    private Dish takeDish() throws InterruptedException {
        Dish dish = dishes.take();                  //take the dish from shelf
        Lock lock = dish.getLock();                 //lock this dish
        Condition conditionDirty = dish.getConditionDirty();       //condition weather the dish is dirty
        lock.lock();
        try{
            logger.debug("Human wait until the dish " + dish.getId() + " being clean");
            while (!dish.isWashed()){           //wait until dish will be clean
                conditionDirty.await();
            }
            logger.debug("Human put the food on the dish " + dish.getId());
            dish.setWashed(false);              //after feeding the cat , dish become dirty
        } finally {
            lock.unlock();
            dishes.put(dish);
        }
        return dish;
    }

    class Washmashine extends Thread{

        private volatile Dish dish;

        public Washmashine(Dish dish) {
            this.dish = dish;
        }

        @Override
        public void run() {
            Lock lock = dish.getLock();
            Condition condition =  dish.getConditionDirty();
            try{
                lock.lock();
                Thread.sleep(200);                   //time to washmashine clean the dish
                dish.setWashed(true);
                logger.debug("Washmashime washed the " + dish.getId());
                condition.signalAll();              //notify the waiting thread
            } catch (InterruptedException e) {
                logger.debug("Washmashime brocken");
            } finally {
                lock.unlock();
            }
        }
    }
}
