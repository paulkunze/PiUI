package de.paulkunze.piui;

import android.app.Activity;
import android.content.Context;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import de.paulkunze.piui.mqtt.UserData;

/**
 * This class contains methods for updating the GUI and can be accessed from everywhere.
 */
public class GuiUpdater extends Activity {

    private static Context context;
    private static Activity view;
    private static TextView consoleView;

    /**
     * Saves references of the view and the context of the main activity (the GUI).
     * This has to be set before any other method can be called.
     * @param activity The main activity.
     */
    public static void setActivity(Activity activity){
        view = activity;
        context = view.getApplicationContext();
    }

    /**
     * Saves a reference of the ListView displaying the log.
     * @param console The TextView showing the console (the history).
     */
    public static void setConsoleView(TextView console){
        consoleView = console;
    }

    /**
     * A getter for the application view.
     * @return The view of the main activity.
     */
    public static Activity getView(){
        return view;
    }

    /**
     * A getter for the application context.
     * @return The context of the main activity.
     */
    public static Context getContext(){
        return context;
    }

    /**
     * Creates and displays a message as a Toast.
     * @param message The content to be displayed.
     */
    public static void makeToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();    // short time
    }

    public static void updateConsoleWithCommand(String command){
        // remove the line with a prompt but no command
        String currentConsoleText = consoleView.getText().toString();
        int lastPromptIndex = currentConsoleText.lastIndexOf(UserData.clientName);
        if(lastPromptIndex < 0)
            lastPromptIndex = 0;
        String cuttedText = currentConsoleText.substring(0, lastPromptIndex);
        consoleView.setText(cuttedText);
        // add the current command and a new empty prompt
        consoleView.append(UserData.clientName + "@pi:/$ " + command + "\n");

        ScrollView scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        scrollView.scrollTo(0, scrollView.getBottom());
    }

    public static void updateConsoleWithResponse(String response){
        consoleView.append(response + "\n" + UserData.clientName + "@pi:/$ ");

        ScrollView scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        scrollView.scrollTo(0, scrollView.getBottom());
    }

}
