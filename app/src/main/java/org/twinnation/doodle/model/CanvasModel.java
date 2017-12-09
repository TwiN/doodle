package org.twinnation.doodle.model;

import android.graphics.Color;
import android.graphics.Paint;


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


    /**
     * Constructor
     */
    public CanvasModel() {
        currentColor = Color.BLACK;
        currentSize = DEFAULT_BRUSH_SIZE;
        this.mode = MODE_NORMAL;
    }


    //////////
    // MISC //
    //////////


    /**
     * Initializes the brush
     */
    private void initBrush() {
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


    /**
     * Decrement the size of the brush
     * @return Whether the brush size decreased
     */
    public boolean decrementBrushSize() {
        if (currentSize > MIN_BRUSH_SIZE) {
            currentSize -= SIZE_STEP;
            initBrush();
        } else {
            return false;
        }
        return true;
    }


    /**
     * Increment the size of the brush
     * @return Whether the brush size incremented
     */
    public boolean incrementBrushSize() {
        if (currentSize < MAX_BRUSH_SIZE) {
            currentSize += SIZE_STEP;
            initBrush();
        } else {
            return false;
        }
        return true;
    }


    /**
     * Cycles to the next mode
     * @return The new mode
     */
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
