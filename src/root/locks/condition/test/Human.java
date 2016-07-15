package root.locks.condition.test;

import org.apache.log4j.Logger;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Human {

    private final static Logger logger = Logger.getRootLogger();
    private Dish dish;

    public Human(Dish dishes) {
        this.dish = dishes;
    }

    public void feedCat(Cat cat) throws InterruptedException {
        logger.debug("Human start feeding the cat " + cat.getName());
        Dish dish = takeDish();
        washDish(dish);
    }

    private Dish takeDish() throws InterruptedException {
        logger.debug("Human take the dish");
        Lock lock = dish.getLock();
        Condition condition = dish.getCondition();
        lock.lock();
        try{
            while (dish.isWashed()){
                 condition.await();
            }
            dish.setWashed(false);
        } finally {
            lock.unlock();
        }
        return dish;
    }

    private void washDish(Dish dish) throws InterruptedException {
        logger.debug("Human wash the dish.");
        Lock lock = dish.getLock();
        Condition condition = dish.getCondition();
        lock.lock();
        try{
            Thread.sleep(10);
            dish.setWashed(true);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
