package com.example.octi.Fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.octi.R;
import com.example.octi.models.Game;
import com.example.octi.models.Octi;
import com.example.octi.models.Vector2D;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieceView extends View{
    private static final Map<Vector2D, Integer> vec2ArrowDrawable;

    static {
        vec2ArrowDrawable = new HashMap<>();
        vec2ArrowDrawable.put(new Vector2D(1, -1), R.drawable.octigon_arrow_top_right);
        vec2ArrowDrawable.put(new Vector2D(0, -1), R.drawable.octigon_arrow_top_center);
        vec2ArrowDrawable.put(new Vector2D(-1, -1), R.drawable.octigon_arrow_top_left);
        vec2ArrowDrawable.put(new Vector2D(-1, 0), R.drawable.octigon_arrow_middle_left);
        vec2ArrowDrawable.put(new Vector2D(-1, 1), R.drawable.octigon_arrow_bottom_left);
        vec2ArrowDrawable.put(new Vector2D(0, 1), R.drawable.octigon_arrow_bottom_center);
        vec2ArrowDrawable.put(new Vector2D(1, 1), R.drawable.octigon_arrow_bottom_right);
        vec2ArrowDrawable.put(new Vector2D(1, 0), R.drawable.octigon_arrow_middle_right);
    }

    private Drawable octagonDrawable;
    private Drawable octagonArrowDrawable;

    private Drawable octagonArrowFlippedDrawable;
    private Octi octi;

    private boolean selected = false;

    public PieceView(Context context) {
        super(context);

        octagonDrawable = ContextCompat.getDrawable(context, R.drawable.octigon);
        octagonArrowDrawable = ContextCompat.getDrawable(context, R.drawable.octigon_team_arrow);
        octagonArrowFlippedDrawable = ContextCompat.getDrawable(context, R.drawable.octigon_team_arrow_flipped);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOctagon(canvas);
        // drawArrows(canvas);
    }

    private void drawOctagon(Canvas canvas) {
        Game.Team team = octi.getTeam();
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
            arrowDrawable.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        } else {
            octagonDrawable.clearColorFilter();
            arrowDrawable.clearColorFilter();
        }

        octagonDrawable.draw(canvas);
        arrowDrawable.draw(canvas);
    }

    /*
    private void drawArrows(Canvas canvas) {
        List<Vector2D> arrows = octi.getArrows();

        for (Vector2D arrow: arrows) {
            Drawable arrowDrawable = ContextCompat.getDrawable(getContext(), vec2ArrowDrawable.get(arrow));
            if (selected) {
                arrowDrawable.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
            } else {
                arrowDrawable.clearColorFilter();
            }
            arrowDrawable.setBounds(canvas.getClipBounds());
            arrowDrawable.draw(canvas);
        }
    }
    */

    public void setOcti(Octi octi) {
        this.octi = octi;
    }

    public void setSelection(boolean state) {
        selected = state;
        invalidate();
    }
}
