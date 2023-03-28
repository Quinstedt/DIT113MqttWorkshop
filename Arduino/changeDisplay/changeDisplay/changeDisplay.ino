#include <rpcWiFi.h>
#include"TFT_eSPI.h"
#include <PubSubClient.h>

// Update these with values suitable for your network.
// Home Wifi
const char* ssid = "****"; // WiFi Name
const char* password = "*****";  // WiFi Password


/**********  HOW TO FIND YOUR MOSQUITTO BROKER ADDRESS*******************
  In Windows command prompt, use the command:   ipconfig
  Copy the Ip address of "Wireless LAN adapter Wi-Fi: IPv4 Address"
  Enter the IP in the sever variable below.
  
*************************************************************************/

const char* server = "***";  // MQTT Broker URL

/* TODO
    add the corresponding topics
*/ 
const char* TOPIC_sub = "";
const char* TOPIC_pub_connection = "";


TFT_eSPI tft;

WiFiClient wioClient;
PubSubClient client(wioClient);
long lastMsg = 0;
char msg[50];
int value = 0;


void setup_wifi() {

  delay(10);

  tft.setTextSize(2);
  tft.setCursor((320 - tft.textWidth("Connecting to Wi-Fi..")) / 2, 120);
  tft.print("Connecting to Wi-Fi..");

  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  WiFi.begin(ssid, password); // Connecting WiFi

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");

  tft.fillScreen(TFT_BLACK);
  tft.setCursor((320 - tft.textWidth("Connected!")) / 2, 120);
  tft.print("Connected!");

  Serial.println("IP address: ");
  Serial.println(WiFi.localIP()); // Display Local IP Address
}

String getPayload(){

}

void callback(char* topic, byte* payload, unsigned int length) {
  tft.fillScreen(TFT_BLACK);
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
// process payload and convert it to a string
  char buff_p[length];
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
    buff_p[i] = (char)payload[i];
  }
  Serial.println();
  buff_p[length] = '\0';
  String message = String(buff_p);
// end of conversion
  /***************  Action with topic and messages ***********/
  setColorAndPrintMessage(message);

}

void setColorAndPrintMessage(String message) {
  int bgColor;    // declare a backgroundColor
  int textColor = TFT_WHITE;    // initializee the text color to white
  String displayText = "Received message:";

  // Set background color based on input string
  /* TODO
     implement the color changes of the display depending on the command received
     Use: https://wiki.seeedstudio.com/Wio-Terminal-LCD-Basic
  */
  
  // Update TFT display and print input message
  tft.fillScreen(bgColor);    // Fill the screen with the background color
   tft.setTextColor(textColor, bgColor);    // set the text and background color                   
  tft.setTextSize(2);                       // set the size of the text
  tft.setCursor((320 - tft.textWidth(displayText)) / 2, 90);    // Make sure to align the text to the center of the screen
  tft.println(displayText);     // print the text
  tft.setCursor((320 - tft.textWidth(message)) / 2, 120);         
  tft.println(message);

}

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Create a random client ID
    String clientId = "WioTerminal";
    // Attempt to connect
    if (client.connect(clientId.c_str())) {
      Serial.println("connected");
      // Once connected, publish an announcement...
      client.publish(TOPIC_pub_connection, "hello world");
      Serial.println("Published connection message ");
      // ... and resubscribe
      client.subscribe(TOPIC_sub);
      Serial.print("Subcribed to: ");
      Serial.println(TOPIC_sub);
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}

void setup() {

  tft.begin();
  tft.fillScreen(TFT_BLACK);
  tft.setRotation(3);


  Serial.println();
  Serial.begin(115200);
  setup_wifi();
  client.setServer(server, 1883); // Connect the MQTT Server   hive_mqtt_server
  client.setCallback(callback);
}

void loop() {


  if (!client.connected()) {
    reconnect();
  }
  client.loop();
}