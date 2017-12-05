package org.twinnation.doodle.model;

import android.graphics.Color;
import android.graphics.Paint;

import org.twinnation.doodle.R;


/**
 * Created by chris on 2017-12-04.
 */
public class CanvasModel {

    public static final int DEFAULT_BRUSH_SIZE  = 15;
    public static final int MAX_BRUSH_SIZE      = 50;
    public static final int MIN_BRUSH_SIZE      = 5;
    public static final int SIZE_STEP           = 5;

    public static final String MODE_NORMAL      = "normal";
    public static final String MODE_ARC         = "arc";

    private int currentSize;
    private int currentColor;

    private String customFileName;
    private String mode;

    private Paint brush;

    private boolean isErasing;


    public CanvasModel() {
        currentColor = Color.BLACK;
        currentSize = DEFAULT_BRUSH_SIZE;
        this.mode = MODE_NORMAL;
    }


    //////////
    // MISC //
    //////////


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


    public int decrementBrushSize() {
        if (currentSize > MIN_BRUSH_SIZE) {
            currentSize -= SIZE_STEP;
            initBrush();
        } else {
            return R.string.minimum_brush_size;
        }
        return 0;
    }


    public int incrementBrushSize() {
        if (currentSize < MAX_BRUSH_SIZE) {
            currentSize += SIZE_STEP;
            initBrush();
        } else {
            return R.string.maximum_brush_size;
        }
        return 0;
    }


    public String cycleNextMode() {
        this.mode = mode.equals(MODE_ARC) ? MODE_NORMAL : MODE_ARC;
        return mode;
    }


    public void toggleEraserMode() {
        isErasing = !isErasing;
        initBrush();
    }


    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////


    public Paint getBrush() {
        if (brush == null) {
            initBrush();
        }
        return brush;
    }


    public boolean isErasing() {
        return isErasing;
    }


    public String getCustomFileName() {
        return customFileName;
    }


    public void setCustomFileName(String customFileName) {
        this.customFileName = customFileName;
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


    public void setCurrentColor(int currentColor) {
        this.currentColor = currentColor;
        initBrush();
    }

}
