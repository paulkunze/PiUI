package de.paulkunze.piui.mqtt;

import de.paulkunze.piui.GuiUpdater;
import de.paulkunze.piui.R;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

/**
 * Reacts to actions made by the client (connect, disconnect, publish and subscribe).
 * Gives the user feedback about the success of the started action.
 */
public class ActionListener implements IMqttActionListener{

    /** The action made by the client. */
    private Action action;

    /** The MQTT client which performs the actions. */
    private Client client;

    /**
     * Enum of all possible actions.
     */
    public enum Action {
        CONNECT,
        DISCONNECT,
        SUBSCRIBE,
        UNSUBSCRIBE,
        PUBLISH
    }

    /**
     * The constructor saves a reference to the client and the performed action.
     * @param client The client which performed the action.
     * @param action The performed action as an enum value.
     */
    public ActionListener(Client client, Action action) {
        this.client = client;
        this.action = action;
    }

    /**
     * Called if the action was successful. If the client connected successfully, it subscribes
     * to the necessary topics.
     * @param asyncActionToken Not used.
     */
    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        if (action == Action.CONNECT) {
            // first unsubscribe so that there are no duplicates
            client.unsubscribe(UserData.topicOut);
            // subscribe to the topic OUT
            client.subscribe(UserData.topicOut);
        }
    }

    /**
     * Called if the action failed. Prints an error message to the log.
     * @param token Not used.
     * @param exception The thrown exception, if any.
     */
    @Override
    public void onFailure(IMqttToken token, Throwable exception) {
        String act = "";
        switch (action) {
            case CONNECT :
                act = GuiUpdater.getContext().getResources().getString(R.string.connect_failed);
                break;
            case DISCONNECT :
                act = GuiUpdater.getContext().getResources().getString(R.string.disconnect_failed);
                break;
            case SUBSCRIBE :
                act = GuiUpdater.getContext().getResources().getString(R.string.subscribe_failed);
                break;
            case UNSUBSCRIBE :
                act = GuiUpdater.getContext().getResources().getString(R.string.unsubscribe_failed);
                break;
            case PUBLISH :
                act = GuiUpdater.getContext().getResources().getString(R.string.publish_failed);
                break;
        }
        try{
            act += ": " + exception.getLocalizedMessage();
            exception.printStackTrace();
        }catch (NullPointerException npe){
            // nothing (if exception is null)
        }
        GuiUpdater.makeToast(act);
    }
}
