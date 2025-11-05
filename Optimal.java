import java.util.*;

public class Optimal_PageReplacement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of frames
        System.out.print("Enter number of frames: ");
        int frames = sc.nextInt();

        // Input number of pages
        System.out.print("Enter number of pages: ");
        int n = sc.nextInt();
        int[] pages = new int[n];

        // Input reference string
        System.out.println("Enter the page reference string:");
        for (int i = 0; i < n; i++) {
            pages[i] = sc.nextInt();
        }

        // List to represent pages in memory
        List<Integer> memory = new ArrayList<>();
        int pageFaults = 0;
        int pageHits = 0;

        System.out.println("\n--- Optimal Page Replacement Process ---");

        // Traverse each page
        for (int i = 0; i < n; i++) {
            int currentPage = pages[i];

            // If page is already present — HIT
            if (memory.contains(currentPage)) {
                pageHits++;
                System.out.println("Step " + (i + 1) + " -> HIT   | Frames: " + memory);
                continue;
            }

            // Page Fault occurs
            pageFaults++;

            // If frames are not full yet
            if (memory.size() < frames) {
                memory.add(currentPage);
            } else {
                // Apply OPTIMAL replacement
                int farthestIndex = -1;
                int pageToReplace = -1;

                // Check future use of each page in memory
                for (int page : memory) {
                    int nextUse = Integer.MAX_VALUE;

                    for (int j = i + 1; j < n; j++) {
                        if (pages[j] == page) {
                            nextUse = j;
                            break;
                        }
                    }

                    // If a page is never used again → replace immediately
                    if (nextUse == Integer.MAX_VALUE) {
                        pageToReplace = page;
                        break;
                    }

                    // Otherwise choose the page used farthest in future
                    if (nextUse > farthestIndex) {
                        farthestIndex = nextUse;
                        pageToReplace = page;
                    }
                }

                // Perform the replacement
                memory.remove(Integer.valueOf(pageToReplace));
                memory.add(currentPage);
            }

            System.out.println("Step " + (i + 1) + " -> FAULT | Frames: " + memory);
        }

        // Final output
        System.out.println("\nTotal Page Hits   : " + pageHits);
        System.out.println("Total Page Faults : " + pageFaults);
        sc.close();
    }
}


// Enter number of frames: 3
// Enter number of pages: 12
// Enter the page reference string:
// 1 2 3 4 1 2 5 1 2 3 4 5