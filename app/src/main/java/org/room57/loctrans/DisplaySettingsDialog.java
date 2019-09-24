package org.room57.loctrans;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;

public class DisplaySettingsDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.display_dialog_title);

        builder.setSingleChoiceItems(Values.displaySettingsText, Values.displaySetting, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Values.displaySetting = which;

                Values.editor = Values.pref.edit();
                Values.editor.putInt("display setting", which);
                Values.editor.apply();

                dialog.dismiss();

                AppCompatDelegate.setDefaultNightMode(Values.displaySetting + 1);
            }
        });

        AlertDialog dialog = builder.create();

        return builder.create();
    }
}
