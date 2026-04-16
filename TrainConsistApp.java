import javax.swing.*;
import java.awt.*;
import java.util.*;

public class TrainConsistApp extends JFrame {

    // Data structures
    private LinkedHashMap<String, String> bogieMap; // Key: BogieID, Value: BogieType
    private HashMap<String, Integer> bogieCapacity; // UC6: capacity mapping
    private HashSet<String> uniqueBogieIDs;         // UC3: uniqueness
    private DefaultListModel<String> listModel;
    private JList<String> bogieList;

    // GUI Components
    private JTextField trainNumberField;
    private JTextField bogieIDField;
    private JComboBox<String> bogieTypeOptions;
    private JComboBox<String> bogieCategoryOptions;

    public TrainConsistApp() {
        // Initialize data structures
        bogieMap = new LinkedHashMap<>();
        uniqueBogieIDs = new HashSet<>();
        bogieCapacity = new HashMap<>();
        populateBogieCapacity();

        // Window setup
        setTitle("Enhanced Train Consist Management");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel: train number and bogie details
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        trainNumberField = new JTextField();
        bogieIDField = new JTextField();

        String[] bogieTypes = {"Sleeper", "AC Chair", "First Class", "Goods Rectangular", "Goods Cylindrical", "Engine", "Guard", "Pantry Car"};
        bogieTypeOptions = new JComboBox<>(bogieTypes);

        String[] categories = {"Passenger", "Goods", "Special"};
        bogieCategoryOptions = new JComboBox<>(categories);

        topPanel.add(new JLabel("Train Number:"));
        topPanel.add(trainNumberField);
        topPanel.add(new JLabel("Bogie ID:"));
        topPanel.add(bogieIDField);
        topPanel.add(new JLabel("Bogie Type:"));
        topPanel.add(bogieTypeOptions);
        topPanel.add(new JLabel("Category:"));
        topPanel.add(bogieCategoryOptions);

        add(topPanel, BorderLayout.NORTH);

        // Center panel: list of bogies
        listModel = new DefaultListModel<>();
        bogieList = new JList<>(listModel);
        add(new JScrollPane(bogieList), BorderLayout.CENTER);

        // Bottom panel: buttons
        JPanel bottomPanel = new JPanel();
        JButton addBtn = new JButton("Add Bogie");
        JButton removeBtn = new JButton("Remove Bogie");
        JButton checkBtn = new JButton("Check Bogie Exists");
        JButton displayBtn = new JButton("Display Train Status");
        JButton showMapBtn = new JButton("Show Bogie Capacities");

        bottomPanel.add(addBtn);
        bottomPanel.add(removeBtn);
        bottomPanel.add(checkBtn);
        bottomPanel.add(displayBtn);
        bottomPanel.add(showMapBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // Button actions
        addBtn.addActionListener(e -> addBogie());
        removeBtn.addActionListener(e -> removeBogie());
        checkBtn.addActionListener(e -> checkBogieExists());
        displayBtn.addActionListener(e -> displayTrainStatus());
        showMapBtn.addActionListener(e -> showBogieCapacityMap());
    }

    // Populate bogie capacities (UC6)
    private void populateBogieCapacity() {
        bogieCapacity.put("Sleeper", 72);
        bogieCapacity.put("AC Chair", 78);
        bogieCapacity.put("First Class", 60);
        bogieCapacity.put("Goods Rectangular", 10000);
        bogieCapacity.put("Goods Cylindrical", 12000);
        bogieCapacity.put("Engine", 0);
        bogieCapacity.put("Guard", 0);
        bogieCapacity.put("Pantry Car", 0);
    }

    // Add bogie with validation
    private void addBogie() {
        String trainNo = trainNumberField.getText().trim();
        String bogieID = bogieIDField.getText().trim();
        String bogieType = (String) bogieTypeOptions.getSelectedItem();
        String category = (String) bogieCategoryOptions.getSelectedItem();

        if (trainNo.isEmpty() || bogieID.isEmpty() || bogieType == null) {
            JOptionPane.showMessageDialog(this, "❌ Enter Train Number, Bogie ID and select type!");
            return;
        }

        if (uniqueBogieIDs.contains(bogieID)) {
            JOptionPane.showMessageDialog(this, "❌ Bogie ID already exists!");
            return;
        }

        // Add to structures
        uniqueBogieIDs.add(bogieID);
        bogieMap.put(bogieID, bogieType);

        // Display in list with capacity
        int capacity = bogieCapacity.getOrDefault(bogieType, 0);
        String details = bogieID + " | " + bogieType + " | " + category + " | Capacity: " + capacity;
        listModel.addElement(details);
        JOptionPane.showMessageDialog(this, "✅ Bogie Added!");
    }

    private void removeBogie() {
        int index = bogieList.getSelectedIndex();
        if (index >= 0) {
            String entry = listModel.get(index);
            String bogieID = entry.split(" \\| ")[0];
            bogieMap.remove(bogieID);
            uniqueBogieIDs.remove(bogieID);
            listModel.remove(index);
            JOptionPane.showMessageDialog(this, "✅ Bogie removed!");
        } else {
            JOptionPane.showMessageDialog(this, "❌ Select a bogie to remove");
        }
    }

    private void checkBogieExists() {
        String bogieID = bogieIDField.getText().trim();
        if (uniqueBogieIDs.contains(bogieID)) {
            JOptionPane.showMessageDialog(this, "✅ Bogie ID exists: " + bogieID);
        } else {
            JOptionPane.showMessageDialog(this, "❌ Bogie ID does not exist");
        }
    }

    private void displayTrainStatus() {
        if (bogieMap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "🚆 Train is empty");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("🚆 Train Status\n--------------------\n");
        int totalSeats = 0;
        int totalCargo = 0;

        for (Map.Entry<String, String> entry : bogieMap.entrySet()) {
            String bogieID = entry.getKey();
            String type = entry.getValue();
            int capacity = bogieCapacity.getOrDefault(type, 0);
            sb.append("🚃 ").append(bogieID).append(" | ").append(type).append(" | Capacity: ").append(capacity);

            if (type.equals("Sleeper") || type.equals("AC Chair") || type.equals("First Class")) {
                totalSeats += capacity;
                sb.append(" seats\n");
            } else if (type.equals("Goods Rectangular") || type.equals("Goods Cylindrical")) {
                totalCargo += capacity;
                sb.append(" kg\n");
            } else {
                sb.append("\n");
            }
        }

        sb.append("--------------------\nTotal Seats: ").append(totalSeats).append("\nTotal Cargo Capacity: ").append(totalCargo).append(" kg\n");
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void showBogieCapacityMap() {
        StringBuilder sb = new StringBuilder("💡 Bogie Capacities\n--------------------\n");
        for (Map.Entry<String, Integer> entry : bogieCapacity.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue());
            if (entry.getKey().equals("Sleeper") || entry.getKey().equals("AC Chair") || entry.getKey().equals("First Class")) sb.append(" seats\n");
            else if (entry.getKey().equals("Goods Rectangular") || entry.getKey().equals("Goods Cylindrical")) sb.append(" kg\n");
            else sb.append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TrainConsistApp().setVisible(true));
    }
}