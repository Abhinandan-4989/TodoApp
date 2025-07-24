package com.todoapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;



public class App {
    static final String FILE_NAME = "tasks.txt";

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Add Task\n2. List Tasks\n3. Mark Done\n4. Delete Task\n5. Exit");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1: addTask(sc); break;
                case 2: listTasks(); break;
                case 3: markDone(sc); break;
                case 4: deleteTask(sc); break;
                case 5: System.exit(0);
                default: System.out.println("Invalid Choice!");
            }
        }
    }

    static void addTask(Scanner sc) throws IOException {
        System.out.print("Enter task: ");
        String task = sc.nextLine();
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
        writer.write("[ ] " + task + "\n");
        writer.close();
    }

    static void listTasks() throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No tasks yet!");
            return;
        }
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
        String line;
        int count = 1;
        while ((line = reader.readLine()) != null) {
            System.out.println(count++ + ". " + line);
        }
        reader.close();
    }

    static void markDone(Scanner sc) throws IOException {
        listTasks();
        System.out.print("Enter task number to mark done: ");
        int taskNum = Integer.parseInt(sc.nextLine());

        List<String> tasks = Files.readAllLines(new File(FILE_NAME).toPath());
        if (taskNum <= 0 || taskNum > tasks.size()) {
            System.out.println("Invalid task number.");
            return;
        }
        String task = tasks.get(taskNum - 1);
        if (task.startsWith("[ ]")) {
            tasks.set(taskNum - 1, task.replace("[ ]", "[x]"));
        }

        Files.write(new File(FILE_NAME).toPath(), tasks);
    }

    static void deleteTask(Scanner sc) throws IOException {
        listTasks();
        System.out.print("Enter task number to delete: ");
        int taskNum = Integer.parseInt(sc.nextLine());

        List<String> tasks = Files.readAllLines(new File(FILE_NAME).toPath());
        if (taskNum <= 0 || taskNum > tasks.size()) {
            System.out.println("Invalid task number.");
            return;
        }
        tasks.remove(taskNum - 1);
        Files.write(new File(FILE_NAME).toPath(), tasks);
    }
}
