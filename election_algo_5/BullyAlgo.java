// 🧠 Concept: Bully Election Algorithm

// In a distributed system, one process is always the coordinator (leader).
// If the coordinator fails, a new one must be elected.

// 👉 Key Idea:

// The process with the highest process ID (PID) becomes the new coordinator.

// When a process detects the coordinator has failed, it starts an election.

// It sends messages to all processes with higher IDs.

// The one with the highest ID that’s still alive becomes the new coordinator.

import java.util.*;
public class BullyAlgo {
    int[] processes;
    int coordinator;

    public void election(int n) {
        System.out.println("\nCoordinator has crashed! Starting election...");

        int newCoord = -1;
        for (int i = n - 1; i >= 0; i--) {
            if (processes[i] == 1) { // active process
                newCoord = i + 1;
                break;
            }
        }

        if (newCoord != -1) {
            coordinator = newCoord;
            System.out.println("New Coordinator is Process " + coordinator);
        } else {
            System.out.println("All processes are crashed! No coordinator available.");
        }
    }

    public void runBully() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();
        processes = new int[n];
        Arrays.fill(processes, 1); // all active initially
        coordinator = n;

        while (true) {
            System.out.println("\n1. Crash a Process");
            System.out.println("2. Recover a Process");
            System.out.println("3. Display Coordinator");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.print("Enter process number to crash: ");
                    int cp = sc.nextInt();
                    if (cp < 1 || cp > n)
                        System.out.println("Invalid process number.");
                    else if (processes[cp - 1] == 1 && coordinator != cp) {
                        processes[cp - 1] = 0;
                        System.out.println("Process " + cp + " has been crashed.");
                    } else if (processes[cp - 1] == 1 && coordinator == cp) {
                        processes[cp - 1] = 0;
                        election(n);
                    } else
                        System.out.println("Process " + cp + " is already crashed.");
                    break;

                case 2:
                    System.out.print("Enter process number to recover: ");
                    int rp = sc.nextInt();
                    if (rp < 1 || rp > n) {
                        System.out.println("Invalid process number.");
                    } else if (processes[rp - 1] == 0) {
                        processes[rp - 1] = 1;
                        if (rp > coordinator) {
                            coordinator = rp;
                            System.out.println("Process " + rp + " recovered and becomes new Coordinator.");
                        } else
                            System.out.println("Process " + rp + " has recovered.");
                    } else
                        System.out.println("Process " + rp + " is already active.");
                    break;

                case 3:
                    System.out.println("Current Coordinator is Process " + coordinator);
                    break;

                case 4:
                    System.out.println("Exiting program.");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public static void main(String[] args) {
        BullyAlgo algo = new BullyAlgo();
        algo.runBully();
    }
}
