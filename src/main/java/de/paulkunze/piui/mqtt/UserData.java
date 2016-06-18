package de.paulkunze.piui.mqtt;

import android.content.SharedPreferences;

/**
 * This class contains all data which is needed for the MQTT connection and which can be edited
 * by the user. It contains the host, port and the topics as well as methods for
 * loading and saving this data.
 */
public class UserData {

    public static String host = "msggwt1.service.deutschebahn.com";
    public static String clientName = "Android";    // the prefix 'piui' is needed for clients
    public static String clientPrefix = "piui";

    public static String topicIn = "PIUI/IN";   // the prefix 'PIUI/' is needed for topics
    public static String topicOut = "PIUI/OUT";
    public static String port = "1905";

    /**
     * Saves the user data to the phone.
     * @param settings A reference to the app-private storage on the phone.
     */
    public static void saveSettings(SharedPreferences settings){
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.putString("host", host)
                .putString("clientName", clientName)
                .putString("topicIn", topicIn)
                .putString("topicOut", topicOut)
                .putString("port", port)
                .apply();
    }

    /**
     * Loads the user data from the phone.
     * @param settings A reference to the app-private storage on the phone.
     */
    public static void loadSettings(SharedPreferences settings){
        host = settings.getString("host", host);
        clientName = settings.getString("clientName", clientName);
        topicIn = settings.getString("topicIn", topicIn);
        topicOut = settings.getString("topicOut", topicOut);
        port = settings.getString("port", port);
    }
}
