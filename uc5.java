import java.util.LinkedList;
import java.util.Queue;

/**
 * Reservation Class (Represents a booking request)
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

/**
 * Booking Request Queue (FIFO Handling)
 */
class BookingQueue {

    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        queue.offer(reservation); // FIFO insertion
        System.out.println("✅ Booking request added for " + reservation.getGuestName());
    }

    // View all requests (without removing)
    public void viewRequests() {
        System.out.println("\n===== BOOKING REQUEST QUEUE =====");

        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : queue) {
            r.display();
        }

        System.out.println("=================================\n");
    }

    // Get next request (for future processing, not used yet)
    public Reservation peekNext() {
        return queue.peek(); // does not remove
    }

    // Get queue size
    public int size() {
        return queue.size();
    }
}

/**
 * Main Application
 */
public class uc5 {

    public static void main(String[] args) {

        // Initialize booking queue
        BookingQueue bookingQueue = new BookingQueue();

        // Guests submit booking requests
        System.out.println("Guests submitting booking requests...\n");

        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        bookingQueue.addRequest(new Reservation("David", "Single Room"));

        // View all queued requests (FIFO order preserved)
        bookingQueue.viewRequests();

        // Show next request to be processed (without removing)
        Reservation next = bookingQueue.peekNext();
        if (next != null) {
            System.out.println("Next request to process (FIFO):");
            next.display();
        }

        // No inventory updates here (important)
        System.out.println("\n⚠️ Note: No room allocation or inventory updates are performed in this stage.");
    }
}