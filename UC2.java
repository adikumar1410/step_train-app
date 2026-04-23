/**
 * Use Case 2: Room Modeling using OOP Concepts
 * Demonstrates Abstraction, Inheritance, Polymorphism, and Encapsulation
 */

// Abstract Class
abstract class Room {
    private int beds;
    private double price;
    private String type;

    // Constructor
    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    // Getters (Encapsulation)
    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    // Common method
    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

// Single Room Class
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1000);
    }
}

// Double Room Class
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2000);
    }
}

// Suite Room Class
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000);
    }
}

// Main Application Class
public class UC2 {

    public static void main(String[] args) {

        // Polymorphism (Parent reference, child objects)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static Availability (simple variables)
        int availableSingleRooms = 5;
        int availableDoubleRooms = 3;
        int availableSuiteRooms = 2;

        // Display details
        System.out.println("===== HOTEL ROOM DETAILS =====\n");

        singleRoom.displayDetails();
        System.out.println("Available Rooms: " + availableSingleRooms);
        System.out.println("--------------------------------");

        doubleRoom.displayDetails();
        System.out.println("Available Rooms: " + availableDoubleRooms);
        System.out.println("--------------------------------");

        suiteRoom.displayDetails();
        System.out.println("Available Rooms: " + availableSuiteRooms);
        System.out.println("--------------------------------");
    }
}