package com.example.multipleviewsrecyclerview;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DescriptionViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "DescriptionViewHolder";

    TextView titleTextView;
    EditText descriptionEditText;

    public DescriptionViewHolder(@NonNull View itemView) {
        super(itemView);

        titleTextView = itemView.findViewById(R.id.textView2);
        descriptionEditText = itemView.findViewById(R.id.editText2);
    }
}
