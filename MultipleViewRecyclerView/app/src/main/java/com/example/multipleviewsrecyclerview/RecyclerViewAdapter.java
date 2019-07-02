package com.example.multipleviewsrecyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private static int TYPE_IMAGE = 0;
    private static int TYPE_NAME = 1;
    private static int TYPE_LINK = 2;
    private static int TYPE_DESCRIPTION = 3;
    private static int TYPE_PRICE = 4;
    private static int TYPE_SIZE = 5;

    private Context mContext;



    public RecyclerViewAdapter(Context context) {
        Log.d(TAG, "RecyclerViewAdapter: context " + context);

        mContext = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_IMAGE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newitem_image,parent,false);
            return new NewItemImageViewHolder(view,mContext);
        }else if (viewType == TYPE_NAME) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newitem_textfield,parent,false);
            return new NewItemTextViewHolder(view);
        }else if (viewType == TYPE_LINK) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newitem_textfield,parent,false);
            return new NewItemTextViewHolder(view);
        }else if (viewType == TYPE_DESCRIPTION) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newitem_description,parent,false);
            return new DescriptionViewHolder(view);
        }else if (viewType == TYPE_SIZE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newitem_size,parent,false);
            return new NewItemSizeViewHolder(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newitem_value,parent,false);
            return new NewItemPriceViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

        if (getItemViewType(position)  == TYPE_IMAGE) {

            //
            // ((NewItemImageViewHolder) holder)

        }else if (getItemViewType(position) == TYPE_NAME) {

            ((NewItemTextViewHolder) holder).titleTextView.setText("Item Name");
            // ((NewItemTextViewHolder) holder).valueEditText.setText("Item Name Item Name Item Name Item Name");
        }else if (getItemViewType(position) == TYPE_LINK) {

            ((NewItemTextViewHolder) holder).titleTextView.setText("Item Link");
            // ((NewItemTextViewHolder) holder).valueEditText.setText("Item Name Item Name Item Name Item Name");
        }else if (getItemViewType(position) == TYPE_DESCRIPTION) {
            // Description
            // can make new method in DescriptionViewHolder to setup data instead of direct assign

            ((DescriptionViewHolder) holder).titleTextView.setText("Item Descritpion");
            ((DescriptionViewHolder) holder).descriptionEditText.setText("Item Description Item Description Item Description Item Description");

            // ((DescriptionViewHolder) holder).setUpDataForFields();

        }else if (getItemViewType(position) == TYPE_SIZE) {
            ((NewItemSizeViewHolder) holder).titleTextView.setText("Describe the item Size");
            ((NewItemSizeViewHolder) holder).subTitleTextView.setText("subtitle");
        }else {
            ((NewItemPriceViewHolder) holder).titleTextView.setText("Item Price");
            // ((NewItemPriceViewHolder) holder).priceEditText.setText("$123");
        }
    }

//    @Override
//    public void onBindViewHolder(@NonNull DescriptionViewHolder holder, int position) {
//        Log.d(TAG, "onBindViewHolder: ");
//
//        if (getItemViewType(position) == TYPE_DESCRIPTION) {
//            // Description
//            // can make new method in DescriptionViewHolder to setup data instead of direct assign
//
//            holder.titleTextView.setText("Item Descritpion");
//            holder.descriptionEditText.setText("Item Description Item Description Item Description Item Description");
//        }else {
//
//        }
//    }

    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_IMAGE;
        }else if (position == 1) {
            return TYPE_NAME;
        }else if (position == 2) {
            return TYPE_LINK;
        }else if (position == 3){
            return TYPE_PRICE;
        }else if (position == 4){
            return TYPE_DESCRIPTION;
        }else if (position == 5) {
            return TYPE_SIZE;
        }else {
            return TYPE_SIZE;
        }
    }
}
