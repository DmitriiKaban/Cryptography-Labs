package com.app.lab3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VigenereCipherApp extends Application {

    private static final String RUSSIAN_ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final int ALPHABET_SIZE = RUSSIAN_ALPHABET.length();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Vigenère Cipher for Russian Alphabet");

        Label lblKey = new Label("Key (min length 7):");
        TextField txtKey = new TextField();
        txtKey.setPromptText("Enter encryption key");

        Label lblMessage = new Label("Message:");
        TextArea txtMessage = new TextArea();
        txtMessage.setPromptText("Enter your message here");

        RadioButton rbEncrypt = new RadioButton("Encrypt");
        RadioButton rbDecrypt = new RadioButton("Decrypt");
        ToggleGroup group = new ToggleGroup();
        rbEncrypt.setToggleGroup(group);
        rbDecrypt.setToggleGroup(group);
        rbEncrypt.setSelected(true);

        Button btnProcess = new Button("Process");

        Label lblResult = new Label("Result:");
        TextArea txtResult = new TextArea();
        txtResult.setEditable(false);

        VBox vbox = new VBox(10, lblKey, txtKey, lblMessage, txtMessage, rbEncrypt, rbDecrypt, btnProcess, lblResult, txtResult);
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);

        Scene scene = new Scene(vbox, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        btnProcess.setOnAction(e -> {
            String key = txtKey.getText().toUpperCase().replaceAll("[^А-ЯЁ]", "");
            String message = txtMessage.getText().toUpperCase().replaceAll("[^А-ЯЁ]", "");
            boolean isEncrypt = rbEncrypt.isSelected();

            if (key.length() < 7) {
                showAlert("Error", "Key must be at least 7 characters long!");
                return;
            }

            if (message.isEmpty()) {
                showAlert("Error", "Message cannot be empty!");
                return;
            }

            String result = isEncrypt ? encryptMessage(key, message) : decryptMessage(key, message);
            txtResult.setText(result);
        });
    }

    private String encryptMessage(String key, String message) {
        StringBuilder encryptedText = new StringBuilder();
        String preparedKey = prepareKey(key, message.length());

        for (int i = 0; i < message.length(); i++) {
            char messageChar = message.charAt(i);
            char keyChar = preparedKey.charAt(i);

            int messageIndex = RUSSIAN_ALPHABET.indexOf(messageChar);
            int keyIndex = RUSSIAN_ALPHABET.indexOf(keyChar);

            int cipherIndex = (messageIndex + keyIndex) % ALPHABET_SIZE;
            encryptedText.append(RUSSIAN_ALPHABET.charAt(cipherIndex));
        }

        return encryptedText.toString();
    }

    private String decryptMessage(String key, String message) {
        StringBuilder decryptedText = new StringBuilder();
        String preparedKey = prepareKey(key, message.length());

        for (int i = 0; i < message.length(); i++) {
            char messageChar = message.charAt(i);
            char keyChar = preparedKey.charAt(i);

            int messageIndex = RUSSIAN_ALPHABET.indexOf(messageChar);
            int keyIndex = RUSSIAN_ALPHABET.indexOf(keyChar);

            int decipherIndex = (messageIndex - keyIndex + ALPHABET_SIZE) % ALPHABET_SIZE;
            decryptedText.append(RUSSIAN_ALPHABET.charAt(decipherIndex));
        }

        return decryptedText.toString();
    }

    private String prepareKey(String key, int messageLength) {
        StringBuilder preparedKey = new StringBuilder();
        int keyLength = key.length();

        for (int i = 0; i < messageLength; i++) {
            preparedKey.append(key.charAt(i % keyLength));
        }

        return preparedKey.toString();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
