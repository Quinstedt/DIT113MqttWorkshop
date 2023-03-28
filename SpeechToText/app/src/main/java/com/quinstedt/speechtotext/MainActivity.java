package com.quinstedt.speechtotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected static final int RESULT_SPEECH = 1;
    private ImageButton speakBtn;       // The image button for the microphone
    private TextView textDisplay;       // Displays the text input from the microphone
    private TextView outputMessage;     // Used for the hello world message
    private BrokerConnection brokerConnection;  // Declare the brokerConnection
    int QOS = 0;

    /* TODO
        define a topic to publish the color
     */
    public static final String PUB_TOPIC = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connecting the attributes to their IDs in the activity layout
        textDisplay = findViewById(R.id.textDisplay);
        outputMessage = findViewById(R.id.outputMessage);
        speakBtn = findViewById(R.id.speakBtn);

        // Creating a brokerConnection
        brokerConnection = new BrokerConnection(getApplicationContext());
        brokerConnection.setConnectionMessage(findViewById(R.id.connectionMessage));
        brokerConnection.connectToMqttBroker();

        // make the mic imageButton clickable
        speakBtn.setOnClickListener(view -> {
            // https://developer.android.com/reference/android/speech/RecognizerIntent
            // create a messaging object to request any action from another app, in this case
            // the speech recognition from google. Starts an activity that will prompt the user
            // for speech and send it through a speech recognizer
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            // intent will first inform the recognizer which speech model to prefer when perform, in this case free form
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            // then we set it up to use US English
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
            try {
                // obtain the result
                startActivityForResult(intent, RESULT_SPEECH);
                textDisplay.setText("");
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), "Your device doesn't support Speech to Text", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            // extract all the results in an string array from the intent
            ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            // display the first index of the array, which will contain our value
            textDisplay.setText(text.get(0));
            // find if a color is given and publish it to the broker
            publishColor(text.get(0));
        }
    }

    /**
     * Function that check if a color exist in the string from the microphone
     * and publish it to the broker.
     * @param message - String obtained from the microphone
     */
    public void publishColor(String message){
        String output = "";
        /* TODO
            1. Use the helper function findColor and publish the color.
            2. When the basic connection is working, add the code to handle the new color (must have it's own topic)
         */

        Log.i("Publishing Color:", output);     //Prints in the android console
    }

    /**
     * Helper function to find the colors in the string
     * @param message
     * @return
     */
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
