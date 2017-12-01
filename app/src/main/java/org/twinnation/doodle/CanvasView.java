package org.twinnation.doodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.twinnation.doodle.util.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


public class CanvasView extends View {

    public static final int MAX_BRUSH_SIZE = 50;
    public static final int MIN_BRUSH_SIZE = 5;


    private Paint brush;
    private Paint background;
    private Path path = new Path();
    private Canvas canvas;

    private List<Path> paths;
    private List<Paint> paints;

    private String mode;
    private boolean isErasing = false;

    private int currentColor = Color.BLACK;
    private int currentSize = 5;
    private String customFileName;


    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        background = new Paint();
        background.setColor(Color.WHITE);
        initBrush();
        mode = "normal";
        paths = new ArrayList<>();
        paints = new ArrayList<>();
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
                initBrush();
                path = new Path();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }


    public Canvas getCanvas() {
        return canvas;
    }


    public void clearCanvas() {
        paths.clear();
        paints.clear();
        invalidate();
    }


    public void saveDoodle() {
        setDrawingCacheEnabled(true);
        setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = getDrawingCache();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Doodle";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(path + "/" + (customFileName == null ? FileUtils.generateFilename() : customFileName));
        FileOutputStream ostream;
        try {
            file.createNewFile();
            ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
            ostream.flush();
            ostream.close();
            Toast.makeText(getContext(), R.string.image_saved, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), R.string.error + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
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


    public String getMode() {
        return mode;
    }


    public String cycleNextMode() {
        mode = mode.equals("arc") ? "normal" : "arc";
        return mode;
    }


    public void toggleEraserMode() {
        isErasing = !isErasing;
        initBrush();
    }


    public void initBrush() {
        brush = new Paint();
        if (isErasing) {
            brush.setColor(Color.WHITE);
            brush.setStyle(Paint.Style.STROKE);
            brush.setStrokeWidth(currentSize);
        } else {
            brush.setColor(currentColor);
            brush.setStyle(Paint.Style.STROKE);
            brush.setStrokeWidth(currentSize);
        }
    }


    public int getCurrentColor() {
        return currentColor;
    }


    public void setCurrentColor(int currentColor) {
        this.currentColor = currentColor;
        initBrush();
    }


    public void decrementBrushSize() {
        if (currentSize > MIN_BRUSH_SIZE) {
            currentSize -= 5;
            initBrush();
        } else {
            Toast.makeText(getContext(), R.string.minimum_brush_size, Toast.LENGTH_SHORT).show();
        }
    }


    public void incrementBrushSize() {
        if (currentSize < MAX_BRUSH_SIZE) {
            currentSize += 5;
            initBrush();
        } else {
            Toast.makeText(getContext(), R.string.maximum_brush_size, Toast.LENGTH_SHORT).show();
        }

    }


    public boolean getIsErasing() {
        return isErasing;
    }


    public int getCurrentSize() {
        return currentSize;
    }


    public String getCustomFileName() {
        return customFileName;
    }


    public void setCustomFileName(String customFileName) {
        this.customFileName = customFileName;
    }

}
