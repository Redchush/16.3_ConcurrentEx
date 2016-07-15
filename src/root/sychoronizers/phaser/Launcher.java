package root.sychoronizers.phaser;

import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class Launcher {
    /*
      The cats organized fight. There is very cocky cat. He is the head of gang.
      Once he decided to gather his gang and go fighting.
      he
      phase0
      1) create his gang
      2) wait until they all go to meeting.
       (gathering, all cats wait his leader)
      phase1
      3) pronounce speech and and wait until all cat's gathered at fighting field.
      (gathering, all cats wait his leader)
      phase2
      4) command to fight
      ( all cats, but not leader, fight )
      5) celebrate the win
        */
    private final static Logger logger = Logger.getRootLogger();

    public static void main(String[] args) {

        int catsNum = 4;
        String gangName = "Sharp Claws";
        ExecutorService service = Executors.newSingleThreadExecutor();
        Phaser phaser = new Phaser();
        HeadCat headCat = new HeadCat("Bob", gangName, catsNum, phaser);
        service.submit(headCat);
        service.shutdown();
    }
}
