# PiUI

**Remote-controlling a RaspberryPi from Android via MQTT**

This app was created at the DB HackDay on the 17th and 18th of June 2016.
It can be used to remote control your RaspberryPi via MQTT with your smartphone. Just type in some commands and see the results after the execution on the Pi. It works with PubSub (Publish-Subscribe), so you can even command multiple Pis with one command at the same time.

The advantages are the following:

- You can run commands on the commandline of the Pi without attaching a keyboard and a monitor.
- You can remotely command your Pi without needing to know its IP address.
- The connection even works if the Smartphone and the Pi are in different networks.
- You can can connect multiple smartphones and multiple Pis at the same time.

To get it working, you need to start a little Python script on the RaspberryPi which is included in this app. You can copy the script to your internal memory storage from within the app.
It also requires an MQTT broker, but it is preconfigured with a public broker, so that it works out of the box.

![function](/images/01.png?raw=true)
![function](/images/02.png?raw=true)


## Installation & Usage Instructions

1. Download the app from the Google Play Store: https://play.google.com/store/apps/details?id=de.paulkunze.piui
2. Open the app and enter your name in the displayed dialog. It will be used as your client ID.
3. Go to the Settings and click the button for saving the python script to the internal memory. It will be located at the root directory of your device.
4. Copy the script to your RaspberryPi and run it. It will connect to a public MQTT broker and wait for messages in the topic PIUI/OUT.
5. Open the console activity in the Android app and type in a command. It will be sent to the topic PIUI/OUT, so that the Python script can receive it.
6. The results of the command will be displayed on the screen after being published by the Python script in the topic PIUI/IN.


## Copyright and License (BSD 2)

Copyright (c) 2016, Paul Kunze All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.