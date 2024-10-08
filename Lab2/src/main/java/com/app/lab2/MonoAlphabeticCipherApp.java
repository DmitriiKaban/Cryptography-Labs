package com.app.lab2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class MonoAlphabeticCipherApp extends Application {
    private TextArea encryptedTextArea;
    private TextArea decryptedTextArea;
    private TableView<LetterFrequency> userFrequencyTable;
    private TableView<LetterFrequency> englishFrequencyTable;
    private Map<Character, Character> substitutions = new HashMap<>();
    private String encryptedMessage = "";

    @Override
    public void start(Stage primaryStage) {
        encryptedTextArea = new TextArea();
        encryptedTextArea.setPromptText("Enter encrypted text here...");

        decryptedTextArea = new TextArea();
        decryptedTextArea.setEditable(false);
        decryptedTextArea.setPromptText("Decrypted message will appear here...");

        userFrequencyTable = createUserFrequencyTable();
        englishFrequencyTable = createEnglishFrequencyTable();

        VBox root = new VBox(10);
        root.getChildren().addAll(new Label("Encrypted Message:"), encryptedTextArea,
                new Label("Decrypted Message:"), decryptedTextArea,
                new Label("User Frequency Table"), userFrequencyTable,
                new Label("English Frequency Table"), englishFrequencyTable);

        encryptedTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            encryptedMessage = newValue;
            updateFrequencyTable();
        });

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Monoalphabetic Cipher Decryption");
        primaryStage.show();
    }

    private TableView<LetterFrequency> createUserFrequencyTable() {
        TableView<LetterFrequency> table = new TableView<>();
        table.setEditable(true);

        TableColumn<LetterFrequency, String> letterColumn = new TableColumn<>("Letter");
        letterColumn.setCellValueFactory(cellData -> cellData.getValue().letterProperty());

        TableColumn<LetterFrequency, Integer> occurrencesColumn = new TableColumn<>("Occurrences");
        occurrencesColumn.setCellValueFactory(cellData -> cellData.getValue().occurrencesProperty().asObject());

        TableColumn<LetterFrequency, Double> frequencyColumn = new TableColumn<>("Frequency (%)");
        frequencyColumn.setCellValueFactory(cellData -> cellData.getValue().frequencyProperty().asObject());

        TableColumn<LetterFrequency, String> substitutionColumn = new TableColumn<>("Substitute");
        substitutionColumn.setCellValueFactory(cellData -> cellData.getValue().substitutionProperty());

        substitutionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        substitutionColumn.setEditable(true);
        substitutionColumn.setOnEditCommit(event -> {
            LetterFrequency frequency = event.getRowValue();
            String substitute = event.getNewValue().toUpperCase();
            if (substitute.length() == 1) {
                substitutions.put(frequency.getLetter().charAt(0), substitute.charAt(0));
                updateDecryptedText();
            }
        });

        table.getColumns().addAll(letterColumn, occurrencesColumn, frequencyColumn, substitutionColumn);
        return table;
    }


    private TableView<LetterFrequency> createEnglishFrequencyTable() {
        TableView<LetterFrequency> table = new TableView<>();
        ObservableList<LetterFrequency> englishFrequencies = FXCollections.observableArrayList(
                new LetterFrequency("E", 12.70), new LetterFrequency("T", 9.06),
                new LetterFrequency("A", 8.17), new LetterFrequency("O", 7.51),
                new LetterFrequency("I", 6.97), new LetterFrequency("N", 6.75),
                new LetterFrequency("S", 6.33), new LetterFrequency("H", 6.09),
                new LetterFrequency("R", 5.99), new LetterFrequency("D", 4.25),
                new LetterFrequency("L", 4.03), new LetterFrequency("C", 2.78),
                new LetterFrequency("U", 2.76), new LetterFrequency("M", 2.41),
                new LetterFrequency("W", 2.36), new LetterFrequency("F", 2.23),
                new LetterFrequency("G", 2.02), new LetterFrequency("Y", 1.97),
                new LetterFrequency("P", 1.93), new LetterFrequency("B", 1.49),
                new LetterFrequency("V", 0.98), new LetterFrequency("K", 0.77),
                new LetterFrequency("J", 0.15), new LetterFrequency("X", 0.15),
                new LetterFrequency("Q", 0.10), new LetterFrequency("Z", 0.07)
        );

        TableColumn<LetterFrequency, String> letterColumn = new TableColumn<>("Letter");
        letterColumn.setCellValueFactory(cellData -> cellData.getValue().letterProperty());

        TableColumn<LetterFrequency, Double> frequencyColumn = new TableColumn<>("Frequency (%)");
        frequencyColumn.setCellValueFactory(cellData -> cellData.getValue().frequencyProperty().asObject());

        table.getColumns().addAll(letterColumn, frequencyColumn);
        table.setItems(englishFrequencies);
        return table;
    }

    private void updateFrequencyTable() {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        int totalLetters = 0;

        for (char c : encryptedMessage.toCharArray()) {
            if (Character.isLetter(c)) {
                c = Character.toUpperCase(c);
                frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
                totalLetters++;
            }
        }

        ObservableList<LetterFrequency> frequencies = FXCollections.observableArrayList();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            char letter = entry.getKey();
            int occurrences = entry.getValue();
            double frequency = (occurrences / (double) totalLetters) * 100;
            frequencies.add(new LetterFrequency(String.valueOf(letter), occurrences, frequency));
        }

        userFrequencyTable.setItems(frequencies);
    }

    private void updateDecryptedText() {
        StringBuilder decryptedText = new StringBuilder();
        for (char c : encryptedMessage.toCharArray()) {
            if (Character.isLetter(c)) {
                char upperC = Character.toUpperCase(c);
                decryptedText.append(substitutions.getOrDefault(upperC, upperC));
            } else {
                decryptedText.append(c);
            }
        }
        decryptedTextArea.setText(decryptedText.toString());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
