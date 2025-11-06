import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class TerminationDetection {

    private static ConcurrentLinkedQueue<Message> messageQueue = new ConcurrentLinkedQueue<>();
    private static AtomicBoolean terminated = new AtomicBoolean(false);
    private static Random random = new Random();
    private static final int processCount = 5;

    // Message class
    static class Message {
        int senderId;
        int receiverId;
        String type;

        Message(int senderId, int receiverId, String type) {
            this.senderId = senderId;
            this.receiverId = receiverId;
            this.type = type;
        }
    }

    // Process class
    static class Process extends Thread {
        private int id;
        private boolean isActive;
        private AtomicInteger deficit;
        private List<Integer> children;
        private boolean isInitiator;

        public Process(int id, boolean isInitiator) {
            this.id = id;
            this.isActive = true;
            this.deficit = new AtomicInteger(0);
            this.children = new ArrayList<>();
            this.isInitiator = isInitiator;
        }

        @Override
        public void run() {
            if (isInitiator) {
                sendMessages();
            }

            while (!terminated.get()) {
                if (isActive) {
                    try {
                        Thread.sleep(random.nextInt(500));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    // Randomly decide whether to become passive or send messages
                    if (random.nextDouble() < 0.3) {
                        becomePassive();
                    } else {
                        sendMessages();
                    }
                } else {
                    processMessages();
                }

                // Initiator checks for global termination
                if (isInitiator && checkTermination()) {
                    terminated.set(true);
                    System.out.println("\n✅ Initiator (Process " + id + ") detected global termination — all processes are passive and no messages are in transit.\n");
                }
            }
        }

        private void sendMessages() {
            int numMessages = random.nextInt(3); // Send 0-2 messages randomly
            for (int i = 0; i < numMessages; i++) {
                int receiverId = random.nextInt(processCount);
                if (receiverId != id) {
                    deficit.incrementAndGet();
                    synchronized (children) {
                        children.add(receiverId);
                    }
                    messageQueue.add(new Message(id, receiverId, "COMPUTATION"));
                    System.out.println("Process " + id + " sent COMPUTATION to Process " + receiverId);
                }
            }
        }

        private void processMessages() {
            for (Message msg = messageQueue.poll(); msg != null; msg = messageQueue.poll()) {
                if (msg.receiverId == id) {
                    if (msg.type.equals("COMPUTATION")) {
                        isActive = true;
                        System.out.println("Process " + id + " received COMPUTATION from Process " + msg.senderId);
                    } else if (msg.type.equals("SIGNAL")) {
                        deficit.decrementAndGet();
                        System.out.println("Process " + id + " received SIGNAL from Process " + msg.senderId);
                        synchronized (children) {
                            children.remove(Integer.valueOf(msg.senderId));
                        }
                    }
                } else {
                    // If message not for this process, put it back
                    messageQueue.add(msg);
                }
            }
        }

        private void becomePassive() {
            isActive = false;
            System.out.println("Process " + id + " became passive.");

            synchronized (children) {
                for (Integer childId : children) {
                    messageQueue.add(new Message(id, childId, "SIGNAL"));
                    System.out.println("Process " + id + " sent SIGNAL to Process " + childId);
                }
                children.clear();
            }
        }

        private boolean checkTermination() {
            return !isActive && children.isEmpty() && deficit.get() == 0 && messageQueue.isEmpty();
        }
    }

    // --- Main ---
    public static void main(String[] args) {
        Process[] processes = new Process[processCount];

        // Create and start processes
        for (int i = 0; i < processCount; i++) {
            processes[i] = new Process(i, i == 0); // Process 0 is initiator
            processes[i].start();
        }

        // Wait for all threads to finish
        for (Process p : processes) {
            try {
                p.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\n==============================");
        System.out.println("✅ All processes have terminated successfully.");
        System.out.println("==============================");
    }
}


