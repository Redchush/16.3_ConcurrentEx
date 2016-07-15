package root.sychoronizers.semaphore;

import org.apache.log4j.Logger;

import java.util.concurrent.Semaphore;

public class BedManager {

    private Bed bed;
    private Semaphore semaphore;
    private final static Logger logger = Logger.getRootLogger();

    public BedManager(Bed bed, Semaphore semaphore) {
        this.bed = bed;
        this.semaphore = semaphore;
    }

    public void askPillow(Cat body) throws InterruptedException {
        boolean isFree = semaphore.tryAcquire();        //manager try to find the place for cat
        if (isFree) {                                  //if there is place
            try{
                bed.addBody(body);                      //add cat body to bed's pillow surface
                logger.debug("Now on bed " + bed.getSet() + " places for " + semaphore.availablePermits());
                body.enjoy();                           //enjoy the softness of pillow
            } finally {
                bed.removeBody(body);                   //move the body from pillow surface
                semaphore.release();                    //signal that place is free
            }
        } else logger.debug("No place for cat " + body.getName());
    }

    public void askPillow(Man body) throws InterruptedException {
        int allPillows = bed.getPillowCount();
        int nowAvailiable = semaphore.drainPermits();        //take all free places on pillows
        /*
        use this construction to be sure that in any case (exception or interruption)
        the precise pillows count will be freed
        (so in other case the total free places may changed because of contract release method)
         */
        try{
            int toAcquireMore = allPillows - nowAvailiable;    //get the count of other pillows
            bed.addBody(body);
            semaphore.acquire(toAcquireMore);                   //then pillows will be free, take it
            try{
                bed.getSet().clear();
                bed.addBody(body);
                logger.debug("Now on bed " + bed.getSet() + " places for " + semaphore.availablePermits());
                body.enjoy();
            } finally {
                bed.removeBody(body);                   //release the plase on pillows
                semaphore.release(toAcquireMore);       //signal all
            }
        } finally {
            semaphore.release(nowAvailiable);

        }
    }
}
