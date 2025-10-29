package main;

import java.util.ArrayList;
import java.util.List;

public class DBConnection {

    // Temporary in-memory list (acting as database)
    private static final List<StudentRecord> database = new ArrayList<>();
    private static int idCounter = 1;

    // Student record structure
    public static class StudentRecord {
        public int id;
        public String name;
        public double sgpa;
        public double cgpa;

        public StudentRecord(int id, String name, double sgpa, double cgpa) {
            this.id = id;
            this.name = name;
            this.sgpa = sgpa;
            this.cgpa = cgpa;
        }
    }

    // Add record to memory
    public static void addRecord(int regNo, String name, double cgpa, double sgpa) {
        database.add(new StudentRecord(idCounter++, name, sgpa, cgpa));
    }

    // Get all student records
    public static List<StudentRecord> getAllRecords() {
        return new ArrayList<>(database);
    }

    // Delete record by ID
    public static void deleteRecord(int id) {
        database.removeIf(r -> r.id == id);
    }

    // Update record if needed
    public static void updateRecord(int id, String name, double sgpa, double cgpa) {
        for (StudentRecord r : database) {
            if (r.id == id) {
                r.name = name;
                r.sgpa = sgpa;
                r.cgpa = cgpa;
                break;
            }
        }
    }
}
