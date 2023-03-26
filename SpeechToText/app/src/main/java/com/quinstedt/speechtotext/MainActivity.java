package com.quinstedt.speechtotext;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected static final int RESULT_SPEECH = 1;
    private ImageButton speakBtn;
    private TextView textDisplay;
    private TextView sentMessage;
    private BrokerConnection brokerConnection;
    int QOS = 1;
    public static final String MAIN_TOPIC = "SpeechApp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textDisplay = findViewById(R.id.textDisplay);
        sentMessage = findViewById(R.id.sentMessage);
        speakBtn = findViewById(R.id.speakBtn);

        brokerConnection = new BrokerConnection(getApplicationContext());
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
            // verify the string using helper method that will also publish the result
            checkCommand(textDisplay.toString());
        }
    }

    protected  void checkCommand(String message){
        String errorMessage = "Error: Could not get a color";

        String match = "default";
        for(Colors color: Colors.values()){
            if(message.toLowerCase().contains(color.getName())){
                match = color.getValue();
            }
        }

        if(!match.equals("default")){
            brokerConnection.mqttClient.publish(MAIN_TOPIC, match, QOS, null);
            String messageFormat = "Sent: " + match;
            sentMessage.setText(messageFormat);
        }else{
            Toast.makeText(getApplicationContext(), match, Toast.LENGTH_LONG).show();
            sentMessage.setText(errorMessage);
        }
    }
}
