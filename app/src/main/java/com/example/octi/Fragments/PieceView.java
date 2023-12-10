package com.example.octi.Fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.octi.R;
import com.example.octi.models.Game;
import com.example.octi.models.Octi;

public class PieceView extends View {

    private Drawable octagonDrawable;
    private Drawable octagonArrowDrawable;

    private Drawable octagonArrowFlippedDrawable;
    private Octi octi;

    private Paint paint;

    public PieceView(Context context) {
        super(context);
        octagonDrawable = ContextCompat.getDrawable(context, R.drawable.octigon);
        octagonArrowDrawable = ContextCompat.getDrawable(context, R.drawable.octigon_arrow);
        octagonArrowFlippedDrawable = ContextCompat.getDrawable(context, R.drawable.octigon_arrow_flipped);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOctagon(canvas);
        drawArrow(canvas);
        drawDarts(canvas);
    }

    private void drawOctagon(Canvas canvas) {
        // Game.Team team = octi.getTeam();
        Game.Team team = Game.Team.RED;
        Drawable arrowDrawable = octagonArrowDrawable;

        if (team == Game.Team.RED) {
            octagonDrawable.setTint(getResources().getColor(R.color.team_red, getContext().getTheme()));
        } else {
            octagonDrawable.setTint(getResources().getColor(R.color.team_green, getContext().getTheme()));
            arrowDrawable = octagonArrowFlippedDrawable;
        }

        octagonDrawable.setBounds(canvas.getClipBounds());
        arrowDrawable.setBounds(canvas.getClipBounds());

        octagonDrawable.draw(canvas);
        arrowDrawable.draw(canvas);
    }

    private void drawArrow(Canvas canvas) {
        // Draw the arrow based on teamColor
    }

    private void drawDarts(Canvas canvas) {
        // Draw darts based on the darts array
    }

    public void setOcti(Octi octi) {
        this.octi = octi;
    }
}
