import java.io.Serializable;
import java.util.Objects;

/**
 * Student Model Class
 * Represents a student entity with validation and encapsulation
 */
public class Student implements Serializable, Comparable<Student> {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String email;
    private double prelimGrade;
    private double midtermGrade;
    private double finalGrade;

    /**
     * Constructor with validation
     */
    public Student(String id, String name, String email, double prelimGrade, double midtermGrade, double finalGrade) throws IllegalArgumentException {
        setId(id);
        setName(name);
        setEmail(email);
        setPrelimGrade(prelimGrade);
        setMidtermGrade(midtermGrade);
        setFinalGrade(finalGrade);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getPrelimGrade() {
        return prelimGrade;
    }

    public double getMidtermGrade() {
        return midtermGrade;
    }

    public double getFinalGrade() {
        return finalGrade;
    }

    /**
     * Calculate overall average grade
     */
    public double getAverageGrade() {
        return (prelimGrade + midtermGrade + finalGrade) / 3.0;
    }

    // Setters with validation
    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be empty");
        }
        if (!id.matches("^[A-Za-z0-9-]+$")) {
            throw new IllegalArgumentException("Student ID can only contain letters, numbers, and hyphens");
        }
        this.id = id.trim();
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty");
        }
        if (name.trim().length() < 2) {
            throw new IllegalArgumentException("Student name must be at least 2 characters");
        }
        this.name = name.trim();
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email.trim().toLowerCase();
    }

    public void setPrelimGrade(double grade) {
        if (grade < 0 || grade > 100) {
            throw new IllegalArgumentException("Prelim grade must be between 0 and 100");
        }
        this.prelimGrade = grade;
    }

    public void setMidtermGrade(double grade) {
        if (grade < 0 || grade > 100) {
            throw new IllegalArgumentException("Midterm grade must be between 0 and 100");
        }
        this.midtermGrade = grade;
    }

    public void setFinalGrade(double grade) {
        if (grade < 0 || grade > 100) {
            throw new IllegalArgumentException("Final grade must be between 0 and 100");
        }
        this.finalGrade = grade;
    }

    /**
     * Convert student to file format (CSV)
     */
    public String toFileFormat() {
        return String.format("%s|%s|%s|%.2f|%.2f|%.2f", id, name, email, prelimGrade, midtermGrade, finalGrade);
    }

    /**
     * Create student from file format
     */
    public static Student fromFileFormat(String line) throws IllegalArgumentException {
        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid file line");
        }

        String[] parts = line.split("\\|");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid file format - expected 6 fields");
        }

        try {
            return new Student(
                    parts[0],
                    parts[1],
                    parts[2],
                    Double.parseDouble(parts[3]),
                    Double.parseDouble(parts[4]),
                    Double.parseDouble(parts[5])
            );
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid grade format");
        }
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Email: %s | Prelim: %.2f | Midterm: %.2f | Final: %.2f | Average: %.2f",
                id, name, email, prelimGrade, midtermGrade, finalGrade, getAverageGrade());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Student other) {
        return this.name.compareToIgnoreCase(other.name);
    }
}