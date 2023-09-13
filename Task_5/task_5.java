import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student {
    private String name;
    private String rollNumber;
    private char grade;
    private float cgpa;

    public Student(String name, String rollNumber, char grade, float cgpa) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = Character.toUpperCase(grade);
        this.cgpa = cgpa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public char getGrade() {
        return grade;
    }

    public void setGrade(char grade) {
        this.grade = Character.toUpperCase(grade);
    }

    public float getCgpa() {
        return cgpa;
    }

    public void setCgpa(float cgpa) {
        this.cgpa = cgpa;
    }

    public String toDataString() {
        return name + "," + rollNumber + "," + grade + "," + cgpa;
    }

    public static Student fromDataString(String dataString) {
        String[] parts = dataString.split(",");
        String name = parts[0];
        String rollNumber = parts[1];
        char grade = parts[2].charAt(0);
        float cgpa = Float.parseFloat(parts[3]);
        return new Student(name, rollNumber, grade, cgpa);
    }

    @Override
    public String toString() {
        return "Name: " + name + " , Roll Number: " + rollNumber + " , Grade: " + grade + " , CGPA: " + cgpa;
    }
}

class StudentManagementSystem {
    private String dataFilePath = "file.txt";

    public StudentManagementSystem() {
        readStudentsFromFile();
    }

    public void addStudent(Student student) {
        saveStudent(student);
    }

    public void removeStudent(String rollNumber) {
        removeStudentFromFile(rollNumber);
    }

    public Student searchStudent(String rollNumber) {
        List<Student> students = readStudentsFromFile();
        for (Student student : students) {
            if (student.getRollNumber().equals(rollNumber)) {
                return student;
            }
        }
        return null;
    }

    public void displayAllStudents() {
        List<Student> students = readStudentsFromFile();

        if (students.isEmpty()) {
            System.out.println("No student data available.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    private List<Student> readStudentsFromFile() {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student student = Student.fromDataString(line);
                students.add(student);
            }
        } catch (IOException e) {
            System.out.println("No data in file ..");
        }
        return students;
    }

    private void saveStudent(Student student) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFilePath, true))) {
            writer.write(student.toDataString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Unable to write data in file ..");
        }
    }

    private void removeStudentFromFile(String rollNumberToRemove) {
        List<Student> students = readStudentsFromFile();

        boolean removed = students.removeIf(student -> student.getRollNumber().equals(rollNumberToRemove));

        if (removed) {
            System.out.println("Student with roll number " + rollNumberToRemove + " removed successfully.");
            writeStudentsToFile(students);
        } else {
            System.out.println("No student with roll number " + rollNumberToRemove + " found.");
        }
    }

    private void writeStudentsToFile(List<Student> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFilePath))) {
            for (Student student : students) {
                writer.write(student.toDataString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Unable to write data in file ..");
        }
    }
}

public class task_5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManagementSystem sms = new StudentManagementSystem();

        System.out.println();
        while (true) {
            System.out.println();
            System.out.println("Student Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.println();
            System.out.println("Enter your choice: ");

            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a valid option.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Enter student name: (in String)");
                    String name = scanner.nextLine();
                    System.out.println("Enter roll number: (in String)");
                    String rollNumber = scanner.nextLine();

                    char grade = ' ';
                    try {
                        System.out.print("Enter grade: ");
                        grade = Character.toUpperCase(scanner.nextLine().charAt(0));
                    } catch (StringIndexOutOfBoundsException e) {
                        System.out.println("Invalid grade. Please enter a valid grade.");
                        continue;
                    }

                    float cgpa = 0.0f;
                    try {
                        System.out.print("Enter CGPA: ");
                        cgpa = Float.parseFloat(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid CGPA format. Please enter a valid number.");
                        continue;
                    }
                    sms.addStudent(new Student(name, rollNumber, grade, cgpa));
                    System.out.println("Student added");
                    break;
                case 2:
                    System.out.println("Enter roll number to remove: (in String)");
                    String removeRollNumber = scanner.nextLine();
                    sms.removeStudent(removeRollNumber);
                    break;
                case 3:
                    System.out.println("Enter roll number to search:  (in String)");
                    String searchRollNumber = scanner.nextLine();
                    Student searchedStudent = sms.searchStudent(searchRollNumber);
                    if (searchedStudent != null) {
                        System.out.println("Student Found: " + searchedStudent);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 4:

                    sms.displayAllStudents();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
