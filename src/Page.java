public class Page {
    private int value;
    private int age; //for FIFO
    private int lockTimeLeft = 4;
    private boolean referenced = false; //for SecondChance
    private boolean lock = true; //for SecondChance

    public Page(int value, int created) {
        this.value = value;
        age = created;
    }

    public int getValue() {
        return value;
    }

    public void tickLock() {
        lockTimeLeft--;
        if (lockTimeLeft <= 0)
            lock = false;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setReferenced(boolean referenced) {
        this.referenced = referenced;
        if (referenced){
            lock = false;
            lockTimeLeft = 0;
        }
    }

    public boolean getReferenced() {
        return referenced;
    }

    public boolean getLock() {
        return lock;
    }


    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public void setLockTimeLeft(int lockTimeLeft) {
        this.lockTimeLeft = lockTimeLeft;
    }

    public int getLockTimeLeft() {
        return lockTimeLeft;
    }

}
