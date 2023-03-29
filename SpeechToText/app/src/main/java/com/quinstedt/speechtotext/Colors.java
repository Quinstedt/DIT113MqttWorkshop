package com.quinstedt.speechtotext;

public enum Colors {
    DEFAULT("" ),
    BLUE("blue"),
    RED("red"),
    BLACK("black"),
    PURPLE("purple");

    private final String name;

    Colors(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
