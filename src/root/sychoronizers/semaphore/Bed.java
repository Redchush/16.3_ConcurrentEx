package root.sychoronizers.semaphore;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Bed {

    private CopyOnWriteArraySet<Layable> set;
    private int pillowCount;

    public Bed(int pillowCount) {
        this.pillowCount = pillowCount;
        this.set = new CopyOnWriteArraySet<Layable>();
    }

    public Set<Layable> getSet() {
        return set;
    }

    public void addBody(Layable body){
           set.add(body);
    }

    public boolean removeBody(Layable body){
        return set.remove(body);
    }

    public int getPillowCount() {
        return pillowCount;
    }
}
