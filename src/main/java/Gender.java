package main.java;

public enum Gender {
    Male("Male"), Female("Female");
    private final String text;


    Gender(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
