package root.sychoronizers.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {
   /*
   There are several cats and one dog. Cats tease the dog, and then dog's patience
   come to end, it become bark.
   So all other cats are afraid of teasing dog more.
    Patience is represented by CountDownLatch object, which on watch
    when dod become bark and all other cats will shadow itself.
       */

    public static void main(String[] args) {

        int patience = 4;
        int catsNum = 6;

        ExecutorService service = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(patience);
        Dog dog = new Dog("Bob" , latch);
        service.submit(dog);
        String catBaseName = "Cat-";
        for (int i = 0; i < catsNum; i++) {
            Cat cat = new Cat(catBaseName + i, latch);
            service.submit(cat);
        }
        service.shutdown();
    }
}
