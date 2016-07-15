package root.locks.readWriteLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Food {

    private int foodWeight;
    private Lock lockRead;
    private Lock lockWrite;

    public Food(int foodWeight) {
        this.foodWeight = foodWeight;
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        lockRead = readWriteLock.readLock();
        lockWrite = readWriteLock.writeLock();
    }

    public int getFoodWeight() {
        return foodWeight;
    }

    public void setFoodWeight(int foodWeight) {
        this.foodWeight = foodWeight;
    }

    public Lock getLockRead() {
        return lockRead;
    }

     public Lock getLockWrite() {
        return lockWrite;
    }


}
