package de.paulkunze.piui.mqtt;

import de.paulkunze.piui.GuiUpdater;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * This class handles all received messages. The topics and contents of the messages
 * are analyzed and the GUI is updated accordingly.
 */
public class CallbackHandler implements MqttCallback{

    /**
     * Updates the GUI according to the message content and topic.
     * @param topic The topic in which the message arrived.
     * @param message The content of the arrived message.
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        if(topic.equals(UserData.topicIn)) {
            String msg = message.toString();
            GuiUpdater.updateConsoleWithResponse(msg);
        }
    }

    /**
     * Not used.
     * @param cause Not used.
     */
    @Override
    public void connectionLost(Throwable cause) {

    }

    /**
     * Not used.
     * @param token Not used.
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
