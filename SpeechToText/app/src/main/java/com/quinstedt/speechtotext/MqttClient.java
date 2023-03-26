package com.quinstedt.speechtotext;

import android.content.Context;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
/*
    https://www.eclipse.org/paho/files/android-javadoc/index.html
    https://www.hivemq.com/blog/mqtt-client-library-enyclopedia-paho-android-service/
 */
public class MqttClient {

    private MqttAndroidClient mMqttAndroidClient;

    public MqttClient(Context context, String serverUrl, String clientId) {
        mMqttAndroidClient = new MqttAndroidClient(context, serverUrl, clientId);
    }

    public void connect(String username, String password, IMqttActionListener connectionCallback, MqttCallback clientCallback) {

        mMqttAndroidClient.setCallback(clientCallback);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);

        try {
            mMqttAndroidClient.connect(options, null, connectionCallback);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    // disconnect from mqtt broker
    public void disconnect(IMqttActionListener disconnectionCallback) {
        try {
            mMqttAndroidClient.disconnect(null, disconnectionCallback);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    // receive information
    public void subscribe(String topic, int qos, IMqttActionListener subscriptionCallback) {
        try {
            mMqttAndroidClient.subscribe(topic, qos, null, subscriptionCallback);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    // unsubscribe from a topic
    public void unsubscribe(String topic, IMqttActionListener unsubscriptionCallback) {
        try {
            mMqttAndroidClient.unsubscribe(topic, null, unsubscriptionCallback);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    // sending information
    public void publish(String topic, String message, int qos, IMqttActionListener publishCallback) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(message.getBytes());
        mqttMessage.setQos(qos);

        try {
            mMqttAndroidClient.publish(topic, mqttMessage, null, publishCallback);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}