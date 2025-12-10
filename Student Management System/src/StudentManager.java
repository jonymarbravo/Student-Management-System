import javax.swing.*;
import java.util.List;

/**
 * Main Student Management System Application
 * Provides JOptionPane-based user interface with improved form validation
 */
public class StudentManager {
    private final StudentRepository repository;
    private static final String APP_TITLE = "Student Management System";

    public StudentManager() {
        this.repository = new StudentRepository();
    }

    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Use default if system look and feel fails
        }

        StudentManager manager = new StudentManager();
        manager.run();
    }

    /**
     * Main application loop
     */
    public void run() {
        showWelcomeMessage();

        boolean running = true;
        while (running) {
            String choice = showMainMenu();

            if (choice == null) {
                running = confirmExit();
                continue;
            }

            try {
                switch (choice) {
                    case "1":
                        addStudent();
                        break;
                    case "2":
                        deleteStudent();
                        break;
                    case "3":
                        updateStudent();
                        break;
                    case "4":
                        searchStudent();
                        break;
                    case "5":
                        viewAllStudents();
                        break;
                    case "6":
                        sortByName();
                        break;
                    case "7":
                        sortByGrade();
                        break;
                    case "8":
                        showStatistics();
                        break;
                    case "9":
                        running = confirmExit();
                        break;
                    default:
                        JOptionPane.showMessageDialog(null,
                                "Invalid choice. Please select 1-9.",
                                APP_TITLE,
                                JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "An error occurred: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        showGoodbyeMessage();
    }

    /**
     * Show welcome message
     */
    private void showWelcomeMessage() {
        String message = "Welcome to Student Management System!\n\n" +
                "Current Database: " + repository.getStudentCount() + " students loaded\n" +
                "Data file: students.txt";
        JOptionPane.showMessageDialog(null, message, APP_TITLE, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Display main menu and get user choice
     */
    private String showMainMenu() {
        String menu = "╔══════════════════════════════════════╗\n" +
                "║   STUDENT MANAGEMENT SYSTEM          ║\n" +
                "╚══════════════════════════════════════╝\n\n" +
                "1. Add New Student\n" +
                "2. Delete Student\n" +
                "3. Update Student Information\n" +
                "4. Search Student by ID\n" +
                "5. View All Students\n" +
                "6. Sort Students by Name\n" +
                "7. Sort Students by Grade\n" +
                "8. View Statistics\n" +
                "9. Exit\n\n" +
                "Total Students: " + repository.getStudentCount();

        return JOptionPane.showInputDialog(null, menu, APP_TITLE, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Add a new student with form validation loop
     */
    private void addStudent() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField prelimField = new JTextField();
        JTextField midtermField = new JTextField();
        JTextField finalField = new JTextField();

        Object[] fields = {
                "Student ID:", idField,
                "Name:", nameField,
                "Email:", emailField,
                "Prelim Grade (0-100):", prelimField,
                "Midterm Grade (0-100):", midtermField,
                "Final Grade (0-100):", finalField
        };

        boolean validInput = false;
        while (!validInput) {
            int result = JOptionPane.showConfirmDialog(null, fields, "Add New Student",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
                return; // User cancelled
            }

            try {
                String id = idField.getText();
                String name = nameField.getText();
                String email = emailField.getText();
                double prelim = Double.parseDouble(prelimField.getText());
                double midterm = Double.parseDouble(midtermField.getText());
                double finalGrade = Double.parseDouble(finalField.getText());

                Student student = new Student(id, name, email, prelim, midterm, finalGrade);
                repository.addStudent(student);

                JOptionPane.showMessageDialog(null,
                        "Student added successfully!\n\n" + student,
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                validInput = true; // Exit loop on success

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Invalid grade format. Please enter numbers only.\n\nPlease correct the form and try again.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                // Loop continues - form stays open
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null,
                        e.getMessage() + "\n\nPlease correct the form and try again.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                // Loop continues - form stays open
            }
        }
    }

    /**
     * Delete a student
     */
    private void deleteStudent() {
        String id = JOptionPane.showInputDialog(null,
                "Enter Student ID to delete:",
                "Delete Student",
                JOptionPane.QUESTION_MESSAGE);

        if (id != null && !id.trim().isEmpty()) {
            Student student = repository.findById(id);

            if (student == null) {
                JOptionPane.showMessageDialog(null,
                        "Student with ID '" + id + "' not found.",
                        "Not Found",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete this student?\n\n" + student,
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                if (repository.deleteStudent(id)) {
                    JOptionPane.showMessageDialog(null,
                            "Student deleted successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    /**
     * Update student information with form validation loop
     */
    private void updateStudent() {
        String id = JOptionPane.showInputDialog(null,
                "Enter Student ID to update:",
                "Update Student",
                JOptionPane.QUESTION_MESSAGE);

        if (id != null && !id.trim().isEmpty()) {
            Student student = repository.findById(id);

            if (student == null) {
                JOptionPane.showMessageDialog(null,
                        "Student with ID '" + id + "' not found.",
                        "Not Found",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            JTextField idField = new JTextField(student.getId());
            idField.setEditable(false);
            JTextField nameField = new JTextField(student.getName());
            JTextField emailField = new JTextField(student.getEmail());
            JTextField prelimField = new JTextField(String.valueOf(student.getPrelimGrade()));
            JTextField midtermField = new JTextField(String.valueOf(student.getMidtermGrade()));
            JTextField finalField = new JTextField(String.valueOf(student.getFinalGrade()));

            Object[] fields = {
                    "Student ID:", idField,
                    "Name:", nameField,
                    "Email:", emailField,
                    "Prelim Grade (0-100):", prelimField,
                    "Midterm Grade (0-100):", midtermField,
                    "Final Grade (0-100):", finalField
            };

            boolean validInput = false;
            while (!validInput) {
                int result = JOptionPane.showConfirmDialog(null, fields, "Update Student",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
                    return; // User cancelled
                }

                try {
                    String newName = nameField.getText();
                    String newEmail = emailField.getText();
                    double newPrelim = Double.parseDouble(prelimField.getText());
                    double newMidterm = Double.parseDouble(midtermField.getText());
                    double newFinal = Double.parseDouble(finalField.getText());

                    Student updatedStudent = new Student(id, newName, newEmail, newPrelim, newMidterm, newFinal);
                    repository.updateStudent(id, updatedStudent);

                    JOptionPane.showMessageDialog(null,
                            "Student updated successfully!\n\n" + updatedStudent,
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    validInput = true; // Exit loop on success

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid grade format. Please enter numbers only.\n\nPlease correct the form and try again.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                    // Loop continues - form stays open
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null,
                            e.getMessage() + "\n\nPlease correct the form and try again.",
                            "Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    // Loop continues - form stays open
                }
            }
        }
    }

    /**
     * Search for a student by ID
     */
    private void searchStudent() {
        String id = JOptionPane.showInputDialog(null,
                "Enter Student ID to search:",
                "Search Student",
                JOptionPane.QUESTION_MESSAGE);

        if (id != null && !id.trim().isEmpty()) {
            Student student = repository.findById(id);

            if (student != null) {
                JOptionPane.showMessageDialog(null,
                        "Student Found:\n\n" + student,
                        "Search Result",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Student with ID '" + id + "' not found.",
                        "Not Found",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * View all students
     */
    private void viewAllStudents() {
        List<Student> students = repository.getAllStudents();

        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No students in the database.",
                    "Empty Database",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder("Total Students: " + students.size() + "\n\n");
        for (int i = 0; i < students.size(); i++) {
            sb.append((i + 1)).append(". ").append(students.get(i)).append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(700, 400));

        JOptionPane.showMessageDialog(null, scrollPane, "All Students",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Sort and display students by name
     */
    private void sortByName() {
        List<Student> students = repository.getSortedByName();

        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No students in the database.",
                    "Empty Database",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder("Students Sorted by Name (A-Z):\n\n");
        for (int i = 0; i < students.size(); i++) {
            sb.append((i + 1)).append(". ").append(students.get(i)).append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(700, 400));

        JOptionPane.showMessageDialog(null, scrollPane, "Sorted by Name",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Sort and display students by average grade
     */
    private void sortByGrade() {
        List<Student> students = repository.getSortedByGrade();

        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No students in the database.",
                    "Empty Database",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder("Students Sorted by Average Grade (Highest First):\n\n");
        for (int i = 0; i < students.size(); i++) {
            sb.append((i + 1)).append(". ").append(students.get(i)).append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(700, 400));

        JOptionPane.showMessageDialog(null, scrollPane, "Sorted by Grade",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Show database statistics
     */
    private void showStatistics() {
        int count = repository.getStudentCount();
        double avgGrade = repository.getAverageGrade();

        String stats = String.format(
                "╔══════════════════════════════════════╗\n" +
                        "║        DATABASE STATISTICS           ║\n" +
                        "╚══════════════════════════════════════╝\n\n" +
                        "Total Students: %d\n" +
                        "Average Grade (All Students): %.2f\n" +
                        "Data File: students.txt\n" +
                        "Backup File: students_backup.txt",
                count, avgGrade);

        JOptionPane.showMessageDialog(null, stats, "Statistics",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Confirm exit
     */
    private boolean confirmExit() {
        int confirm = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to exit?\nAll data is saved to students.txt",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        return confirm != JOptionPane.YES_OPTION;
    }

    /**
     * Show goodbye message
     */
    private void showGoodbyeMessage() {
        JOptionPane.showMessageDialog(null,
                "Thank you for using Student Management System!\n" +
                        "Your data has been saved to students.txt",
                APP_TITLE,
                JOptionPane.INFORMATION_MESSAGE);
    }
}