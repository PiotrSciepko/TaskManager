package com.PiotrS;

import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    private static final String PROJECT_FILE = "tasks.csv";
    private static final List<String> TASKS = importProjectFile();

    public static void main(String[] args) {
        mainMenu();
    }

    private static List<String> importProjectFile() {
        Path path = Paths.get(PROJECT_FILE);
        List<String> tasks = new ArrayList<>();
        try {
            tasks = Files.readAllLines(path);
            System.out.println("Project file successfully imported...");
        } catch (IOException e) {
            System.out.println("Cannot import data");
        }
        return tasks;
    }

    private static void exportProjectFile() {
        Path path = Paths.get(PROJECT_FILE);
        try {
            Files.write(path, TASKS);
        } catch (IOException e) {
            System.out.println("Cannot export data");
        }
    }

    private static void mainMenu() {
        List<String> menuItems = List.of("add", "remove", "list", "exit");
        System.out.println(ConsoleColors.BLUE + "Please select an option:");
        System.out.print(ConsoleColors.RESET);
        for (String item : menuItems) {
            System.out.println(item);
        }

        Scanner sc = new Scanner(System.in);
        String s = "";
        while (!menuItems.contains(s)) {
            s = sc.next().toLowerCase();
            switch (s) {
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask();
                    break;
                case "list":
                    displayListOfTasks();
                    break;
                case "exit":
                    System.out.println(ConsoleColors.RED + "bye, bye");
                    exportProjectFile();
                    break;
                default:
                    System.out.println("Please select a correct option!");
            }
        }
    }

    private static void displayListOfTasks() {
        for (int i = 0; i < TASKS.size(); i++) {
            String s = TASKS.get(i).replace(",", " ");
            System.out.print(ConsoleColors.RESET);
            System.out.println((i + 1) + " : " + s.substring(0, s.lastIndexOf(" ")) + ConsoleColors.RED + s.substring(s.lastIndexOf(" ")));
        }
        mainMenu();
    }

    private static void addTask() {
        Scanner sc = new Scanner(System.in);
        String newTask = "";
        System.out.println("Please add task description:");
        newTask += sc.nextLine() + ", ";
        System.out.println("Please add task due date:");
        newTask += sc.nextLine() + ", ";
        System.out.println("Is your task important? (true/false)");
        newTask += sc.nextLine();
        TASKS.add(newTask);
        exportProjectFile();
        mainMenu();
    }

    private static void removeTask() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please select number to remove:");

        while (true) {
            String s = sc.next();
            if (StringUtils.isNumeric(s)) {
                int taskToBeRemoved = Integer.parseInt(s);
                if (taskToBeRemoved >= 1 && taskToBeRemoved <= TASKS.size()) {
                    TASKS.remove(taskToBeRemoved - 1);
                    System.out.println("Value has been successfully deleted");
                    break;
                } else if (taskToBeRemoved == 0) {
                    break;
                } else {
                    System.out.println("Select number (1 - " + TASKS.size() + ")" + "... or press 0 to cancel");
                    continue;
                }
            }
            System.out.println("Invalid data, try again... or press 0 to cancel");
        }
        mainMenu();
    }
}
