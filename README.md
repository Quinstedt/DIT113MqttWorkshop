# DIT113MqttWorkshop
## Purpose
The goal of the workshop is to give student practical knowledge and deeper understanding on how to work with MQTT. Ensuring that they can get familirize with how to create MQTT topics and how to use them while publishing and subscribing. Moreover, the student gain a general understanding of how to handle incomming messages from the broker. 

## Description
The android app has a basic voice recognition that identifies different color commands which are then send to the Wio Terminal using MQTT
<br><br>
![ezgif com-resize](https://user-images.githubusercontent.com/90027419/228066704-302f6189-5b18-4e1e-9545-aa5c35e911e8.gif)
 
### Class diagram
### Android class diagram 
[ADD PICTURE]
### MQTT communication between Components
[ADD PICTURE]

## Tools
>  Eclipse Mosquitto broker <br>[Download here](https://mosquitto.org/download/)<br>

>  Arduino IDE <br>[WINDOWS](https://docs.arduino.cc/software/ide-v1/tutorials/Windows),  [MAC](https://docs.arduino.cc/software/ide-v1/tutorials/macOS), 
 [LINUX](https://docs.arduino.cc/software/ide-v1/tutorials/Linux) 

>  Android studio <br>[Download here](https://developer.android.com/studio)
 
## Setup Guide

| Description | Command |
|-------|---|
|Add Wifi information to the arduino code.<br> *(Doesn't work with iphone's share wifi or eduroam)*| `ssid = <WIFI name>`<br> `password = <the wifi password>`|
|Open a terminal and select the IP "Wireless LAN adapter Wi-Fi: IPv4 Address"<br> paste it in the arduino "server" variable| Windows: `ipconfig `<br> MacOS: `/sbin/ifconfig`|
|Go to the mosquitto folder and open mosquitto.config file in your computer, below "General configuration" add:| `listener 1883 0.0.0.0 ` <br> `allow_anonymous true` |
|Open terminal and navigate to mosquitto root folder | Windows: `mosquitto -c mosquitto.conf -v `<br> MacOS: `brew services start mosquitto` |
|In Android Studio, go to Device manager and download an emulator| Minimun API 30. [Download an Emulator](https://github.com/Quinstedt/DIT113MqttWorkshop/wiki/Set-up-an-Emulator)|
|Activate the microphone|Open *Extended Controls* and activate the microphone settings <br><br> ![ezgif com-video-to-gif](https://user-images.githubusercontent.com/90027419/228104899-651069f6-8368-41f3-9a11-74f43ccd4cfb.gif) |

> [Common Error With Mosquitto](https://github.com/Quinstedt/DIT113MqttWorkshop/wiki/Mosquitto-Common-Error)
<br>

# Tasks
Remember to create different issues, use labels and use feature branches :smile:

## Android studio
A few implementation are missing, they are mark with a TODO. <br>
**:bangbang::warning: NOTE:** You dont need to understand how android studio or the voice recognition works, you should focus on understanding the MQTT connection is used. I have added comments and additional documentation for those that may be interested. We will cover Android Studio during the Android studio lecture. 

- [ ] Create a class diagram for the android class in "com.quinstedt.speechtotext" folder. <br>	*You should only include the 4 classes in that folder*
- [ ] Add a picture to the README
- [ ] BrokerConnection class - define a subscription topic and subscribe to the connection
- [ ] MainActivity class - define a publish topic for the color commands and publish the color command

## Arduino IDE/Wio Terminal
- [ ] Add the topics for publish and subscribe
- [ ] Complete the implemention of the setColorAndPrintMessage(String message) function, for the color *red*, *black* and *blue* this should be done by using the incomming messages.

## Introducing a new color with it's own topic
*:bangbang::no_entry: IMPORTANT:* Dont do this task before completing the previous task.<br>
Now that you can change the color of your Wio terminal using the voice commands in your Android App :sunglasses::thumbsup:, we are going to introduce at least one more color.
The new color MUST have it's own topic (*It cannot be the exact same topic as you use for the previous colors*).
- [ ] Add the new color in the Colors enum class
- [ ] Refactor the publishColor method so that it nows can send the previous colors command AND the new color using it's own topic.
- [ ] Refactor the callback function in the your arduino code, so that it can handle the two different topics for the changes in the display color.

## Final README update
- [ ] Create a simple digram that shows the MQTT communication between components. <br> *Clarification: create a diagram that shows all three components and the topic that they subscribe and publish to.* 
- [ ] Add a picture to the README


##Contributors
.. add your team members 






