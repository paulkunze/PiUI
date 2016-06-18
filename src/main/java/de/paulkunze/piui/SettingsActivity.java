package de.paulkunze.piui;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.paulkunze.piui.mqtt.UserData;

/**
 * The class for the settings activity contains methods for displaying and saving the user settings.
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GuiUpdater.setActivity(this);
        displaySettings();
    }

    /**
     * Load and display the user settings
     */
    public void displaySettings() {
        ((EditText) findViewById(R.id.host)).setText(UserData.host);
        ((EditText) findViewById(R.id.port)).setText(UserData.port);
        ((EditText) findViewById(R.id.client)).setText(UserData.clientName);
        ((EditText) findViewById(R.id.topic_in)).setText(UserData.topicIn);
        ((EditText) findViewById(R.id.topic_out)).setText(UserData.topicOut);
    }

    /**
     * Save the user settings
     *
     * @param view Needed by the button, not used.
     */
    public void saveSettings(View view) {
        EditText host = (EditText) findViewById(R.id.host);
        UserData.host = host.getText().toString();
        EditText port = (EditText) findViewById(R.id.port);
        UserData.port = port.getText().toString();
        EditText client = (EditText) findViewById(R.id.client);
        UserData.clientName = client.getText().toString();
        EditText topicIn = (EditText) findViewById(R.id.topic_in);
        UserData.topicIn = topicIn.getText().toString();
        EditText topicOut = (EditText) findViewById(R.id.topic_out);
        UserData.topicOut = topicOut.getText().toString();
        UserData.saveSettings(getSharedPreferences("SETTINGS", Context.MODE_PRIVATE));
        // confirm
        GuiUpdater.makeToast(getResources().getString(R.string.toast_saved));
        // go back
        finish();
    }

    public void saveScript(View view) {
        try {
            exportFile();
        } catch (IOException e) {
            GuiUpdater.makeToast(e.getLocalizedMessage());
        }
    }

    private void exportFile() throws IOException {
        InputStream in = getResources().openRawResource(R.raw.piui);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File file = new File(extStorageDirectory, "piui.py");
        Log.e("1", file.getAbsolutePath());
        FileOutputStream out = new FileOutputStream(file);
        byte[] buff = new byte[1024];
        int read = 0;

        try {
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } finally {
            in.close();
            out.close();
            GuiUpdater.makeToast(getResources().getString(R.string.script_saved));
        }
//
//
//
//        outStream.write();
//        outStream.flush();
//        outStream.close();
//
//
//        FileChannel inChannel = null;
//        FileChannel outChannel = null;
//
//        InputStream scriptIn = getResources().openRawResource(
//                getResources().getIdentifier("piui", "raw", getPackageName()));
//
//
//
//            inChannel = ((FileInputStream) scriptIn).getChannel();
//            outChannel = scriptOut.getChannel();
//
//        try {
//            FileOutputStream scriptOut = openFileOutput("piui.py", Context.MODE_PRIVATE);
//            scriptOut.write(scriptIn);
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

}
