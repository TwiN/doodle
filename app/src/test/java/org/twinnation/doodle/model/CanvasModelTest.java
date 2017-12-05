package org.twinnation.doodle.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class CanvasModelTest {

    private CanvasModel canvasModel;


    @Before
    public void setUp() throws Exception {
        canvasModel = new CanvasModel();
    }


    @After
    public void tearDown() throws Exception {
        canvasModel = null;
    }


    @Test
    public void getCurrentSize() throws Exception {
        assertEquals("Default size should be " + CanvasModel.DEFAULT_BRUSH_SIZE,
                CanvasModel.DEFAULT_BRUSH_SIZE, canvasModel.getCurrentSize());
    }


    @Test
    public void incrementBrushSize() throws Exception {
        canvasModel.incrementBrushSize();
        assertEquals("Each size incrementation should add " + CanvasModel.SIZE_STEP,
                CanvasModel.DEFAULT_BRUSH_SIZE + CanvasModel.SIZE_STEP, canvasModel.getCurrentSize());

        int x = 10;
        while (x --> 0) {
            canvasModel.incrementBrushSize();
        }
        int messageOutput = canvasModel.incrementBrushSize();
        assertNotEquals("A non-null R.string.id should be returned because the max size has been reached",
                0, messageOutput);

        assertEquals("The current brush size cannot be above the maximum brush size.",
                CanvasModel.MAX_BRUSH_SIZE, canvasModel.getCurrentSize());
    }


    @Test
    public void decrementBrushSize() throws Exception {
        canvasModel.decrementBrushSize();
        assertEquals("Each size decrementation should substract " + CanvasModel.SIZE_STEP,
                CanvasModel.DEFAULT_BRUSH_SIZE - CanvasModel.SIZE_STEP, canvasModel.getCurrentSize());

        int x = 10;
        while (x --> 0) {
            canvasModel.decrementBrushSize();
        }
        int messageOutput = canvasModel.decrementBrushSize();
        assertNotEquals("A non-null R.string.id should be returned because the min size has been reached",
                0, messageOutput);

        assertEquals("The current brush size cannot be under the minimum brush size.",
                CanvasModel.MIN_BRUSH_SIZE, canvasModel.getCurrentSize());
    }


    @Test
    public void cycleNextMode() throws Exception {
        assertEquals(CanvasModel.MODE_NORMAL, canvasModel.getMode());
        canvasModel.cycleNextMode();
        assertEquals(CanvasModel.MODE_ARC, canvasModel.getMode());
    }

}
