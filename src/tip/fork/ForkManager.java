package tip.fork;

import tip.philosopher.Philosopher;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ForkManager {

    private final List<Fork> forks;

    public ForkManager(int forksSize) {
        List<Fork> list = new ArrayList<>(forksSize);
        for (int i = 0; i < forksSize ; i++) {
            Fork fork = new Fork(i);
            list.add(fork);
        }
        forks = Collections.unmodifiableList(list);
    }

    /**
     * @param philosopher : philosopher who want to take a fork
     * @param isRight : whether the philosopher drop the right or left fork
     * @throws InterruptedException
     */

    public int takeFork(Philosopher philosopher, boolean isRight) throws InterruptedException {
        int forkId = isRight ? getRightForkByPhilosopher(philosopher)
                             : getLeftForkByPhilosopher(philosopher);

        Fork fork = forks.get(forkId);
        Lock lock = fork.getLock();
        Condition condition =  fork.getCondition();
        try{
            lock.lock();                    //block the fork
            while (fork.isTaken()){         //if fork is taken -> wait for it setted free
                condition.await();
            }
            fork.setTaken(true);              //change the state of fork marking at as taken
          } finally {
            lock.unlock();                    //leave the fork mutex
        }

        return forkId;
    }

    /**
     *
     * @param philosopher  : philosopher who want to take a fork
     * @param isRight : whether the philosopher drop the right or left fork
     * @throws InterruptedException
     */
    public void dropFork (Philosopher philosopher, boolean isRight) throws InterruptedException {
        int forkId = isRight ? getRightForkByPhilosopher(philosopher) //define the fork that must be taken
                             : getLeftForkByPhilosopher(philosopher);
        Fork fork = forks.get(forkId);                   //get fork by id from storage

        Lock lock = fork.getLock();
        try {
            fork.getLock().lock();
            fork.setTaken(false);                         //mark the fork as taken
            fork.getCondition().signalAll();                //signal all other philosophers that fork is free now
        } finally {
            lock.unlock();                             //leave the fork mutex
        }
    }

    /**
     *
     * @param philosopher
     * @return the appropriate right forkId for this philosopher
     * @throws InterruptedException
     */
    private int getRightForkByPhilosopher(Philosopher philosopher) throws InterruptedException {
        return philosopher.getId();  //right fork id is equals to philosopher id
    }

    /**
     *
     * @param phiosopher
     * @return the appropriate left forkId for this philosopher
     * @throws InterruptedException
     */
    private int getLeftForkByPhilosopher(Philosopher phiosopher) throws InterruptedException{
        int id = phiosopher.getId();
        int forkId = (id == 0)? forks.size() - 1 : (id - 1);  // left fork id is equals philosopher id - 1, so to the
                                                                // first  philosopher (id = 0) grab last fork to make a circle
        return forkId;
    }


}
