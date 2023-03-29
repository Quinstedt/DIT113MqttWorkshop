package com.quinstedt.speechtotext;

import static com.quinstedt.speechtotext.Utils.findColor;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
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
        assertEquals("black",a);
    }

    @Test
    public void testCheckCommandMethod_short_And_LowerCase() {
        String input = "red";
        assertEquals(Colors.RED.getName(), findColor(input));
    }

}