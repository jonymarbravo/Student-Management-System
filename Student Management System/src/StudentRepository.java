import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Repository class for managing student data persistence
 * Handles file I/O operations with proper error handling
 */
public class StudentRepository {
    private static final String FILE_NAME = "students.txt";
    private static final String BACKUP_FILE_NAME = "students_backup.txt";
    private final Path filePath;
    private final Path backupPath;
    private List<Student> students;

    public StudentRepository() {
        this.filePath = Paths.get(FILE_NAME);
        this.backupPath = Paths.get(BACKUP_FILE_NAME);
        this.students = new ArrayList<>();
        loadFromFile();
    }

    /**
     * Load students from file on initialization
     */
    private void loadFromFile() {
        if (!Files.exists(filePath)) {
            System.out.println("No existing data file found. Starting with empty database.");
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue; // Skip empty lines and comments
                }

                try {
                    Student student = Student.fromFileFormat(line);
                    students.add(student);
                } catch (IllegalArgumentException e) {
                    System.err.println("Error parsing line " + lineNumber + ": " + e.getMessage());
                }
            }
            System.out.println("Loaded " + students.size() + " students from file.");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Save all students to file with backup
     */
    public boolean saveToFile() {
        try {
            // Create backup if file exists
            if (Files.exists(filePath)) {
                Files.copy(filePath, backupPath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Write to file
            try (BufferedWriter writer = Files.newBufferedWriter(filePath,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING)) {

                writer.write("# Student Management System Data File\n");
                writer.write("# Format: ID|Name|Email|PrelimGrade|MidtermGrade|FinalGrade\n");
                writer.write("# Last updated: " + new Date() + "\n");

                for (Student student : students) {
                    writer.write(student.toFileFormat());
                    writer.newLine();
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Add a new student
     */
    public boolean addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        // Check for duplicate ID
        if (findById(student.getId()) != null) {
            throw new IllegalArgumentException("Student with ID " + student.getId() + " already exists");
        }

        students.add(student);
        return saveToFile();
    }

    /**
     * Delete student by ID
     */
    public boolean deleteStudent(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be empty");
        }

        boolean removed = students.removeIf(s -> s.getId().equalsIgnoreCase(id.trim()));
        if (removed) {
            saveToFile();
        }
        return removed;
    }

    /**
     * Update student information
     */
    public boolean updateStudent(String id, Student updatedStudent) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be empty");
        }
        if (updatedStudent == null) {
            throw new IllegalArgumentException("Updated student data cannot be null");
        }

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equalsIgnoreCase(id.trim())) {
                students.set(i, updatedStudent);
                return saveToFile();
            }
        }
        return false;
    }

    /**
     * Find student by ID
     */
    public Student findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }

        return students.stream()
                .filter(s -> s.getId().equalsIgnoreCase(id.trim()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Search students by name (partial match)
     */
    public List<Student> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String searchTerm = name.trim().toLowerCase();
        return students.stream()
                .filter(s -> s.getName().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Get all students
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    /**
     * Sort students by name (A-Z)
     */
    public List<Student> getSortedByName() {
        List<Student> sorted = new ArrayList<>(students);
        Collections.sort(sorted);
        return sorted;
    }

    /**
     * Sort students by average grade (highest first)
     */
    public List<Student> getSortedByGrade() {
        List<Student> sorted = new ArrayList<>(students);
        sorted.sort((s1, s2) -> Double.compare(s2.getAverageGrade(), s1.getAverageGrade()));
        return sorted;
    }

    /**
     * Get total number of students
     */
    public int getStudentCount() {
        return students.size();
    }

    /**
     * Calculate average grade across all students
     */
    public double getAverageGrade() {
        if (students.isEmpty()) {
            return 0.0;
        }
        return students.stream()
                .mapToDouble(Student::getAverageGrade)
                .average()
                .orElse(0.0);
    }
}