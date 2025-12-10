# 🎓 Student Management System

A comprehensive Java-based Student Management System with data persistence, featuring a user-friendly JOptionPane interface for managing student records efficiently.

## 📋 Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [System Requirements](#system-requirements)
- [Installation](#installation)
- [How to Run](#how-to-run)
- [Usage Guide](#usage-guide)
- [Project Structure](#project-structure)
- [Data Storage](#data-storage)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)

## ✨ Features

### Core Functionality
- ✅ **Add Student** - Register new students with complete validation
- ✅ **Delete Student** - Remove student records with confirmation
- ✅ **Update Student** - Modify existing student information
- ✅ **Search Student** - Find students by unique ID
- ✅ **View All Students** - Display complete student roster
- ✅ **Sort by Name** - Alphabetical ordering (A-Z)
- ✅ **Sort by Grade** - Ranking by average performance
- ✅ **Statistics Dashboard** - View system metrics

### Advanced Features
- 🎯 **Three-Grade System** - Prelim, Midterm, and Final grades
- 📊 **Automatic Average Calculation** - Real-time GPA computation
- 💾 **Data Persistence** - Automatic save to `students.txt`
- 🔄 **Automatic Backup** - Creates `students_backup.txt`
- ✅ **Input Validation** - Comprehensive error checking
- 🔁 **Smart Form Handling** - Stay on form until input is correct
- 🔒 **Duplicate Prevention** - No duplicate student IDs allowed
- 📧 **Email Validation** - Proper email format enforcement

## 🛠️ Technologies Used

- **Language**: Java (JDK 8 or higher)
- **IDE**: IntelliJ IDEA 2025.2.1
- **GUI**: Java Swing (JOptionPane)
- **Data Storage**: File I/O with text files
- **Architecture**: Repository Pattern with clean separation of concerns

## 💻 System Requirements

- **Java Development Kit (JDK)**: Version 8 or higher
- **IDE**: IntelliJ IDEA (recommended) or any Java IDE
- **Operating System**: Windows, macOS, or Linux
- **Memory**: Minimum 2GB RAM
- **Storage**: 50MB free space

## 📥 Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/student-management-system.git
   cd student-management-system
   ```

2. **Open in IntelliJ IDEA**
   - Launch IntelliJ IDEA
   - Select `File > Open`
   - Navigate to the cloned project directory
   - Click `OK`

3. **Verify Project Structure**
   ```
   student-management-system/
   ├── src/
   │   ├── Student.java
   │   ├── StudentRepository.java
   │   └── StudentManager.java
   ├── students.txt (auto-generated)
   ├── students_backup.txt (auto-generated)
   └── README.md
   ```

## 🚀 How to Run

### Using IntelliJ IDEA

1. Open the project in IntelliJ IDEA
2. Navigate to `src/StudentManager.java`
3. Right-click on the file and select `Run 'StudentManager.main()'`
4. Or click the green play button (▶️) next to the `main` method

### Using Command Line

```bash
# Navigate to src directory
cd src

# Compile all Java files
javac *.java

# Run the main class
java StudentManager
```

## 📖 Usage Guide

### Adding a Student

1. Select option `1` from the main menu
2. Fill in all required fields:
   - **Student ID**: Alphanumeric characters and hyphens only
   - **Name**: Minimum 2 characters
   - **Email**: Valid email format (e.g., student@example.com)
   - **Prelim Grade**: 0-100
   - **Midterm Grade**: 0-100
   - **Final Grade**: 0-100
3. Click `OK` to submit
4. If validation fails, correct the errors and click `OK` again
5. The form stays open until all inputs are valid

### Updating a Student

1. Select option `3` from the main menu
2. Enter the Student ID to update
3. Modify the fields as needed
4. Click `OK` to save changes
5. If validation fails, correct the errors and resubmit

### Deleting a Student

1. Select option `2` from the main menu
2. Enter the Student ID to delete
3. Confirm the deletion when prompted

### Searching for a Student

1. Select option `4` from the main menu
2. Enter the Student ID
3. View the complete student information

### Viewing Statistics

1. Select option `8` from the main menu
2. View total student count and average grade

## 📁 Project Structure

### Student.java
- **Purpose**: Model class representing a student entity
- **Responsibilities**:
  - Student data encapsulation
  - Input validation (ID, name, email, grades)
  - Grade calculation (average of three grades)
  - File format conversion
  - Comparable implementation for sorting

### StudentRepository.java
- **Purpose**: Data access layer managing persistence
- **Responsibilities**:
  - File I/O operations
  - CRUD operations (Create, Read, Update, Delete)
  - Automatic backup creation
  - Data loading on startup
  - Searching and sorting logic

### StudentManager.java
- **Purpose**: Main application with user interface
- **Responsibilities**:
  - JOptionPane-based UI
  - Menu navigation
  - Form validation loops
  - User interaction handling
  - Application workflow control

## 💾 Data Storage

### File Format

Data is stored in `students.txt` using pipe-delimited format:

```
# Student Management System Data File
# Format: ID|Name|Email|PrelimGrade|MidtermGrade|FinalGrade
# Last updated: Wed Dec 11 10:30:45 CST 2024
STU001|John Doe|john.doe@example.com|85.00|90.00|88.00
STU002|Jane Smith|jane.smith@example.com|92.00|88.00|95.00
```

### Backup System

- Automatic backup created before every save operation
- Backup file: `students_backup.txt`
- Allows data recovery in case of corruption

## 📸 Screenshots

*Add screenshots of your application here*

### Main Menu
```
╔══════════════════════════════════════╗
║   STUDENT MANAGEMENT SYSTEM          ║
╚══════════════════════════════════════╝

1. Add New Student
2. Delete Student
3. Update Student Information
4. Search Student by ID
5. View All Students
6. Sort Students by Name
7. Sort Students by Grade
8. View Statistics
9. Exit

Total Students: 5
```

### Student Record Display
```
ID: STU001 | Name: John Doe | Email: john.doe@example.com 
Prelim: 85.00 | Midterm: 90.00 | Final: 88.00 | Average: 87.67
```

## 🔍 Key Features Explained

### Smart Form Validation
When a user enters invalid data, the system:
1. Shows a clear error message
2. **Keeps the form open** with entered data
3. Allows immediate correction
4. Only closes when all data is valid or user cancels

### Grade System
- **Three separate grades**: Prelim, Midterm, Final
- **Automatic averaging**: System calculates overall average
- **Individual tracking**: Each grade stored separately
- **Range validation**: All grades must be 0-100

### Data Integrity
- **Duplicate prevention**: No two students can have the same ID
- **Email validation**: Ensures proper email format
- **ID format checking**: Only alphanumeric and hyphens allowed
- **Name validation**: Minimum 2 characters required

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👤 Author

**Your Name**
- GitHub: [@Jonymar Barrete](https://github.com/jonymarbravo)
- Email: jonymarbarrete88@gmail.com

## 🙏 Acknowledgments

- Inspired by real-world student management needs
- Built with Java best practices and design patterns
- Created for educational and practical purposes

## 📞 Support

For support, email jonymarbarrete88@gmail.com or open an issue in the GitHub repository.

---

⭐ **If you find this project helpful, please give it a star!** ⭐
