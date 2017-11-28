package org.twinnation.doodle;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by chris on 2017-11-24.
 */

public class BottomToolBarFragment extends Fragment {


    private CanvasView canvasView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view  = inflater.inflate(R.layout.fragment_bottom_toolbar, container, false);

        ImageButton clearCanvasBtn = (ImageButton)view.findViewById(R.id.clearCanvas);
        ImageButton saveCanvasBtn = (ImageButton)view.findViewById(R.id.saveCanvas);
        ImageButton undoBtn = (ImageButton)view.findViewById(R.id.undo);
        ImageButton eraserBtn = (ImageButton)view.findViewById(R.id.eraser);

        clearCanvasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canvasView.clearCanvas();
            }
        });

        undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canvasView.undo();
            }
        });

        eraserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canvasView.toggleEraserMode();
            }
        });

        saveCanvasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canvasView.saveDoodle();
            }
        });

        return view;
    }


    public void setCanvasView(CanvasView cv) {
        this.canvasView = cv;
    }
}
