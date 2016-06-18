package de.paulkunze.piui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import de.paulkunze.piui.mqtt.Client;
import de.paulkunze.piui.mqtt.UserData;

/* Displays a TextView and an EditText like a console for sending commands to the pi. */
public class ConsoleActivity extends AppCompatActivity {

    /** The client for connecting to the MQTT broker. */
    private static Client client = new Client();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // load the user settings
        UserData.loadSettings(getSharedPreferences("SETTINGS", Context.MODE_PRIVATE));

        // initialize the history display
        TextView consoleView = (TextView) findViewById(R.id.console);
        if (savedInstanceState != null) {   // reload after rotation
            String consoleText = savedInstanceState.getString("console");
            consoleView.setText(consoleText);
        }else {
            consoleView.append(UserData.clientName + "@pi:/$ ");
        }

        // init the GuiUpdater by passing the context and references to the log
        GuiUpdater.setActivity(this);
        GuiUpdater.setConsoleView(consoleView);
    }

    public void sendCommand(View view){
        EditText input = (EditText) findViewById(R.id.input);
        String command = input.getText().toString();
        GuiUpdater.updateConsoleWithCommand(command);
        client.publish(UserData.topicOut, command);
        input.setText("");
    }

    /**
     * Reconnects to the MQTT broker for receiving and sending messages.
     */
    @Override
    public void onResume() {
        super.onResume();

        if (isNetworkAvailable()) {
            client.connect();
        } else {
            GuiUpdater.makeToast(getResources().getString(R.string.no_internet));
        }
    }

    /**
     * Checks if the device is connected to the internet.
     * @return True if connected.
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Disconnects the app from the MQTT broker.
     * This is made for saving power. It is possible to stay connected.
     */
    @Override
    public void onPause() {
        super.onPause();
        client.disconnect();
    }

    /**
     * Saves the displayed log and the displayed image for a reload in onCreate.
     * This is needed for remaining the state after rotating or pausing the app.
     * @param savedState Contains the saved data about the state.
     */
    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        TextView consoleView = (TextView) findViewById(R.id.console);
        String currentConsoleText = consoleView.getText().toString();
        savedState.putString("console", currentConsoleText);
    }
}
