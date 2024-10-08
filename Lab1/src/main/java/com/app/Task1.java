package com.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Task1 {

    public static String encrypt(String message, int key, Map<Character, Integer> map) {
        StringBuilder result = new StringBuilder();

        Map<Integer, Character> reverseMap = getReverseMap(map);

        for (char c : message.toCharArray()) {
            if (map.containsKey(c)) {
                int originalPos = map.get(c);
                int newPos = (originalPos + key) % 26;
                result.append(reverseMap.get(newPos));
            }
        }
        return result.toString();
    }

    public static String decrypt(String cryptogram, int key, Map<Character, Integer> map) {
        StringBuilder result = new StringBuilder();

        Map<Integer, Character> reverseMap = getReverseMap(map);

        for (char c : cryptogram.toCharArray()) {
            if (map.containsKey(c)) {
                int originalPos = map.get(c);
                int newPos = (originalPos - key + 26) % 26;
                result.append(reverseMap.get(newPos));
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Character, Integer> characterIntegerMap = getMap();

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
            int key = scanner.nextInt();
            while (key < 1 || key > 25) {
                System.out.println("Invalid key. Please enter a value between 1 and 25.");
                key = scanner.nextInt();
            }
            scanner.nextLine();
            String input;

            if (operation == 1) {
                System.out.println("Enter the message to encrypt (only letters): ");
                input = readString(scanner);
                String encryptedMessage = encrypt(input, key, characterIntegerMap);
                System.out.println("Encrypted Message: " + encryptedMessage);

            } else if (operation == 2) {
                System.out.println("Enter the cryptogram to decrypt (only letters): ");
                input = readString(scanner);
                String decryptedMessage = decrypt(input, key, characterIntegerMap);
                System.out.println("Decrypted Message: " + decryptedMessage);
            }
        }
    }

    private static String readString(Scanner scanner) {
        String str = scanner.nextLine();
        str = str.replaceAll("[^a-zA-Z]", "").toUpperCase();
        return str;
    }

    private static HashMap<Character, Integer> getMap() {

        HashMap<Character, Integer> characterIntegerMap = new HashMap<>();

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < alphabet.length(); i++) {
            characterIntegerMap.put(alphabet.charAt(i), i);
        }

        return characterIntegerMap;
    }

    private static Map<Integer, Character> getReverseMap(Map<Character, Integer> map) {
        Map<Integer, Character> reverseMap = new HashMap<>();
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            reverseMap.put(entry.getValue(), entry.getKey());
        }
        return reverseMap;
    }
}
