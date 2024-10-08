package com.app.lab2;

import javafx.beans.property.*;

public class LetterFrequency {
    private final StringProperty letter;
    private final IntegerProperty occurrences;
    private final DoubleProperty frequency;
    private final StringProperty substitution;

    public LetterFrequency(String letter, int occurrences, double frequency) {
        this.letter = new SimpleStringProperty(letter);
        this.occurrences = new SimpleIntegerProperty(occurrences);
        this.frequency = new SimpleDoubleProperty(frequency);
        this.substitution = new SimpleStringProperty("");
    }

    public LetterFrequency(String letter, double frequency) {
        this.letter = new SimpleStringProperty(letter);
        this.occurrences = new SimpleIntegerProperty(0);
        this.frequency = new SimpleDoubleProperty(frequency);
        this.substitution = new SimpleStringProperty("");
    }

    public String getLetter() {
        return letter.get();
    }

    public void setLetter(String letter) {
        this.letter.set(letter);
    }

    public StringProperty letterProperty() {
        return letter;
    }

    public int getOccurrences() {
        return occurrences.get();
    }

    public void setOccurrences(int occurrences) {
        this.occurrences.set(occurrences);
    }

    public IntegerProperty occurrencesProperty() {
        return occurrences;
    }

    public double getFrequency() {
        return frequency.get();
    }

    public void setFrequency(double frequency) {
        this.frequency.set(frequency);
    }

    public DoubleProperty frequencyProperty() {
        return frequency;
    }

    public String getSubstitution() {
        return substitution.get();
    }

    public void setSubstitution(String substitution) {
        this.substitution.set(substitution);
    }

    public StringProperty substitutionProperty() {
        return substitution;
    }
}

