import java.util.*;

/**
 * Abstract Room Class (Domain Model)
 */
abstract class Room {
    private String type;
    private int beds;
    private double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type +
                " | Beds: " + beds +
                " | Price: ₹" + price);
    }
}

/**
 * Concrete Room Types
 */
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2000);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000);
    }
}

/**
 * Inventory Class (State Holder - Read Only Access Here)
 */
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // intentionally unavailable
        inventory.put("Suite Room", 2);
    }

    // Read-only method
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Expose all keys (safe read)
    public Set<String> getRoomTypes() {
        return inventory.keySet();
    }
}

/**
 * Search Service (Read-Only Logic)
 */
class SearchService {

    private RoomInventory inventory;
    private List<Room> rooms;

    public SearchService(RoomInventory inventory, List<Room> rooms) {
        this.inventory = inventory;
        this.rooms = rooms;
    }

    // Search available rooms (READ-ONLY)
    public void searchAvailableRooms() {
        System.out.println("\n===== AVAILABLE ROOMS =====");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getType());

            // Validation: show only available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println("---------------------------");
            }
        }

        System.out.println("===== END OF RESULTS =====\n");
    }
}

/**
 * Main Application
 */
public class uc4 {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize room domain objects
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Initialize search service
        SearchService searchService = new SearchService(inventory, rooms);

        // Guest performs search
        System.out.println("Guest searching for available rooms...");
        searchService.searchAvailableRooms();

        // Verify inventory remains unchanged (read-only behavior)
        System.out.println("Verifying inventory is unchanged...");
        System.out.println("Single Room Available: " + inventory.getAvailability("Single Room"));
        System.out.println("Double Room Available: " + inventory.getAvailability("Double Room"));
        System.out.println("Suite Room Available: " + inventory.getAvailability("Suite Room"));
    }
}
