package com.quinstedt.speechtotext;
/* TODO
    Add at least one more colors
    Choice from the list: https://wiki.seeedstudio.com/Wio-Terminal-LCD-Basic
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
