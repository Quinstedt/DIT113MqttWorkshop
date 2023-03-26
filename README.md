# DIT113MqttWorkshop
Preparation for  Mqtt Workshop using Android Studio, Wio Terminal and Mosquitto



## (will be updated soon) adding only basic setup

#Android

Instructions: 
- Use an emulator with API 30 or API 31.
- To activate the microphone in the emulator press the 3 dots to open the extended controls
-![image](https://user-images.githubusercontent.com/90027419/227800445-f7f61290-ab6d-4362-af8e-a70c30041d37.png)

- Go to Microphone and activate all the setting for the microphone
 
- ![image](https://user-images.githubusercontent.com/90027419/227800638-b6db7133-9a02-4395-9fe5-deebf9088f43.png)


#Mosquitto

- Open the mosquitto.conf file in the mosquitto folder in your computer and add: 

`listener 1883 0.0.0.0 ` <br> 
`protocol mqtt`  <br>
`listener 9001`  <br>
`protocol websockets`<br>
`allow_anonymous true`

- Open the terminal navigate to the mosquitto and run with

`mosquitto -c mosquitto.conf -v `  


