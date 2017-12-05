package org.twinnation.doodle.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.os.Environment;

import org.twinnation.doodle.R;
import org.twinnation.doodle.util.FileUtils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by chris on 2017-12-04.
 */

public class CanvasModel {

    public static final int MAX_BRUSH_SIZE = 50;
    public static final int MIN_BRUSH_SIZE = 5;

    private int currentSize;
    private int currentColor;

    private String customFileName;
    private String mode;

    private Paint brush;

    private boolean isErasing;



    public CanvasModel() {
        currentColor = Color.BLACK;
        currentSize = 15;
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


    public Paint getBrush() {
        if (brush == null) {
            initBrush();
        }
        return brush;
    }


    public int decrementBrushSize() {
        if (currentSize > MIN_BRUSH_SIZE) {
            currentSize -= 5;
            initBrush();
        } else {
            return R.string.minimum_brush_size;
        }
        return 0;
    }


    public int incrementBrushSize() {
        if (currentSize < MAX_BRUSH_SIZE) {
            currentSize += 5;
            initBrush();
        } else {
            return R.string.maximum_brush_size;
        }
        return 0;
    }


    public String cycleNextMode() {
        this.mode = mode.equals("arc") ? "normal" : "arc";
        return mode;
    }


    public String getCustomFileName() {
        return customFileName;
    }


    public void setCustomFileName(String customFileName) {
        this.customFileName = customFileName;
    }


    public void setCurrentColor(int currentColor) {
        this.currentColor = currentColor;
        initBrush();
    }


    public void toggleEraserMode() {
        isErasing = !isErasing;
        initBrush();
    }


    public boolean isErasing() {
        return isErasing;
    }


    public String getMode() {
        return mode;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public int getCurrentColor() {
        return currentColor;
    }
}
