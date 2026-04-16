import java.util.*;

/**
 * Reservation Class (Booking Request)
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
}

/**
 * Booking Queue (FIFO)
 */
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // removes in FIFO order
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

/**
 * Inventory Service (Centralized State)
 */
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void displayInventory() {
        System.out.println("\n=== CURRENT INVENTORY ===");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " → " + e.getValue());
        }
    }
}

/**
 * Booking Service (Core Allocation Logic)
 */
class BookingService {

    private RoomInventory inventory;

    // Prevent double booking
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Track room allocations per type
    private Map<String, Set<String>> roomAllocations = new HashMap<>();

    // Room ID counters
    private Map<String, Integer> counters = new HashMap<>();

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        int count = counters.getOrDefault(roomType, 0) + 1;
        counters.put(roomType, count);

        return roomType.replace(" ", "").toUpperCase() + "-" + count;
    }

    // Process booking request
    public void processRequest(Reservation r) {
        String type = r.getRoomType();

        System.out.println("\nProcessing booking for " + r.getGuestName());

        // Check availability
        if (inventory.getAvailability(type) <= 0) {
            System.out.println("❌ No rooms available for " + type);
            return;
        }

        // Generate unique ID
        String roomId = generateRoomId(type);

        // Ensure uniqueness (Set check)
        if (allocatedRoomIds.contains(roomId)) {
            System.out.println("❌ Duplicate Room ID detected! (should never happen)");
            return;
        }

        // Allocate room
        allocatedRoomIds.add(roomId);

        roomAllocations.putIfAbsent(type, new HashSet<>());
        roomAllocations.get(type).add(roomId);

        // Update inventory (atomic step)
        inventory.decrement(type);

        // Confirm booking
        System.out.println("✅ Booking Confirmed!");
        System.out.println("Guest: " + r.getGuestName());
        System.out.println("Room Type: " + type);
        System.out.println("Assigned Room ID: " + roomId);
    }

    // Display all allocations
    public void displayAllocations() {
        System.out.println("\n=== ROOM ALLOCATIONS ===");
        for (String type : roomAllocations.keySet()) {
            System.out.println(type + " → " + roomAllocations.get(type));
        }
    }
}

/**
 * Main Application
 */
public class uc6 {

    public static void main(String[] args) {

        // Initialize components
        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        // Add booking requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room")); // should fail
        queue.addRequest(new Reservation("David", "Suite Room"));

        // Process queue (FIFO)
        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            bookingService.processRequest(r);
        }

        // Display final state
        bookingService.displayAllocations();
        inventory.displayInventory();
    }
}
