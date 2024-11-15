package de.ostfalia.bips.ws24.camunda.database.Model;

public class Option<T> {
    private final String label;
    private final T value;

    public Option(String label, T value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public T getValue() {
        return value;
    }
}
