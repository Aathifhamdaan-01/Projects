package aathif;
import java.io.*;
import java.util.*;

public class StudentManagement {

    static final String FILE_NAME = "students.txt";

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addStudent(sc);
                case 2 -> viewStudents();
                case 3 -> updateStudent(sc);
                case 4 -> deleteStudent(sc);
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void addStudent(Scanner sc) throws IOException {
        System.out.print("Enter Roll No: ");
        String rollNo = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Marks: ");
        int marks = sc.nextInt();
        sc.nextLine();

        StudentRecord student = new StudentRecord(rollNo, name, marks);
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(student.toString() + "\n");
        }
        System.out.println("Student added successfully!");
    }

    static void viewStudents() throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists() || file.length() == 0) {
            System.out.println("No students found!");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\nRoll No | Name | Marks");
            System.out.println("-----------------------");
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println(data[0] + " | " + data[1] + " | " + data[2]);
            }
        }
    }

    static void updateStudent(Scanner sc) throws IOException {
        System.out.print("Enter Roll No to update: ");
        String rollNo = sc.nextLine();
        List<StudentRecord> students = loadStudents();
        boolean found = false;

        for (StudentRecord s : students) {
            if (s.rollNo.equals(rollNo)) {
                System.out.print("Enter new Name: ");
                s.name = sc.nextLine();
                System.out.print("Enter new Marks: ");
                s.marks = sc.nextInt();
                sc.nextLine();
                found = true;
                break;
            }
        }
        if (found) {
            saveStudents(students);
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Student not found!");
        }
    }

    static void deleteStudent(Scanner sc) throws IOException {
        System.out.print("Enter Roll No to delete: ");
        String rollNo = sc.nextLine();
        List<StudentRecord> students = loadStudents();
        boolean removed = students.removeIf(s -> s.rollNo.equals(rollNo));

        if (removed) {
            saveStudents(students);
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found!");
        }
    }

    static List<StudentRecord> loadStudents() throws IOException {
        List<StudentRecord> students = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return students;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                students.add(new StudentRecord(data[0], data[1], Integer.parseInt(data[2])));
            }
        }
        return students;
    }

    static void saveStudents(List<StudentRecord> students) throws IOException {
        try (FileWriter fw = new FileWriter(FILE_NAME)) {
            for (StudentRecord s : students) {
                fw.write(s.toString() + "\n");
            }
        }
    }
}
