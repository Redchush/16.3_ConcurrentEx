package root.sychoronizers.phaser;

import org.apache.log4j.Logger;

import java.util.concurrent.Phaser;

public class HeadCat extends Cat implements Runnable {

    private final static Logger logger = Logger.getRootLogger();
    int gangCount;

    public HeadCat(String name, String gangName, int gagnCount, Phaser phaser) {
        super(name, gangName, phaser);
        this.gangCount = gagnCount;
    }

    @Override
    public void run() {

        try {
           logger.debug("Cat " + getName() + " gather the gang " + getGangName());
            gatherGang();
            pronounseTheSpeech();
            runToCall();
            conductNegotations();
            getPhaser().arriveAndAwaitAdvance();
            logger.debug(getName() + " celebrate the win");
            getPhaser().arriveAndDeregister();
        } catch (InterruptedException e) {
              logger.debug("Cat unexpectedly dying");
        }

    }

    private void conductNegotations() {
        logger.debug(getName() + " cast abusive words toward another head cat and command to fight");
         getPhaser().arrive();
    }

    private void gatherGang(){
        String catBaseName = "Cat-";
        for (int i = 0; i < gangCount; i++) {        //collect the gang
            Cat cat = new Cat(catBaseName + i, getGangName(), getPhaser());
            new Thread(cat).start();
        }
    }

    private void pronounseTheSpeech(){
        while (getPhaser().getUnarrivedParties() != 1){} //wait until other cat's gather
        logger.debug(getName() + " pronounce inspired speech.");
         getPhaser().arrive();                            //notify other cats to they began to run
    }
}
