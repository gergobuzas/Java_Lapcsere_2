import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static int runTime = 0;
    private static ArrayList<Page> pages = new ArrayList<Page>();
    private static SecondChance secondChance = new SecondChance();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String[] input = scanner.nextLine().split(",");
            for (String s : input) {
                pages.add(new Page(Math.abs(Integer.parseInt(s) ) ) );
                runTime++;
            }
        }

        //Run the algorithm
        for (Page page: pages) {
            secondChance.tick(page);
        }
        System.out.printf("\n%d", secondChance.getPageFaults());
    }
}