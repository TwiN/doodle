package org.twinnation.doodle.view;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import org.twinnation.doodle.R;


public class ColorPickerDialog extends DialogFragment {

    private IColorPicker client;
    private Dialog dialog;


    public interface IColorPicker {
        void onColorPicked(int color);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.color_picker, null);

        dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.pick_color)
                .setNegativeButton(R.string.cancel, null)
                .setView(view)
                .create();

        createClickListenerForColor(view, R.id.color_blue, Color.BLUE);
        createClickListenerForColor(view, R.id.color_green, Color.GREEN);
        createClickListenerForColor(view, R.id.color_red, Color.RED);
        createClickListenerForColor(view, R.id.color_black, Color.BLACK);
        createClickListenerForColor(view, R.id.color_purple, 0xFFAA66CC);

        return dialog;
    }


    private void createClickListenerForColor(View view, int btnId, final int color) {
        view.findViewById(btnId).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                client.onColorPicked(color);
                dialog.dismiss();
            }
        });
    }


    public void attach(IColorPicker client) {
        this.client = client;
    }

}
