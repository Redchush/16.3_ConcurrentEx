package root.sychoronizers.countDownLatch;

import org.apache.log4j.Logger;

import java.util.concurrent.CountDownLatch;

public class Dog implements Runnable {

    private final static Logger logger = Logger.getRootLogger();
    private String name;
    private CountDownLatch dogPatience;

    public Dog(String name, CountDownLatch dogPatience) {
        this.name = name;
        this.dogPatience = dogPatience;
    }

    @Override
    public void run() {
        try {
            dogPatience.await();
            bark();
        } catch (InterruptedException e) {
            logger.debug("Dog unexpectedly die");
        }
    }

    private void bark() {
        logger.debug("woof - woof - woof!!!!" + this.name + " said.");
    }
}
