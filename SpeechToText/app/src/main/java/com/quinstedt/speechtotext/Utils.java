package com.quinstedt.speechtotext;

public class Utils {

    public static void delay(int time){
        try{
            Thread.sleep(time);
        }catch(Exception exception){
            exception.getStackTrace();
        }
    }

    public static String findColor(String message){

        String output = "";
        for(Colors color: Colors.values()){
            if(message.toLowerCase().contains(color.getName())){
                output = color.getName();
            }
        }
        return output;
    }
}
