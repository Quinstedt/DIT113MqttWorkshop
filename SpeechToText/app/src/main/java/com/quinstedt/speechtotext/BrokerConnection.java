package com.quinstedt.speechtotext;

/*
    This class handles the broker connection of the app.
 */

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class BrokerConnection extends AppCompatActivity {

    /* TODO:
        define a topic for the hello world message
     */
    public static final String SUB_TOPIC = ""; // topic to subscribe to


    public static final String LOCALHOST = "10.0.2.2"; // Ip address of the local host
    private static final String MQTT_SERVER = "tcp://" + LOCALHOST + ":1883";   // the server uses tcp protocol on the local host ip and listens to the port 1883
    public static final String CLIENT_ID = "DIT113-SpeechToText";   // the app client ID name
    public static final int QOS = 0;    // quality of service

    private boolean isConnected = false;
    private MqttClient mqttClient;
    Context context;
    TextView connectionMessage;

    public BrokerConnection(Context context){
        this.context = context;
        mqttClient = new MqttClient(context, MQTT_SERVER, CLIENT_ID);
        connectToMqttBroker();
    }

    public void connectToMqttBroker() {
        if (!isConnected) {
            mqttClient.connect(CLIENT_ID, "", new IMqttActionListener() {

                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    isConnected = true;
                    final String successfulConnection = "Connected to MQTT broker";
                    Log.i(CLIENT_ID, successfulConnection);
                    Toast.makeText(context, successfulConnection, Toast.LENGTH_LONG).show();
                    /* TODO
                        Subscribe to the connection topic.
                        Note: set the subscriptionCallback to null
                    */

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    final String failedConnection = "Failed to connect to MQTT broker";
                    Log.e(CLIENT_ID, failedConnection);
                    Toast.makeText(context, failedConnection, Toast.LENGTH_SHORT).show();
                }
            }, new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    isConnected = false;

                    final String connectionLost = "Connection to MQTT broker lost";
                    Log.w(CLIENT_ID, connectionLost);
                    Toast.makeText(context, connectionLost, Toast.LENGTH_SHORT).show();
                }

                /**
                 * Function that handles the messages received from the broker
                 * @param topic- the topic that has been received
                 * @param message - the message received
                 * @throws Exception
                 */

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    /* TODO
                        The code commented below is missing the if-condition.
                        Uncomment the code provide the correct if-condition so that
                        the connectionMessage can be display.
                     */

                   /* if()){
                        String messageMQTT = message.toString();
                        connectionMessage.setText(messageMQTT);
                        Log.i(CLIENT_ID, "Message" + messageMQTT);  // prints in the console
                    }else {
                        // prints in the console
                        Log.i(CLIENT_ID, "[MQTT] Topic: " + topic + " | Message: " + message.toString());
                    }*/
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d(CLIENT_ID, "Message delivered");
                }
            });
        }
    }

    /**
     * Function that set the textview reference from the MainActivity to the connectionMessage textview
     * in the brokerConnection.
     * @param textView
     */
    public void setConnectionMessage(TextView textView) {
        this.connectionMessage = textView;
    }

    public MqttClient getMqttClient() {
        return mqttClient;
    }





}
