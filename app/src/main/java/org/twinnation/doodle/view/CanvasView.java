package org.twinnation.doodle.view;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.twinnation.doodle.R;
import org.twinnation.doodle.util.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


public class CanvasView extends View {

    private Paint currentPaint;

    private Paint brush;



    private Paint background;
    private Path path;
    private Canvas canvas;

    private String mode;

    private List<Path> paths;
    private List<Paint> paints;



    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        background = new Paint();
        background.setColor(Color.WHITE);
        mode = "normal";
        paths = new ArrayList<>();
        paints = new ArrayList<>();
        path = new Path();
        brush = new Paint();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPaint(background);
        for (int i = 0; i < paths.size(); i++) {
            canvas.drawPath(paths.get(i), paints.get(i));
        }
        canvas.drawPath(path, brush);
        this.canvas = canvas;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                if (mode.equals("arc")) {
                    path.close();
                }
                paths.add(path);
                paints.add(brush);
                path = new Path();
                // initBrush();
                brush = new Paint(currentPaint);
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }


    public void clearCanvas() {
        paths.clear();
        paints.clear();
        invalidate();
    }


    public void saveDoodle(String fileName) {
        setDrawingCacheEnabled(true);
        setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = getDrawingCache();
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
            MediaScannerConnection.scanFile(getContext(), new String[]{file.getPath()}, new String[]{"image/png"}, null);
            showAlertDialog(getContext().getString(R.string.image_saved));
        } catch (Exception e) {
            showAlertDialog(getContext().getString(R.string.error) + ": "+e.getMessage());
            e.printStackTrace();
        }
    }


    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {}
        });
        builder.create().show();
    }


    public void undo() {
        if (paths.size() > 0) {
            paths.remove(paths.size()-1);
            paints.remove(paints.size()-1);
            invalidate();
        } else {
            Toast.makeText(getContext(), R.string.nothing_to_undo, Toast.LENGTH_SHORT).show();
        }
    }


    public String cycleNextMode() {
        mode = mode.equals("arc") ? "normal" : "arc";
        return mode;
    }


    public void setBrush(Paint brush, int msg) {
        this.currentPaint = new Paint(brush);
        this.brush = currentPaint;
        if (msg != 0) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

}
