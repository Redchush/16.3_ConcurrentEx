package tip.fork;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Fork {

    private int id;  // identificator to differ the forks
    private Lock lock;
    private Condition condition;
    private volatile boolean taken;  //is the fork is taken by philosopher

    public Fork(int id) {
        this.id = id;
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
        taken = false;
    }

    public Fork() {}

    public Lock getLock(){
        return lock;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public boolean isTaken() {
        return taken;
    }
}
