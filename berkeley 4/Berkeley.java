import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

class Node {
  final int id;
  long clockOffsetMs;
  boolean alive = true;

  Node(int id, long offsetMs) {
    this.id = id;
    this.clockOffsetMs = offsetMs;
  }

  long currentTime() {
    return System.currentTimeMillis() + clockOffsetMs;
  }
}

public class Berkeley {
  static String formatTime(long ms) {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    return sdf.format(new Date(ms));
  }

  //new Date(0) → represents 1 Jan 1970, 05:30:00 AM IST
  //currenttimemills - conv into readable format

  public static void main(String[] args) {
    List<Node> nodes = new ArrayList<>();
    nodes.add(new Node(0, 0));      // master
    nodes.add(new Node(1, 500));    // +0.5 sec
    nodes.add(new Node(2, -800));   // -0.8 sec
    nodes.add(new Node(3, 1200));   // +1.2 sec
    nodes.add(new Node(4, -300));   // -0.3 sec

    Node master = nodes.get(0);
    List<Long> samples = new ArrayList<>();

    for (Node n : nodes) {
      if (!n.alive) {
        continue;
      }
      samples.add(n.currentTime());
    }

    Collections.sort(samples);

    int trim = Math.max(0, samples.size() / 10);
    List<Long> trimmed = samples.subList(trim, samples.size() - trim);

    long sum = 0;
    for (long t : trimmed) {
      sum += t;
    }
    long avg = sum / trimmed.size();

    System.out.println("--------------------------------------------------");
    System.out.println(" Before Synchronization ");
    System.out.println("--------------------------------------------------");
    for (Node n : nodes) {
      if (n.alive) {
        System.out.printf(
          "Node %d Time: %s (Offset: %d ms)%n",n.id, formatTime(n.currentTime()), n.clockOffsetMs
        );
      } else {
        System.out.printf("Node %d is inactive%n", n.id);
      }
    }

    for (Node n : nodes) {
      if (n.alive) {
        long adjust = avg - n.currentTime();
        n.clockOffsetMs += adjust;
      }
    }

    System.out.println("-------------------------------------------------");
    System.out.println(" After Synchronization ");
    System.out.println("--------------------------------------------------");
    for (Node n : nodes) {
      if (n.alive) {
        System.out.printf(
          "Node %d Time: %s (Offset: %d ms)%n", n.id, formatTime(n.currentTime()), n.clockOffsetMs
        );
      } else {
        System.out.printf("Node %d is inactive%n", n.id);
      }
    }

    System.out.println("--------------------------------------------------");
    System.out.println("Average (Reference) Time: " + formatTime(avg));
    System.out.println("--------------------------------------------------");
  }
}
