package todo;

import java.io.*;
import java.util.*;

class Task {
    String title;
    boolean completed;

    Task(String title, boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    @Override
    public String toString() {
        return title + "," + completed;
    }
}

public class ToDoList {
    static final String FILE_NAME = "tasks.txt";

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== To-Do List ===");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addTask(sc);
                case 2 -> viewTasks();
                case 3 -> markCompleted(sc);
                case 4 -> deleteTask(sc);
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void addTask(Scanner sc) throws IOException {
        System.out.print("Enter Task Title: ");
        String title = sc.nextLine();
        Task task = new Task(title, false);

        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(task.toString() + "\n");
        }
        System.out.println("Task added!");
    }

    static void viewTasks() throws IOException {
        List<Task> tasks = loadTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found!");
            return;
        }
        System.out.println("\n--- To-Do List ---");
        for (int i = 0; i < tasks.size(); i++) {
            String status = tasks.get(i).completed ? "[✔]" : "[ ]";
            System.out.println((i + 1) + ". " + status + " " + tasks.get(i).title);
        }
    }

    static void markCompleted(Scanner sc) throws IOException {
        List<Task> tasks = loadTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found!");
            return;
        }
        viewTasks();
        System.out.print("Enter task number to mark as completed: ");
        int index = sc.nextInt() - 1;

        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).completed = true;
            saveTasks(tasks);
            System.out.println("Task marked as completed!");
        } else {
            System.out.println("Invalid task number!");
        }
    }

    static void deleteTask(Scanner sc) throws IOException {
        List<Task> tasks = loadTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found!");
            return;
        }
        viewTasks();
        System.out.print("Enter task number to delete: ");
        int index = sc.nextInt() - 1;

        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            saveTasks(tasks);
            System.out.println("Task deleted!");
        } else {
            System.out.println("Invalid task number!");
        }
    }

    static List<Task> loadTasks() throws IOException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return tasks;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                tasks.add(new Task(data[0], Boolean.parseBoolean(data[1])));
            }
        }
        return tasks;
    }

    static void saveTasks(List<Task> tasks) throws IOException {
        try (FileWriter fw = new FileWriter(FILE_NAME)) {
            for (Task t : tasks) {
                fw.write(t.toString() + "\n");
            }
        }
    }
}
