import java.util.HashMap;
import java.util.Map;

/**
 * Abstract Room Class (Reused from Use Case 2)
 */
abstract class Room {
    private int beds;
    private double price;
    private String type;

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
 * Concrete Room Classes
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
 * RoomInventory Class (Centralized State Management)
 */
class RoomInventory {

    // HashMap to store availability
    private Map<String, Integer> inventory;

    // Constructor - initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initial room availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (controlled)
    public void updateAvailability(String roomType, int change) {
        int current = inventory.getOrDefault(roomType, 0);
        int updated = current + change;

        if (updated < 0) {
            System.out.println("❌ Not enough rooms available for: " + roomType);
        } else {
            inventory.put(roomType, updated);
            System.out.println("✅ Updated " + roomType + " availability to: " + updated);
        }
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("\n===== CURRENT ROOM INVENTORY =====");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " → Available: " + entry.getValue());
        }
        System.out.println("==================================\n");
    }
}

/**
 * Main Application
 */
public class uc3 {

    public static void main(String[] args) {

        // Initialize rooms (domain)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Initialize inventory (centralized state)
        RoomInventory inventory = new RoomInventory();

        // Display room details
        System.out.println("===== ROOM DETAILS =====");
        single.displayDetails();
        doubleRoom.displayDetails();
        suite.displayDetails();

        // Display inventory
        inventory.displayInventory();

        // Simulate booking (decrease availability)
        System.out.println("Booking 2 Single Rooms...");
        inventory.updateAvailability("Single Room", -2);

        // Simulate cancellation (increase availability)
        System.out.println("Cancelling 1 Suite Room...");
        inventory.updateAvailability("Suite Room", +1);

        // Display updated inventory
        inventory.displayInventory();
    }
}