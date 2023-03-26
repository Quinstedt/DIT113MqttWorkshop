package com.quinstedt.speechtotext;

/*
    https://www.eclipse.org/paho/files/android-javadoc/index.html
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



    public static final String MAIN_TOPIC = "SpeechApp";
    public static final String LOCALHOST = "10.0.2.2";
    //public static final String MQTT_SERVER = "tcp://" + LOCALHOST + ":1883";

    public static final String ONLINEHOST = "broker.mqttdashboard.com";
    public static final String MQTT_SERVER = "tcp://" + LOCALHOST + ":8000";
    public static final String CLIENT_ID = "nicoDit113";
    public static final int QOS = 0;


    private boolean isConnected = false;
    public MqttClient mqttClient;
    Context context;
    TextView text;

    public BrokerConnection(Context context){
        this.context = context;
        mqttClient = new MqttClient(context, MQTT_SERVER, CLIENT_ID);
        connectToMqttBroker();
    }

    public void connectToMqttBroker() {
        if (!isConnected) {
            mqttClient.connect(CLIENT_ID, "", new IMqttActionListener() {
                /**
                 *  Add below the topic that the app subscribe to
                 *  and add the method for that topic in messageArrived(...)
                 */
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    isConnected = true;
                    final String successfulConnection = "Connected to MQTT broker";
                    Log.i(CLIENT_ID, successfulConnection);
                    Toast.makeText(context, successfulConnection, Toast.LENGTH_SHORT).show();
                    mqttClient.subscribe(MAIN_TOPIC, QOS, null);
                    mqttClient.publish(MAIN_TOPIC, "Hello World", QOS, null);

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
                 *  Method that retrieve the message inside a topic
                 *
                 *  Note: change to a switch instead for better structure
                 */
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    if(topic.equals(MAIN_TOPIC)){
                        String messageMQTT = message.toString();
                        text.setText(messageMQTT);
                        Log.i(CLIENT_ID, "Message" + messageMQTT);
                    }else {
                        Log.i(CLIENT_ID, "[MQTT] Topic: " + topic + " | Message: " + message.toString());
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d(CLIENT_ID, "Message delivered");
                }
            });
        }
    }

    /**
     * This method is use to create the message that will be publish
     * add the mMqttClient.publish(<topic>, <message >, QOS, null);
     * to specify the topic as in ControlPad or Joystick Class
     *
     * @param message - the message that we send to the broker
     * @param actionDescription - the action description that will be printed
     */
    public void publishMqttMessage(String message, String actionDescription) {
        if (!isConnected) {
            final String notConnected = "Not connected (yet)";
            Log.e(CLIENT_ID, notConnected);
            Toast.makeText(context, notConnected, Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i(CLIENT_ID, actionDescription);
    }


    public TextView getMessage() {
        return this.text;
    }
    public MqttClient getMqttClient() {
        return mqttClient;
    }



}
