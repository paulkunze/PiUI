import paho.mqtt.client as mqtt
import subprocess

# The callback for when the client receives a CONNACK response from the server.
def on_connect(client, userdata, flags, rc):
    print("Connected with result code "+str(rc))

    # Subscribing in on_connect() means that if we lose the connection and
    # reconnect then subscriptions will be renewed.
    #client.subscribe("$PIUI/#")
    client.subscribe("PIUI/OUT")

# The callback for when a PUBLISH message is received from the server.
def on_message(client, userdata, msg):
    print(msg.topic+" "+str(msg.payload))
    try:
        output = subprocess.check_output(msg.payload)
        output = str(output, 'utf-8')
    except:
        output = "command not found"

    print(output)
    client.publish("PIUI/IN", payload=output, qos=0, retain=False)



client = mqtt.Client(client_id="piui1")

client.on_connect = on_connect
client.on_message = on_message

client.connect("msggwt1.service.deutschebahn.com", 1905, 60)

# Blocking call that processes network traffic, dispatches callbacks and
# handles reconnecting.
# Other loop*() functions are available that give a threaded interface and a
# manual interface.
client.loop_forever()