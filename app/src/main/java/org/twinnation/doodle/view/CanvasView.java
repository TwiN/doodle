package org.twinnation.doodle.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.twinnation.doodle.R;

import java.util.ArrayList;
import java.util.List;


public class CanvasView extends View {

    private Paint currentPaint;
    private Paint brush;
    private Paint background;
    private Path path;
    private String mode;

    private List<Path> paths;
    private List<Paint> paints;


    /**
     * Constructor
     */
    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.paths = new ArrayList<>();
        this.paints = new ArrayList<>();
        this.path = new Path();
        this.brush = new Paint();
        this.background = new Paint();
        this.background.setColor(Color.WHITE);
        this.mode = "normal";
    }


    /////////////////////
    // EVENT LISTENERS //
    /////////////////////


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPaint(background);
        for (int i = 0; i < paths.size(); i++) {
            canvas.drawPath(paths.get(i), paints.get(i));
        }
        canvas.drawPath(path, brush);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                if (mode.equals("arc")) {
                    path.close();
                }
                paths.add(path);
                paints.add(brush);
                path = new Path();
                brush = new Paint(currentPaint);
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }


    //////////
    // MISC //
    //////////

    /**
     * Clears the canvas
     */
    public void clearCanvas() {
        paths.clear();
        paints.clear();
        invalidate();
    }


    /**
     * Removes the last the
     */
    public void undo() {
        if (paths.size() > 0) {
            paths.remove(paths.size()-1);
            paints.remove(paints.size()-1);
            invalidate();
        } else {
            Toast.makeText(getContext(), R.string.nothing_to_undo, Toast.LENGTH_SHORT).show();
        }
    }


    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////


    /**
     * Sets the brush and display a message (if the msg string resource id isn't 0)
     */
    public void setBrush(Paint brush, int msg) {
        this.currentPaint = new Paint(brush);
        this.brush = currentPaint;
        if (msg != 0) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Sets the mode
     * @param mode
     */
    public void setMode(String mode) {
        this.mode = mode;
        Toast.makeText(getContext(), getContext().getString(R.string.mode_set) + ": " + mode, Toast.LENGTH_SHORT).show();
    }


    /**
     * Gets the bitmap
     */
    public Bitmap getBitmap() {
        setDrawingCacheEnabled(true);
        setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        return getDrawingCache();
    }

}
