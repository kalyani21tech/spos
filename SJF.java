import java.util.Scanner;

public class SJF_Preemptive {

    // Simple container for process data
    static class Process {
        int id;           // process id (1..n)
        int at;           // arrival time
        int bt;           // original burst time
        int remainingBt;  // remaining burst time (changes during simulation)
        int ct;           // completion time
        int tat;          // turn around time
        int wt;           // waiting time
        int rt;           // response time (first time CPU assigned - arrival)
        boolean isCompleted = false;


        Process(int id, int at, int bt) {
            this.id = id;
            this.at = at;
            this.bt = bt;
            this.remainingBt = bt;
            this.rt = -1; // -1 indicates not started yet
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process[] p = new Process[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Arrival time of P" + (i + 1) + ": ");
            int at = sc.nextInt();
            System.out.print("Enter Burst time of P" + (i + 1) + ": ");
            int bt = sc.nextInt();
            p[i] = new Process(i + 1, at, bt);
        }

        int time = 0;    // cpu time for process   
        int completed = 0;  // no of processes completed 
        int sumTAT = 0, sumWT = 0, sumRT = 0;

        // Continue until all processes are completed
        while (completed != n) {
            int idx = -1;
            int minRemaining = Integer.MAX_VALUE;

            // pick the arrived process with the smallest remaining time
            for (int i = 0; i < n; i++) {
                if (!p[i].isCompleted && p[i].at <= time) {
                    if (p[i].remainingBt < minRemaining) {
                        minRemaining = p[i].remainingBt;
                        idx = i;
                    } else if (p[i].remainingBt == minRemaining) {
                        // tie-breaker: earlier arrival time, then smaller id
                        if (idx == -1
                                || p[i].at < p[idx].at
                                || (p[i].at == p[idx].at && p[i].id < p[idx].id)) {
                            idx = i;
                        }
                    }
                }
            }

            if (idx != -1) {
                // if first time the process gets CPU, record response time
                if (p[idx].rt == -1) {
                    p[idx].rt = time - p[idx].at;
                }

                // execute for one unit
                p[idx].remainingBt--;
                time++;

                // if finished, set completion and compute times
                if (p[idx].remainingBt == 0) {
                    p[idx].ct = time;
                    p[idx].tat = p[idx].ct - p[idx].at;
                    p[idx].wt = p[idx].tat - p[idx].bt;
                    p[idx].isCompleted = true;

                    sumTAT += p[idx].tat;
                    sumWT += p[idx].wt;
                    sumRT += p[idx].rt;

                    completed++;
                }
            } else {
                // no process has arrived yet at current time -> idle CPU
                time++;
            }
        }

        // Print results
        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT\tRT");
        for (int i = 0; i < n; i++) {
            System.out.printf("P%d\t%d\t%d\t%d\t%d\t%d\t%d\n",
                    p[i].id, p[i].at, p[i].bt, p[i].ct, p[i].tat, p[i].wt, p[i].rt);
        }

        System.out.printf("\nAverage TAT: %.2f\n", sumTAT / (float) n);
        System.out.printf("Average WT: %.2f\n", sumWT / (float) n);
        System.out.printf("Average RT: %.2f\n", sumRT / (float) n);

        sc.close();
    }
}
