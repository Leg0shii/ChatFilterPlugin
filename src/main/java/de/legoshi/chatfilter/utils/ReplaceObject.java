package de.legoshi.chatfilter.utils;

public class ReplaceObject {

    private char value;
    private int position;

    /**
     * No usage yet. Will be used to detect spaces with dots.
     * @param value char value of string
     * @param position position of char in string
     */
    public ReplaceObject(char value, int position) {
        this.value = value;
        this.position = position;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
