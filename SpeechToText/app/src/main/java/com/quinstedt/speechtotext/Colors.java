package com.quinstedt.speechtotext;
/* TODO
    Add at least one more colors
 */
public enum Colors {
    BLUE("blue"),
    RED("red"),
    BLACK("black");

    private final String name;

    Colors(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
