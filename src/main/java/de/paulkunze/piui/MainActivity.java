package de.paulkunze.piui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

/* Entry point for the app. Shows a start screen with a logo and some buttons */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        enterNameOnFirstRun();
    }

    /**
     * Checks if the app was started before and shows a dialog for entering the user name if not.
     */
    public void enterNameOnFirstRun() {
        boolean isFirstRun = getSharedPreferences("FIRST_RUN", Activity.MODE_PRIVATE)
                .getBoolean("isFirstRun", true);
        if (isFirstRun){
            // let the user enter his name
            new EnterNameDialog().show(getFragmentManager(), "enterName");
            // save the name
            getSharedPreferences("FIRST_RUN", Activity.MODE_PRIVATE)
                    .edit()
                    .putBoolean("isFirstRun", false)
                    .apply();
        }
    }

    public void showConsole(View view){
        startActivity(new Intent(this, ConsoleActivity.class));
    }

    public void showSettings(View view){
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void showCredits(View view){
        startActivity(new Intent(this, CreditsActivity.class));
    }

}
