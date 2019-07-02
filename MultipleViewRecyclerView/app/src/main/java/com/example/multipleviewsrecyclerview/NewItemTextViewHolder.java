package com.example.multipleviewsrecyclerview;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewItemTextViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "NewItemTextViewHolder";

    TextView titleTextView;
    EditText valueEditText;

    public NewItemTextViewHolder(@NonNull View itemView) {
        super(itemView);

        titleTextView = itemView.findViewById(R.id.textView);
        valueEditText = itemView.findViewById(R.id.editText);
    }

    void setUpData() {
        //
        titleTextView.setText("Title");
        //valueEditText.setText("HELLO ajksh k");
    }
}


class NewItemSizeViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "NewItemSizeViewHolder";

    TextView titleTextView;
    TextView subTitleTextView;
    Button button1;
    Button button2;
    Button button3;

    public NewItemSizeViewHolder(@NonNull View itemView) {
        super(itemView);

        titleTextView = itemView.findViewById(R.id.textView4);
        subTitleTextView = itemView.findViewById(R.id.textView3);

        button1 = itemView.findViewById(R.id.button);
        button2 = itemView.findViewById(R.id.button2);
        button3 = itemView.findViewById(R.id.button3);

    }

    void setUpData() {
        //
        titleTextView.setText("Title");
        //valueEditText.setText("HELLO ajksh k");
    }
}
class NewItemPriceViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "NewItemPriceViewHolder";

    TextView titleTextView;
    EditText priceEditText;


    public NewItemPriceViewHolder(@NonNull View itemView) {
        super(itemView);

        titleTextView = itemView.findViewById(R.id.textView5);
        priceEditText = itemView.findViewById(R.id.editText4);

    }

    void setUpData() {
        //
        // titleTextView.setText("Title");
        //valueEditText.setText("HELLO ajksh k");
    }
}
