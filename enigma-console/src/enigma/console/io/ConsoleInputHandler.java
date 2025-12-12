package enigma.console.io;

import enigma.console.exceptions.InvalidInputFormatException;

import java.util.Scanner;

public class ConsoleInputHandler {

    private final Scanner scanner;

    public ConsoleInputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readChoice(int min, int max) {
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            throw new InvalidInputFormatException("Invalid input! Please enter a number.");
        }
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        if (choice < min || choice > max) {
            throw new InvalidInputFormatException("Invalid choice! Please choose between " + min + " and " + max);
        }
        
        return choice;
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}

