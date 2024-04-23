package com.example.octi.Fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.octi.R;
import com.example.octi.Models.Game;
import com.example.octi.Models.Pod;
import com.example.octi.Models.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PieceView extends View{
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

    private final Drawable octagonDrawable;
    private final Drawable octagonArrowDrawable;

    private final Drawable octagonArrowFlippedDrawable;
    private Pod pod;

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
        drawArrows(canvas);
    }

    public Pod getPod() {
        return pod;
    }

    private void drawOctagon(Canvas canvas) {
        Game.Team team = pod.getTeam();
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


    private void drawArrows(Canvas canvas) {
        ArrayList<Boolean> prongs = pod.getProngs();

        for (int i = 0; i < prongs.size(); i++) {
            if (!prongs.get(i)) {
                continue;
            }
            Drawable arrowDrawable = ContextCompat.getDrawable(getContext(), prong2prongDrawable[i]);
            if (selected) {
                arrowDrawable.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
            } else {
                arrowDrawable.clearColorFilter();
            }
            arrowDrawable.setBounds(canvas.getClipBounds());
            arrowDrawable.draw(canvas);
        }
    }

    public void setPod(Pod pod) {
        this.pod = pod;
    }

    public void setSelection(boolean state) {
        selected = state;
        invalidate();
    }
}
