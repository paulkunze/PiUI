package de.paulkunze.piui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import de.paulkunze.piui.mqtt.UserData;

/**
 * A dialog which is shown on the very first start of the app.
 * Contains an EditText for entering and saving the user name.
 */
public class EnterNameDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View view = inflater.inflate(R.layout.dialog_enter_name, null);
        builder.setView(view)
                .setTitle(R.string.name_title)
                .setPositiveButton(R.string.name_save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // add the topic to the set and save
                        EditText etName = (EditText) view.findViewById(R.id.etName);
                        UserData.clientName = etName.getText().toString();
                        UserData.saveSettings(getActivity().getSharedPreferences("SETTINGS", Context.MODE_PRIVATE));
                    }
                });
        return builder.create();
    }
}
