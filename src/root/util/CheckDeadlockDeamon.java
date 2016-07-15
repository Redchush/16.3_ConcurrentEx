package root.util;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * check if the deadlock occurred
 */
public class CheckDeadlockDeamon extends Thread {

    private final static Logger logger = Logger.getRootLogger();
    private String poolName;
    private int poolSize;
    private long timeToLive;

    public CheckDeadlockDeamon(String poolName, int poolSize, long timeToLive) {
        this.poolName = poolName;
        this.poolSize = poolSize;
        this.timeToLive = timeToLive;
        this.setDaemon(true);
        this.start();
    }

    @Override
    public void run() {
        // preparations for checking threads that need to be checking for deadlock
        long start = System.currentTimeMillis();
        List<Thread> threads = new ArrayList<>();
        final Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces(); // collect all threads
        for(  Map.Entry<Thread, StackTraceElement[]> entry : allStackTraces.entrySet()){
            Thread thread = entry.getKey();
            if (thread.getName().contains(poolName)){           //checking if thread belong to pool
                threads.add(thread);
            }
        }

        while (!Thread.interrupted()){          //all time
            long timeMark = System.currentTimeMillis();   //kill the threads if time to live is expired
            if (timeMark - start > timeToLive){
                for (Thread thread : threads){
                    thread.interrupt();
                }
            }

            int counter = 0;
            for (Thread thread : threads){            //check the threads for state
                boolean is = checkThread(thread);
                 if (is){
                     counter++;
                 } else {
                     break;
                 }
            }
            if (counter == poolSize){             //so if all threads in waiting state, that means that deadlock occured
               logger.debug("DEADLOCK OCCURRED!!!");
            }

        }
    }

    /**
     * Double check if the thread in waiting state
     * @param thread
     * @return is the thread in waiting state
     */
    private boolean checkThread(Thread thread){
        State state = thread.getState();
        if (state == State.WAITING){
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {

            }
            state = thread.getState();
            if (state == State.WAITING){
                return true;
            }
        }
        return false;
    }

}
