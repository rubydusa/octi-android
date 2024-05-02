package com.example.octi.Fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.octi.Models.Vector2D;
import com.example.octi.R;
import com.example.octi.Models.Game;
import com.example.octi.Models.Pod;

import java.util.ArrayList;

public class CellView extends View{
    private static final int[] prong2prongDrawable = new int[8];

    static {
        // 0 is middle right, going counter clockwise
        prong2prongDrawable[0] = R.drawable.octigon_arrow_middle_right;
        prong2prongDrawable[1] = R.drawable.octigon_arrow_top_right;
        prong2prongDrawable[2] = R.drawable.octigon_arrow_top_center;
        prong2prongDrawable[3] = R.drawable.octigon_arrow_top_left;
        prong2prongDrawable[4] = R.drawable.octigon_arrow_middle_left;
        prong2prongDrawable[5] = R.drawable.octigon_arrow_bottom_left;
        prong2prongDrawable[6] = R.drawable.octigon_arrow_bottom_center;
        prong2prongDrawable[7] = R.drawable.octigon_arrow_bottom_right;
    }

    private final FrameLayout frame;

    private final Paint paint;
    private final Drawable octagonDrawable;
    private final Drawable octagonArrowDrawable;

    private final Drawable octagonArrowFlippedDrawable;
    private final Drawable octagonOutlineDrawable;
    private Pod pod;
    @Nullable
    private Game.Team color;

    private final Vector2D position;
    private boolean selected = false;
    private boolean jumpedOver = false;
    private boolean selectedForEating = false;

    public CellView(Context context, GridLayout gridLayout, Vector2D position, CellClickListener listener) {
        super(context);
        // allocate resources
        paint = new Paint();
        octagonOutlineDrawable = ContextCompat.getDrawable(context, R.drawable.octigon_outline);
        octagonDrawable = ContextCompat.getDrawable(context, R.drawable.octigon);
        octagonArrowDrawable = ContextCompat.getDrawable(context, R.drawable.octigon_team_arrow);
        octagonArrowFlippedDrawable = ContextCompat.getDrawable(context, R.drawable.octigon_team_arrow_flipped);

        // create frame layout
        frame = new FrameLayout(context);
        frame.setId(View.generateViewId());
        frame.setClickable(true);
        frame.setOnClickListener(v -> listener.onCellClick(this));
        frame.setBackgroundResource(R.drawable.cell_background);

        // add to grid
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = 0;
        params.columnSpec = GridLayout.spec(position.getX(), 1f);
        params.rowSpec = GridLayout.spec(position.getY(), 1f);
        gridLayout.addView(frame, params);

        // display self in frame
        frame.addView(this);

        this.position = position;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCell(canvas);
        if (pod == null) {
            return;
        }
        drawOctagon(canvas);
        drawArrows(canvas);
        drawEatSelection(canvas);
    }

    public Pod getPod() {
        return pod;
    }

    public Vector2D getPosition() { return position; }

    public boolean isSelected() { return selected; }

    private void drawCell(Canvas canvas) {
        int colorResource = getCellColorResource(color);
        int color = ContextCompat.getColor(getContext(), colorResource);
        if (selected && pod == null) {
            color = Color.GREEN;
        }
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }

    private void drawOctagon(Canvas canvas) {
        Game.Team team = pod.getTeam();
        octagonOutlineDrawable.setBounds(canvas.getClipBounds());
        Drawable arrowDrawable = octagonArrowDrawable;

        if (team == Game.Team.RED) {
            octagonDrawable.setTint(getResources().getColor(R.color.team_red, getContext().getTheme()));
        } else {
            octagonDrawable.setTint(getResources().getColor(R.color.team_green, getContext().getTheme()));
            arrowDrawable = octagonArrowFlippedDrawable;
        }

        octagonDrawable.setBounds(canvas.getClipBounds());
        arrowDrawable.setBounds(canvas.getClipBounds());

        if (selected) {
            octagonDrawable.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        } else {
            octagonDrawable.clearColorFilter();
        }

        octagonOutlineDrawable.draw(canvas);
        octagonDrawable.draw(canvas);
        arrowDrawable.draw(canvas);
    }


    private void drawArrows(Canvas canvas) {
        ArrayList<Boolean> prongs = pod.getProngs();

        for (int i = 0; i < prongs.size(); i++) {
            if (!prongs.get(i)) {
                continue;
            }
            Drawable arrowDrawable = ContextCompat.getDrawable(getContext(), prong2prongDrawable[i]);
            arrowDrawable.setBounds(canvas.getClipBounds());
            arrowDrawable.draw(canvas);
        }
    }

    private void drawEatSelection(Canvas canvas) {
        if (!jumpedOver) {
            return;
        }

        if (selectedForEating) {
            paint.setColor(Color.RED);
        } else {
            paint.setColor(Color.GREEN);
        }
        paint.setAlpha(100);
        paint.setStyle( Paint.Style.FILL );
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }

    public void setPod(Pod pod) {
        this.pod = pod;
        // cause to re-render
        invalidate();
    }

    public void setColor(Game.Team color) {
        this.color = color;
        // cause to re-render
        invalidate();
    }

    public void setSelection(boolean state) {
        selected = state;
        // cause to re-render
        invalidate();
    }

    public void setEatSelection(boolean state) {
        jumpedOver = true;
        selectedForEating = state;
        // cause to re-render
        invalidate();
    }

    public void clearCell() {
        pod = null;
        selected = false;
        jumpedOver = false;
        selectedForEating = false;
        invalidate();
    }

    private int getCellColorResource(@Nullable Game.Team color) {
        if (color == null) {
            return R.color.cell_background;
        } else {
            switch (color) {
                case RED:
                    return R.color.light_team_red;
                case GREEN:
                    return R.color.light_team_green;
            }
        }

        // TODO: Clarify in code this is unreachable
        return 0;
    }


    public interface CellClickListener {
        void onCellClick(CellView cellView);
    }
}
