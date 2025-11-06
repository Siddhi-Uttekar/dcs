import java.util.*;

class Process {
    int index;
    int id;
    boolean isActive;

    public Process(int index, int id) {
        this.index = index;
        this.id = id;
        this.isActive = true;
    }
}

public class RingElection {
    static Scanner sc = new Scanner(System.in);
    static List<Process> ring = new ArrayList<>();

    public static void main(String[] args) {
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter ID for process " + i + ": ");
            int id = sc.nextInt();
            ring.add(new Process(i, id));
        }

        // Simulate coordinator failure: deactivate last process
        ring.get(n - 1).isActive = false;
        System.out.println("Process " + ring.get(n - 1).id + " is set as inactive (coordinator down)");

        while (true) {
            System.out.println("\nMenu:\n1. Start Election\n2. Exit");
            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter initiator process ID: ");
                int initiatorId = sc.nextInt();
                startElection(initiatorId);
            } else {
                break;
            }
        }
    }

    static void startElection(int initiatorId) {
        int n = ring.size();
        int initiatorIndex = -1;

        // Find initiator index
        for (int i = 0; i < n; i++) {
            if (ring.get(i).id == initiatorId && ring.get(i).isActive) {
                initiatorIndex = i;
                break;
            }
        }

        if (initiatorIndex == -1) {
            System.out.println("Invalid initiator or process is inactive.");
            return;
        }

        System.out.println("Election initiated by Process " + initiatorId);

        List<Integer> electionIds = new ArrayList<>();
        int currentIndex = initiatorIndex;

        do {
            Process p = ring.get(currentIndex);
            if (p.isActive) {
                System.out.println("Process " + p.id + " received election message.");
                electionIds.add(p.id);
            }
            currentIndex = (currentIndex + 1) % n;
        } while (currentIndex != initiatorIndex);

        int newCoordinatorId = Collections.max(electionIds);
        System.out.println("Election complete. New coordinator is Process " + newCoordinatorId);

        // Announce coordinator
        currentIndex = initiatorIndex;
        do {
            Process p = ring.get(currentIndex);
            if (p.isActive) {
                System.out.println("Process " + p.id + " notified: New coordinator is Process " + newCoordinatorId);
            }
            currentIndex = (currentIndex + 1) % n;
        } while (currentIndex != initiatorIndex);
    }
}