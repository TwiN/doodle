package org.twinnation.doodle.controller;

import android.Manifest;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.twinnation.doodle.R;
import org.twinnation.doodle.model.CanvasModel;
import org.twinnation.doodle.util.FileUtils;
import org.twinnation.doodle.view.BottomToolBarFragment;
import org.twinnation.doodle.view.ColorPickerDialog;
import org.twinnation.doodle.view.FileNamePickerDialog;
import org.twinnation.doodle.view.CanvasView;

import java.io.File;
import java.io.FileOutputStream;


public class DrawActivity extends AppCompatActivity implements FileNamePickerDialog.IFileNamePicker, ColorPickerDialog.IColorPicker {

    private CanvasView canvasView;
    private CanvasModel canvasModel;

    private BottomToolBarFragment bottomToolBarFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestNeededPermissions();

        canvasView = (CanvasView)findViewById(R.id.canvas);
        canvasModel = new CanvasModel();
        canvasView.setBrush(canvasModel.getBrush(), 0);
        bottomToolBarFragment = (BottomToolBarFragment)getFragmentManager().findFragmentById(R.id.bottomBar);
        initComponentsAndListeners();
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
                canvasModel.cycleNextMode();
                canvasView.setMode(canvasModel.getMode());
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
        canvasModel.setCustomFileName(fileName);
        Toast.makeText(this, getApplicationContext().getString(R.string.set_file_name) + ": "
                + fileName, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onColorPicked(int color) {
        canvasModel.setCurrentColor(color);
        canvasView.setBrush(canvasModel.getBrush(), 0);
    }


    private void initComponentsAndListeners() {
        final ImageButton clearCanvasBtn = bottomToolBarFragment.getView().findViewById(R.id.clearCanvas);
        final ImageButton saveCanvasBtn = bottomToolBarFragment.getView().findViewById(R.id.saveCanvas);
        final ImageButton undoBtn = bottomToolBarFragment.getView().findViewById(R.id.undo);
        final Button plusSize = bottomToolBarFragment.getView().findViewById(R.id.plusSize);
        final Button minusSize = bottomToolBarFragment.getView().findViewById(R.id.minusSize);
        final ImageButton eraserBtn = bottomToolBarFragment.getView().findViewById(R.id.eraser);

        clearCanvasBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                canvasView.clearCanvas();
            }
        });
        undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                canvasView.undo();
            }
        });
        saveCanvasBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                saveDoodle(canvasView.getBitmap(), canvasModel.getCustomFileName());
            }
        });
        plusSize.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                int msg = canvasModel.incrementBrushSize();
                canvasView.setBrush(canvasModel.getBrush(), msg);
            }
        });
        minusSize.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                int msg = canvasModel.decrementBrushSize();
                canvasView.setBrush(canvasModel.getBrush(), msg);
            }
        });
        eraserBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                canvasModel.toggleEraserMode();
                canvasView.setBrush(canvasModel.getBrush(), 0);
                eraserBtn.setImageResource(canvasModel.isErasing() ? R.mipmap.eraser_green : R.mipmap.eraser);
            }
        });
    }


    public void saveDoodle(Bitmap bitmap, String fileName) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Doodle";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(path + "/" + (fileName == null ? FileUtils.generateFilename() : fileName));
        try {
            file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
            ostream.flush();
            ostream.close();
            MediaScannerConnection.scanFile(getApplicationContext(), new String[]{file.getPath()}, new String[]{"image/png"}, null);
            canvasView.showAlertDialog(getApplicationContext().getString(R.string.image_saved));
        } catch (Exception e) {
            canvasView.showAlertDialog(getApplicationContext().getString(R.string.error) + ": "+e.getMessage());
            e.printStackTrace();
        }
    }

    public CanvasView getCanvasView() {
        return canvasView;
    }


    public CanvasModel getCanvasModel() {
        return canvasModel;
    }

}
