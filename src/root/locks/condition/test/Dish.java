package root.locks.condition.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Dish {

    private volatile boolean isWashed;
    private int id;
    private Lock lock;
    private Condition conditionWashed;
    private Condition conditionDirty;

    public Dish(int id) {
        this.id = id;
        isWashed = true;
        this.lock = new ReentrantLock();
        this.conditionWashed = lock.newCondition();
        this.conditionDirty= lock.newCondition();
    }

    public Lock getLock() {
        return lock;
    }

    public Condition getConditionWashed() {
        return conditionWashed;
    }

    public Condition getConditionDirty() {
        return conditionDirty;
    }

    public boolean isWashed() {
        return isWashed;
    }

    public void setWashed(boolean washed) {
        isWashed = washed;
    }

    public int getId() {
        return id;
    }
}
