import java.util.*;

public class LRU_Simple {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input: number of pages in reference string
        System.out.print("Enter number of pages: ");
        int n = sc.nextInt();
        int[] pages = new int[n];

        // Input: reference string (sequence of pages)
        System.out.println("Enter page reference string:");
        for (int i = 0; i < n; i++)
            pages[i] = sc.nextInt();

        // Input: number of frames available
        System.out.print("Enter number of frames: ");
        int m = sc.nextInt();

        // Use ArrayList to represent frames (dynamic)
        List<Integer> frames = new ArrayList<>();

        // Counters for hits and faults
        int pageFaults = 0, pageHits = 0;

        System.out.println("\n--- LRU Page Replacement ---");

        // Traverse each page in the reference string
        for (int page : pages) {
            // If page is already in memory → HIT
            if (frames.contains(page)) {
                // Remove it and add again to mark it as most recently used (MRU)
                frames.remove((Integer) page);
                frames.add(page);
                pageHits++;
                System.out.printf("Page %d -> HIT\tFrames: %s%n", page, frames);
            } 
            // If page not in memory → FAULT
            else {
                pageFaults++;

                // If memory is full, remove least recently used page (first in list)
                if (frames.size() == m)
                    frames.remove(0);

                // Add new page to the end (most recently used position)
                frames.add(page);
                System.out.printf("Page %d -> FAULT\tFrames: %s%n", page, frames);
            }
        }

        // Final output
        System.out.println("\nTotal Page Hits   = " + pageHits);
        System.out.println("Total Page Faults = " + pageFaults);

        sc.close();
    }
}



// Enter number of pages in reference string: 12
// Enter page reference string:
// 1 2 3 4 1 2 5 1 2 3 4 5
// Enter number of frames: 3

