package com.quinstedt.speechtotext;

public enum Colors {
    BLUE("blue", "1"),
    RED("red", "2"),
    BLACK("black", "3"),
    PURPLE("purple", "4");


    private final String name;
    private final String value;

    Colors(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
