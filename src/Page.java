public class Page {
    private int value;
    private int age = 0; //for FIFO
    private boolean referenced = false; //for SecondChance
    private boolean lock = true; //for SecondChance

    public Page(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void incrementAge() {
        age++;
        if (age == 4){
            lock = false;
        }
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setReferenced(boolean referenced) {
        this.referenced = referenced;
    }

    public boolean getReferenced() {
        return referenced;
    }

    public boolean getLock() {
        return lock;
    }
}
