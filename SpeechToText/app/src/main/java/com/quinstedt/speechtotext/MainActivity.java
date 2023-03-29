package com.quinstedt.speechtotext;
import static com.quinstedt.speechtotext.Utils.findColor;

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
    private ImageButton speakBtn;
    private TextView textDisplay;
    private TextView outputMessage;
    //private TextView connectionMessage;
    private BrokerConnection brokerConnection;
    int QOS = 1;
    public static final String PUB_TOPIC = "SpeechApp";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textDisplay = findViewById(R.id.textDisplay);
        outputMessage = findViewById(R.id.outputMessage);
        //connectionMessage = findViewById(R.id.connectionMessage);
        speakBtn = findViewById(R.id.speakBtn);

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
            // verify the string using helper method that will also publish the result
            publishColor(text.get(0));
        }
    }


    public void publishColor(String message){
        String output = findColor(message);
        Log.i("**** COLOR *****", output);

        brokerConnection.getMqttClient().publish(PUB_TOPIC, output ,QOS, null);
    }


}
