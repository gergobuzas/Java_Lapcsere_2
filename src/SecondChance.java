import java.util.ArrayList;

public class SecondChance {
    private int pageFaults = 0;
    private Frame A = new Frame("A");
    private Frame B = new Frame("B");
    private Frame C = new Frame("C");
    private ArrayList<Frame> frames = new ArrayList<Frame>();

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
        System.out.print("*");
        pageFaults++;
    }

    private boolean swapFrames(Page p){
        //if all frames are full, find a page to evict --- this is the second chance part
        //Sort the pages by age, descending
        //Go through the frames until you find a page that has not been referenced and is not locked
        frames.sort((f1, f2) -> f2.getPage().getAge() - f1.getPage().getAge());
        for (Frame frame : frames) {
            if (!frame.getPage().getReferenced() && !frame.getPage().getLock()) {
                frame.setPage(p);
                System.out.printf("%s",frame.getName());
                pageFaults++;
                return true;
            }
            //If the page has been referenced, set referenced to false and increment its age
            frame.getPage().setReferenced(false);
        }
        return false;
    }

    private boolean tryReferencingPage(Page p){
        //if page is already in a frame, set referenced to true
        for (Frame frame : frames) {
            if (frame.getPage() != null){
                if (frame.getPage().getValue() == p.getValue()) {
                    p.setReferenced(true);
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


    public int getPageFaults() {
        return pageFaults;
    }


}
