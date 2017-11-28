package org.twinnation.doodle;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements FileNamePickerDialog.IFileNamePicker {

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
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 53523);
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
                Toast.makeText(this, R.string.mode_set + canvasView.cycleNextMode(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.setFileName:
                FileNamePickerDialog fileNamePickerDialog = new FileNamePickerDialog();
                fileNamePickerDialog.show(getSupportFragmentManager(), "bla bla bla bla");
                fileNamePickerDialog.attach(MainActivity.this);
                break;

        }
        return true;
    }


    @Override
    public void onFileNamePicked(String fileName) {
        // TODO: change file name
        Toast.makeText(this, "Set file name to "+ fileName, Toast.LENGTH_LONG).show();
    }
}
