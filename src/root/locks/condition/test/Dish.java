package root.locks.condition.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Dish {


    private volatile boolean isWashed;
    private Lock lock;
    private Condition condition;

    public Dish() {
        isWashed = false;
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
    }

    public Lock getLock() {
        return lock;
    }

    public Condition getCondition() {
        return condition;
    }

    public boolean isWashed() {
        return isWashed;
    }

    public void setWashed(boolean washed) {
        isWashed = washed;
    }
}
