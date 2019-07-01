package com.example.multipleviewsrecyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<DescriptionViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private static int TYPE_NAME = 1;
    private static int TYPE_DESCRIPTION = 2;


    public RecyclerViewAdapter(Context context) {
        Log.d(TAG, "RecyclerViewAdapter: context " + context);
    }


    @NonNull
    @Override
    public DescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_DESCRIPTION) {
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newitem_description,parent,false);
            return new DescriptionViewHolder(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newitem_description,parent,false);
            return new DescriptionViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull DescriptionViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

        if (getItemViewType(position) == TYPE_DESCRIPTION) {
            // Description
            // can make new method in DescriptionViewHolder to setup data instead of direct assign

            holder.titleTextView.setText("Item Descritpion");
            holder.descriptionEditText.setText("Item Description Item Description Item Description Item Description");
        }else {

        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 1) {
            return TYPE_NAME;
        }else {
            return TYPE_DESCRIPTION;
        }
    }
}
