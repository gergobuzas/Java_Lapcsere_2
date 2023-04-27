import java.util.LinkedList;

public class SecondChance {
    private int pageFaults = 0;
    private Frame A = new Frame("A");
    private Frame B = new Frame("B");
    private Frame C = new Frame("C");
    private LinkedList<Frame> frames = new LinkedList<Frame>();

    public SecondChance() {
        A.setPage(null);
        B.setPage(null);
        C.setPage(null);
        frames.add(A);
        frames.add(B);
        frames.add(C);
    }

    public void tick(Page page) {
        incrementAges();
        addPage(page);
        //sort();
    }

    private void sort() {
        frames.sort((f1, f2) -> f2.getPage().getAge() - f1.getPage().getAge());
    }

    private void incrementAges() {
        for (Frame frame : frames) {
            frame.incrementAge();
        }
    }

    public void addPage(Page p){
        if(tryReferencingPage(p))
            return;
        if(tryPuttingInEmptyFrame(p))
            return;
        if(swapFrames(p))
            return;
        noSpaceLeft();
    }

    private boolean swapFrames(Page p){
        //if all frames are full, find a page to evict --- this is the second chance part
        //Sort the pages by age, descending
        //Go through the frames until you find a page that has not been referenced and is not locked
        //frames.sort((f1, f2) -> f2.getPage().getAge() - f1.getPage().getAge());
        int lockCounter = 0;
        for (Frame frame : frames){
            if (frame.getPage().getLock())
                lockCounter++;
        }
        if (lockCounter == 3){
            return false;
        }
        boolean found = false;
        int i = 0;
        while (!found) {
                if (!frames.get(i).getPage().getReferenced() && !frames.get(i).getPage().getLock()) {
                    frames.get(i).setPage(p);
                    Frame temp = frames.get(i);
                    frames.removeFirst();
                    frames.add(temp);
                    System.out.printf("%s",temp.getName());
                    pageFaults++;
                    return true;
                } else if (frames.get(i).getPage().getReferenced()) {
                    frames.get(i).getPage().setReferenced(false);
                    //TODO FIFO part, for the second chance part
                    Frame temp = frames.get(i);
                    frames.remove(i);
                    frames.add(temp);
                } else if (frames.get(i).getPage().getLock()) {
                    //TODO FIFO part, for the second chance part
                    //Search the index of the next page that is not locked
                    int j = i;
                    while (frames.get(j).getPage().getLock()) {
                        j++;
                    }

                    Frame temp = frames.get(j);
                    int oldIdx = i + j;
                    frames.remove(j);
                    temp.setPage(p);
                    frames.add(temp);
                    System.out.printf("%s",temp.getName());
                    pageFaults++;
                    return true;
                }
            }
            return true;
        }


    private boolean tryReferencingPage(Page p){
        //if page is already in a frame, set referenced to true
        for (int i = 0; i < 3; i++) {
            if (frames.get(i).getPage() != null){
                if (frames.get(i).getPage().getValue() == p.getValue()) {
                    p.setReferenced(true);
                    p.setAge(frames.get(i).getPage().getAge());
                    p.setLock(frames.get(i).getPage().getLock());
                    p.setLockTimeLeft(frames.get(i).getPage().getLockTimeLeft());
                    frames.get(i).setPage(p);
                    System.out.print("-");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean tryPuttingInEmptyFrame(Page p){
        for (Frame frame : frames) {
            if (frame.getPage() == null) {
                frame.setPage(p);
                pageFaults++;
                System.out.printf("%s",frame.getName());
                return true;
            }
        }
        return false;
    }

    private void noSpaceLeft(){
        int count = 0;
        for (Frame frame : frames) {
            if (frame.getPage().getLock()) {
                count++;
            }
        }
        if (count == 3){
            System.out.print("*");
            pageFaults++;
        }
    }

    public int getPageFaults() {
        return pageFaults;
    }


}
