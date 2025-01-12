package Business.Pharmacy;

import java.sql.*;
import java.util.ArrayList;

public class MedicineDatabase {
    private static MedicineDatabase instance;
    private Connection connection;

    private MedicineDatabase() {
        try {
            // SQLite database connection (creates `pharmacy.db` in the project folder if it doesn’t exist)
            connection = DriverManager.getConnection("jdbc:sqlite:pharmacy.db");
            initializeDatabase(); // Ensure the medicines table and data exist
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static MedicineDatabase getInstance() {
        if (instance == null) {
            instance = new MedicineDatabase();
        }
        return instance;
    }

    // Initialize the database: create table and insert sample data if not exists
    private void initializeDatabase() {
        try (Statement stmt = connection.createStatement()) {
            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS medicines (
                    id TEXT PRIMARY KEY,
                    name TEXT NOT NULL,
                    price REAL NOT NULL,
                    stock INTEGER NOT NULL,
                    description TEXT,
                    expiry_date TEXT NOT NULL
                );
            """;

            stmt.execute(createTableSQL);

            String insertSampleDataSQL = """
                INSERT OR IGNORE INTO medicines (id, name, price, stock, description, expiry_date) VALUES
                ('M001', 'Dolo 650', 15.00, 1000, 'Paracetamol for fever and pain', '2025-12-31'),
                ('M002', 'Crocin', 12.00, 800, 'Pain reliever and fever reducer', '2025-10-31'),
                ('M003', 'Azithromycin', 150.00, 500, 'Antibiotic', '2025-06-30'),
                ('M004', 'Montek LC', 120.00, 300, 'Allergy medication', '2025-08-31'),
                ('M005', 'Cetrizine', 10.00, 600, 'Antihistamine for allergies', '2025-09-30'),
                ('M006', 'Omeprazole', 85.00, 400, 'Acid reflux medication', '2025-07-31'),
                ('M007', 'Metformin', 50.00, 700, 'Diabetes medication', '2025-11-30'),
                ('M008', 'Aspirin', 25.00, 1000, 'Blood thinner', '2025-12-31'),
                ('M009', 'Vitamin D3', 180.00, 400, 'Vitamin supplement', '2025-05-31'),
                ('M010', 'B-Complex', 120.00, 500, 'Vitamin supplement', '2025-04-30');
            """;
            

            stmt.execute(insertSampleDataSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all medicines
    public ArrayList<Medicine> getAllMedicines() {
        ArrayList<Medicine> medicines = new ArrayList<>();
        try {
            String query = "SELECT * FROM medicines";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                medicines.add(new Medicine(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("stock"),
                    resultSet.getString("description"),
                    resultSet.getString("expiry_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicines;
    }

    // Retrieve a single medicine
    public Medicine getMedicine(String id) {
        try {
            String query = "SELECT * FROM medicines WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Medicine(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("stock"),
                    resultSet.getString("description"),
                    resultSet.getString("expiry_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update stock for a specific medicine
    public boolean updateStock(String medicineId, int quantity) {
        try {
            String query = "UPDATE medicines SET stock = stock - ? WHERE id = ? AND stock >= ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, quantity); // Deduct the dosage
            statement.setString(2, medicineId);
            statement.setInt(3, quantity);
    
            return statement.executeUpdate() > 0; // Returns true if stock was updated
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    

    // Add a new medicine (if needed)
    public void addMedicine(Medicine medicine) {
        try {
            String query = "INSERT INTO medicines (id, name, price, stock, description, expiry_date) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, medicine.getId());
            statement.setString(2, medicine.getName());
            statement.setDouble(3, medicine.getPrice());
            statement.setInt(4, medicine.getStock());
            statement.setString(5, medicine.getDescription());
            statement.setString(6, medicine.getExpiryDate());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
