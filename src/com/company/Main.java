package com.company;

import pl.coderslab.ConsoleColors;

import java.io.Console;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static String[][] tasks;

    public static void main(String[] args) {
        String input = "";
        loadFile();
        System.out.println(ConsoleColors.BLUE + "Please select an option:");
        System.out.println(ConsoleColors.WHITE + "add\nremove\nlist\nexit");
        while(!input.equals("exit")) {
            input = getInput();
            resolveInput(input);
        }
        System.out.println(ConsoleColors.RED + "Bye");
    }


    public static void loadFile() {
        Path path = Paths.get("tasks.csv");
        tasks = new String[0][0];
        try {
            int counter = 0;
            for (String line : Files.readAllLines(path)) {
                tasks = Arrays.copyOf(tasks, tasks.length + 1);
                tasks[counter] = new String[3];
                tasks[counter] = line.split(", ");
                counter++;
            }
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED_BOLD + "Can't load the file");
        }
    }


    public static String getInput(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(input);
        return input;
    }


    public static void resolveInput(String input){
        switch(input){
            case "add":
                resolveInputAdd();
                break;
            case "remove":
                resolveInputRemove();
                break;
            case  "list":
                resolveInputList();
                break;
            case "exit":
                resolveInputExit();
                break;
            default:
                System.out.println("Please type one of the following commands:\nadd\nremove\nlist\nexit");
        }
    }


    public static void resolveInputAdd(){
        Scanner scanner = new Scanner(System.in);
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        System.out.println("Please add task description");
        tasks[tasks.length - 1][0] = scanner.nextLine();
        System.out.println("Please add task due date");
        tasks[tasks.length - 1][1] = scanner.nextLine();
        System.out.println("Is your task important: true/false");
        while (true) {
            tasks[tasks.length - 1][2] = scanner.nextLine();
            if(tasks[tasks.length - 1][2].equals("true") || tasks[tasks.length - 1][2].equals("false"))
                break;
            System.out.println("Please type 'true' or 'false'");
        }
        System.out.println(String.format("Task '%s, %s, %s' successfully added",
                tasks[tasks.length - 1][0], tasks[tasks.length - 1][1], tasks[tasks.length - 1][2]));
    }


    public static void resolveInputRemove(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(String.format("Please select number to remove (from 1 to %s)", tasks.length));
        int integerInput = 0;
        while (integerInput > tasks.length || integerInput < 1){
            try{
                integerInput = scanner.nextInt();
            }catch (Exception e){
                scanner.nextLine();
                System.out.println(String.format("Please select number from 1 to %s", tasks.length));
                System.out.println("If you change your mind type 'quit'");
                if(scanner.nextLine().equals("quit"))
                {
                    System.out.println("quit");
                    return;
                }
            }
        }
        removeTask(integerInput);
    }


    public static void removeTask(int integerInput){
        String[][] bufferTasks = tasks;
        tasks = Arrays.copyOf(tasks, tasks.length - 1);
        for(int i = 0; i <= tasks.length;i++){
            if(i < integerInput - 1)
                tasks[i] = bufferTasks[i];
            if(i > integerInput - 1)
                tasks[i - 1] = bufferTasks[i];
        }
        System.out.println(String.format("Successfully removed %s position", integerInput));
    }


    public static void resolveInputList(){
        int counter = 1;
        for(String[] arr:tasks) {
            System.out.print(ConsoleColors.CYAN + counter + ": ");
            System.out.print(ConsoleColors.WHITE + arr[0] + "     ");
            System.out.print(ConsoleColors.GREEN_BRIGHT + arr[1] + "     ");
            if(arr[2].equals("true"))
                System.out.println(ConsoleColors.RED_BOLD + arr[2]);
            else
                System.out.println(ConsoleColors.WHITE + arr[2]);
            counter++;
            System.out.print(ConsoleColors.WHITE + "");
        }
    }


    public static void resolveInputExit(){
        Path path1 = Paths.get("tasks.csv");
        List<String> tasksList = new ArrayList<>();
        for(String[] part:tasks){
            tasksList.add(String.format("%s, %s, %s", part[0], part[1], part[2]));
        }
        try {
            Files.write(path1, tasksList);
        } catch (IOException ex) {
            System.out.println(ConsoleColors.RED_BOLD + "Can't save the file");
        }
    }
}
