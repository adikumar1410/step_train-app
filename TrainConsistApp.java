// Custom Runtime Exception
class CargoSafetyException extends RuntimeException {
    public CargoSafetyException(String message) {
        super(message);
    }
}

// Goods Bogie Class
class GoodsBogie {
    String type;   // Cylindrical, Rectangular
    String cargo;

    GoodsBogie(String type) {
        this.type = type;
    }

    // Method to assign cargo safely
    public void assignCargo(String cargo) {

        try {
            // Safety Rule:
            // Rectangular bogie cannot carry Petroleum
            if (type.equalsIgnoreCase("Rectangular") &&
                    cargo.equalsIgnoreCase("Petroleum")) {

                throw new CargoSafetyException(
                        "Unsafe: Rectangular bogie cannot carry Petroleum");
            }

            // If safe → assign cargo
            this.cargo = cargo;
            System.out.println("Cargo assigned successfully: " + cargo);

        } catch (CargoSafetyException e) {
            System.out.println("Error: " + e.getMessage());

        } finally {
            System.out.println("Cargo assignment process completed.\n");
        }
    }

    @Override
    public String toString() {
        return type + " bogie carrying " + (cargo != null ? cargo : "No Cargo");
    }
}

// Main Class
public class TrainConsistApp {

    public static void main(String[] args) {

        // Create bogies
        GoodsBogie b1 = new GoodsBogie("Cylindrical");
        GoodsBogie b2 = new GoodsBogie("Rectangular");

        // Safe assignment
        b1.assignCargo("Petroleum");

        // Unsafe assignment
        b2.assignCargo("Petroleum");

        // Program continues
        System.out.println("Final Bogie States:");
        System.out.println(b1);
        System.out.println(b2);
    }
}