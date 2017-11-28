package org.twinnation.doodle;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import org.twinnation.doodle.util.FileUtils;

/**
 * Created by chris on 2017-11-28.
 */

public class ColorPickerDialog extends DialogFragment {

    private IColorPicker client;


    public interface IColorPicker {
        void onColorPicked(int color);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.color_picker, null);

        final EditText editText = view.findViewById(R.id.fileName);
        editText.setText(FileUtils.generateFilename());

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Pick a color")
                .setNegativeButton("Cancel", null)
                .setView(view)
                .create();

        return dialog;
    }


    public void attach(IColorPicker client) {
        this.client = client;
    }

}
