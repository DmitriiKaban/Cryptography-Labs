package com.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Task2 {

    public static Map<Character, Integer> generateAlphabetMap(String key2) {

        Map<Character, Integer> key2Map = new HashMap<>();
        Map<Character, Boolean> usedChars = new HashMap<>();

        for (char c : key2.toCharArray()) {
            if (!usedChars.containsKey(c)) {
                usedChars.put(c, true);
                key2Map.put(c, key2Map.size());
            }
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            if (!usedChars.containsKey(c)) {
                key2Map.put(c, key2Map.size());
            }
        }

        return key2Map;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String key2;
        Map<Character, Integer> key2Map;
        int operation;

        while (true) {

            System.out.println("Choose operation (1 for Encryption, 2 for Decryption, 0 Exit): ");
            operation = scanner.nextInt();
            scanner.nextLine();

            if (operation == 0) {
                System.out.println("Exiting program.");
                return;
            }

            while (operation < 0 || operation > 2) {
                System.out.println("Invalid operation choice. Please enter 1 for Encryption, 2 for Decryption, 0 to Exit.");
                operation = scanner.nextInt();
                scanner.nextLine();
            }

            System.out.println("Enter the key (1-25): ");
            int key1 = scanner.nextInt();
            while (key1 < 1 || key1 > 25) {
                System.out.println("Invalid key. Please enter a value between 1 and 25.");
                key1 = scanner.nextInt();
            }
            scanner.nextLine();

            String input;

            do {
                System.out.println("Enter second key (at least 7 characters): ");
                key2 = readString(scanner);
            } while (key2.length() < 7);

            key2Map = generateAlphabetMap(key2);

            if (operation == 1) {
                System.out.println("Enter the message to encrypt (only letters): ");
                input = readString(scanner);
                String encryptedMessage = Task1.encrypt(input, key1, key2Map);
                System.out.println("Encrypted Message: " + encryptedMessage);

            } else if (operation == 2) {
                System.out.println("Enter the cryptogram to decrypt (only letters): ");
                input = readString(scanner);
                String decryptedMessage = Task1.decrypt(input, key1, key2Map);
                System.out.println("Decrypted Message: " + decryptedMessage);
            }
        }
    }

    private static String readString(Scanner scanner) {
        String str = scanner.nextLine();
        str = str.replaceAll("[^a-zA-Z]", "").toUpperCase();
        return str;
    }
}
