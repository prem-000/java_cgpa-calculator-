package main;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:student_data.db"; // Creates file in project directory

    static {
        // Create database table if not exists
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            String createTable = """
                    CREATE TABLE IF NOT EXISTS students (
                        id INTEGER PRIMARY KEY,
                        name TEXT NOT NULL,
                        cgpa REAL,
                        sgpa REAL
                    );
                    """;
            stmt.execute(createTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------- Add Record ----------
    public static void addRecord(int id, String name, double cgpa, double sgpa) {
        try (Connection conn = DriverManager.getConnection(URL)) {
            // If record already exists → update SGPA
            if (recordExists(conn, id)) {
                String sql = "UPDATE students SET name=?, sgpa=? WHERE id=?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setDouble(2, sgpa);
                pstmt.setInt(3, id);
                pstmt.executeUpdate();
            } else {
                String sql = "INSERT INTO students (id, name, cgpa, sgpa) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, id);
                pstmt.setString(2, name);
                pstmt.setDouble(3, cgpa);
                pstmt.setDouble(4, sgpa);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------- Update Record ----------
    public static void updateRecord(int id, String name, double cgpa, double sgpa) {
        String sql = "UPDATE students SET name=?, cgpa=?, sgpa=? WHERE id=?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, cgpa);
            pstmt.setDouble(3, sgpa);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------- Delete Record ----------
    public static void deleteRecord(int id) {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------- Get All Records ----------
    public static String getAllRecords() {
        StringBuilder result = new StringBuilder("📘 STUDENT RECORDS\n\n");
        String sql = "SELECT * FROM students ORDER BY id";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double cgpa = rs.getDouble("cgpa");
                double sgpa = rs.getDouble("sgpa");

                result.append(String.format("Reg No: %d | Name: %s | CGPA: %.2f | SGPA: %.2f%n",
                        id, name, cgpa, sgpa));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (result.toString().equals("📘 STUDENT RECORDS\n\n"))
            result.append("No records found!");

        return result.toString();
    }

    // ---------- Helper: Check if Record Exists ----------
    private static boolean recordExists(Connection conn, int id) throws SQLException {
        String sql = "SELECT id FROM students WHERE id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }

    public static Connection getConnection() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getConnection'");
    }
}
