package com.quinstedt.speechtotext;

import static com.quinstedt.speechtotext.MainActivity.findColor;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleUnitTest {

    @Test
    public void testCheckCommandMethod_LongString() {
        String input = "set the color to Blue to the wio color";
        assertEquals(Colors.BLUE.getName(), findColor(input));
    }

    @Test
    public void testCheckCommandMethod_short_And_UpperCase() {
        String input = "set BLACK";
        String a =  findColor(input);
        assertEquals(Colors.BLACK.getName(),a);
    }

    @Test
    public void testCheckCommandMethod_short_And_LowerCase() {
        String input = "red";
        assertEquals(Colors.RED.getName(), findColor(input));
    }

}