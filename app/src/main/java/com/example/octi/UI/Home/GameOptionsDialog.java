package com.example.octi.UI.Home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.octi.R;

public class GameOptionsDialog extends DialogFragment {

    public interface GameOptionsListener {
        void onLocalGameSelected();
        void onCreateRoomSelected();
        void onJoinRoomSelected(String roomCode);
    }

    private GameOptionsListener listener;

    public void setGameOptionsListener(GameOptionsListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_game_options, null);
        // dialogView.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.setView(dialogView);

        Button localGameButton = dialogView.findViewById(R.id.btn_local_game);
        Button createRoomButton = dialogView.findViewById(R.id.btn_create_room);
        Button joinRoomOptionButton = dialogView.findViewById(R.id.btn_join_room_option);
        LinearLayout joinRoomLayout = dialogView.findViewById(R.id.layout_join_room);

        localGameButton.setOnClickListener(v -> {
            listener.onLocalGameSelected();
            dismiss();
        });

        createRoomButton.setOnClickListener(v -> {
            listener.onCreateRoomSelected();
            dismiss();
        });

        joinRoomOptionButton.setOnClickListener(v -> {
            localGameButton.setVisibility(View.GONE);
            createRoomButton.setVisibility(View.GONE);
            joinRoomOptionButton.setVisibility(View.GONE);
            joinRoomLayout.setVisibility(View.VISIBLE);
        });

        dialogView.findViewById(R.id.btn_join_room).setOnClickListener(v -> {
            EditText roomCodeEditText = dialogView.findViewById(R.id.et_room_code);
            String roomCode = roomCodeEditText.getText().toString();
            listener.onJoinRoomSelected(roomCode);
            dismiss();
        });


        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}