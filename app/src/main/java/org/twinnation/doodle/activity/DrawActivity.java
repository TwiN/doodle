package org.twinnation.doodle.activity;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.twinnation.doodle.R;
import org.twinnation.doodle.fragment.BottomToolBarFragment;
import org.twinnation.doodle.fragment.ColorPickerDialog;
import org.twinnation.doodle.fragment.FileNamePickerDialog;
import org.twinnation.doodle.view.CanvasView;


public class DrawActivity extends AppCompatActivity implements FileNamePickerDialog.IFileNamePicker, ColorPickerDialog.IColorPicker {

    private CanvasView canvasView;
    private BottomToolBarFragment bottomToolBarFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestNeededPermissions();
        canvasView = (CanvasView)findViewById(R.id.canvas);
        bottomToolBarFragment = (BottomToolBarFragment)getFragmentManager().findFragmentById(R.id.bottomBar);
        bottomToolBarFragment.setCanvasView(canvasView);
    }


    private void requestNeededPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 53523);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.mode:
                Toast.makeText(this, getApplicationContext().getString(R.string.mode_set) + ": "
                        + canvasView.cycleNextMode(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.setFileName:
                FileNamePickerDialog fileNamePickerDialog = new FileNamePickerDialog();
                fileNamePickerDialog.show(getSupportFragmentManager(), "fileNamePickerDialog");
                fileNamePickerDialog.attach(DrawActivity.this);
                break;
            case R.id.setColor:
                ColorPickerDialog colorPickerDialog = new ColorPickerDialog();
                colorPickerDialog.show(getSupportFragmentManager(), "colorPickerDialog");
                colorPickerDialog.attach(DrawActivity.this);
                break;
        }
        return true;
    }


    @Override
    public void onFileNamePicked(String fileName) {
        canvasView.setCustomFileName(fileName);
        Toast.makeText(this, getApplicationContext().getString(R.string.set_file_name) + ": "
                + fileName, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onColorPicked(int color) {
        canvasView.setCurrentColor(color);
    }


    public CanvasView getCanvasView() {
        return canvasView;
    }

}
