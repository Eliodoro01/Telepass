package com.example.telepass;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TelepassSystem extends Subject {
    private static final String DB_URL = "jdbc:sqlite:telepass.db";

    public TelepassSystem() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            // Crea la tabella devices se non esiste
            stmt.execute("CREATE TABLE IF NOT EXISTS devices (" +
                    "device_code TEXT PRIMARY KEY, " +
                    "license_plate TEXT NOT NULL, " +
                    "owner_name TEXT NOT NULL, " +
                    "payment_method TEXT NOT NULL, " +
                    "is_telepass_plus INTEGER DEFAULT 0)"); // Aggiunto campo per Telepass+

            // PER "AGGIUSTARE" PROBLEMA CON LA NON CREAZIONE DEL CAMPO TELEPASS PLUS
            addColumnIfNotExists(conn, "devices", "is_telepass_plus", "INTEGER DEFAULT 0");

            // Crea la tabella transactions se non esiste
            stmt.execute("CREATE TABLE IF NOT EXISTS transactions (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "device_code TEXT NOT NULL, " +
                    "type TEXT NOT NULL, " +
                    "amount REAL NOT NULL, " +
                    "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (device_code) REFERENCES devices(device_code))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addColumnIfNotExists(Connection conn, String tableName, String columnName, String columnType) {
        try {
            // Verifica se la colonna esiste già
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, tableName, columnName);
            if (!columns.next()) {
                // La colonna non esiste, quindi la aggiungiamo
                String sql = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " " + columnType;
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(sql);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insertDevice(String deviceCode, String licensePlate, String ownerName, String paymentMethod) {
        String sql = "INSERT INTO devices(device_code, license_plate, owner_name, payment_method) VALUES(?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, deviceCode);
            pstmt.setString(2, licensePlate);
            pstmt.setString(3, ownerName);
            pstmt.setString(4, paymentMethod);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean revokeDevice(String deviceCode) {
        String sql = "DELETE FROM devices WHERE device_code = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, deviceCode);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double enterTollBooth(String deviceCode) {
        double amount = 2.50; // Tariffa fissa
        String sql = "INSERT INTO transactions(device_code, type, amount) VALUES(?, 'ENTRY', ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, deviceCode);
            pstmt.setDouble(2, amount);
            pstmt.executeUpdate();
            notifyObservers("Transazione ENTRY registrata per il dispositivo: " + deviceCode);
            return amount;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public double exitTollBooth(String deviceCode) {
        double amount = 2.50; // Tariffa fissa
        String sql = "INSERT INTO transactions(device_code, type, amount) VALUES(?, 'EXIT', ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, deviceCode);
            pstmt.setDouble(2, amount);
            pstmt.executeUpdate();
            notifyObservers("Transazione EXIT registrata per il dispositivo: " + deviceCode);
            return amount;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<String> getStatistics() {
        List<String> stats = new ArrayList<>();
        String sql = "SELECT type, COUNT(*) as count, SUM(amount) as total FROM transactions GROUP BY type";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String type = rs.getString("type");
                int count = rs.getInt("count");
                double total = rs.getDouble("total");
                stats.add(type + ": " + count + " transazioni, totale incasso: " + total + "€");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }

    public boolean associateLicensePlate(String deviceCode, String newLicensePlate) {
        String sql = "UPDATE devices SET license_plate = ? WHERE device_code = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newLicensePlate);
            pstmt.setString(2, deviceCode);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean convertToTelepassPlus(String deviceCode) {
        String sql = "UPDATE devices SET is_telepass_plus = 1 WHERE device_code = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, deviceCode);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isTelepassPlus(String deviceCode) {
        String sql = "SELECT is_telepass_plus FROM devices WHERE device_code = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, deviceCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("is_telepass_plus") == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}