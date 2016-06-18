package de.paulkunze.piui.mqtt;

import android.util.Log;

import de.paulkunze.piui.GuiUpdater;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * The MQTT client class which handles connecting, disconnecting, publishing and subscribing.
 */
public class Client {

    /** The Paho MQTT client for Android. */
    private static MqttAndroidClient client;

    MqttConnectOptions conOpt;
    /** The protocol used to connect to the broker. Can be tcp or ssl. */
    private static final String PROTOCOL = "tcp://";    // "ssl://"

    private void createClient(){
        conOpt  = new MqttConnectOptions();
        conOpt.setCleanSession(false);  // true -> do not maintain state after restart
        conOpt.setConnectionTimeout(1000);
        conOpt.setKeepAliveInterval(10);
        client = new MqttAndroidClient(GuiUpdater.getContext(),
                PROTOCOL + UserData.host + ":" + UserData.port,
                UserData.clientPrefix + UserData.clientName);

        client.setCallback(new CallbackHandler());
        client.setTraceCallback(new TraceHandler());
    }

    /**
     * Creates a new MQTT client, connects to the broker and sets all connection options
     */
    public void connect() {
        if (isClientConnected())
            return;

        createClient();

        try {
            if(!client.isConnected())
                client.connect(conOpt, null, new ActionListener(this, ActionListener.Action.CONNECT));
        }catch(MqttException me){
            GuiUpdater.makeToast("mqtt error: " + me.getLocalizedMessage());
            Log.e(this.getClass().getCanonicalName(), "mqtt error: " + me.getLocalizedMessage());
        }
    }

    /**
     * Disconnects the client from the server.
     */
    public void disconnect() {
        if (!isClientConnected()) {
            return;
        }
        try {
            client.disconnect(null, new ActionListener(this, ActionListener.Action.DISCONNECT));
        } catch (MqttException me) {
            GuiUpdater.makeToast("mqtt error: " + me.getLocalizedMessage());
            Log.e(this.getClass().getCanonicalName(), "mqtt error: " + me.getLocalizedMessage());
        }
    }

    public boolean subscribe(String topic) {
        try {
            if (!isClientConnected()) {
                return false;
            }
            // topic, qos, userContext, callback
            client.subscribe(topic, 2, null, new ActionListener(this, ActionListener.Action.SUBSCRIBE));
        } catch (MqttException me) {
            GuiUpdater.makeToast("mqtt error: " + me.getLocalizedMessage());
            Log.e(this.getClass().getCanonicalName(), "mqtt error: " + me.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public void unsubscribe(String topic) {
        try {
            if (!isClientConnected()) {
                return;
            }
            // topic, userContext, callback
            client.unsubscribe(topic, null, new ActionListener(this, ActionListener.Action.UNSUBSCRIBE));
        } catch (MqttException me) {
            GuiUpdater.makeToast("mqtt error: " + me.getLocalizedMessage());
            Log.e(this.getClass().getCanonicalName(), "mqtt error: " + me.getLocalizedMessage());
        }
    }

    public boolean publish(String topic, String message) {
        try {
            if (!isClientConnected()) {
                return false;
            }
            // true = retained -> later subscribers will get it
            client.publish(topic, message.getBytes(), 2, false, null,
                    new ActionListener(this, ActionListener.Action.PUBLISH));
        } catch (MqttException me) {
            GuiUpdater.makeToast("mqtt error: " + me.getLocalizedMessage());
            Log.e(this.getClass().getCanonicalName(), "mqtt error: " + me.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public boolean isClientConnected() {
        try {
            if (client != null)
                return client.isConnected();
        } catch (NullPointerException | IllegalArgumentException e) {
            // return false
            // it is an mqtt bug
        }
        return false;
    }
}


