package org.twinnation.doodle.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import org.twinnation.doodle.R;
import org.twinnation.doodle.util.FileUtils;

/**
 * Created by chris on 2017-11-28.
 */
public class FileNamePickerDialog extends DialogFragment {

    private IFileNamePicker client;


    public interface IFileNamePicker {
        void onFileNamePicked(String fileName);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.file_name_picker, null);

        final EditText editText = view.findViewById(R.id.fileName);
        editText.setText(FileUtils.generateFilename());

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.change_file_name)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        client.onFileNamePicked(editText.getText().toString());
                    }
                })
                .setView(view)
                .create();

        return dialog;
    }


    public void attach(IFileNamePicker client) {
        this.client = client;
    }

}
