package com.example.octi.Room;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;

import com.example.octi.R;

public class JoinRoomDialog extends Dialog {
    public JoinRoomDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_join_room);

        // Button closeButton = findViewById(R.id.closeButton);
        // closeButton.setOnClickListener(v -> dismiss());
    }
}
