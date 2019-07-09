package com.example.multipleviewsrecyclerview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multipleviewsrecyclerview.model.NonCuratedItem;

import java.math.BigDecimal;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements NewItemImageViewHolder.AddimagesAction {

    private static final String TAG = "RecyclerViewAdapter";

    private static int TYPE_IMAGE = 0;
    private static int TYPE_NAME = 1;
    private static int TYPE_LINK = 2;
    private static int TYPE_DESCRIPTION = 3;
    private static int TYPE_PRICE = 4;
    private static int TYPE_SIZE = 5;

    private Context mContext;
    private ONNClickListener onnClickListener;

    private NonCuratedItem newItem;



    public RecyclerViewAdapter(Context context, NonCuratedItem item, ONNClickListener onnClickListener) {
        Log.d(TAG, "RecyclerViewAdapter: context " + context);

        mContext = context;
        this.onnClickListener = onnClickListener;
        this.newItem = item;
    }

    public NonCuratedItem getUpdatedItem() {
        return newItem;
    }

    public void setUpdatedNewItem(NonCuratedItem item) {
        newItem = item;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_IMAGE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newitem_image,parent,false);
            NewItemImageViewHolder holder = new NewItemImageViewHolder(view,mContext,newItem.getImages(),this);
            return holder;
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
            ((NewItemImageViewHolder) holder).setUpdatedImages(newItem.getImages());


        }else if (getItemViewType(position) == TYPE_NAME) {

            ((NewItemTextViewHolder) holder).titleTextView.setText("Item Name");

            ((NewItemTextViewHolder) holder).valueEditText.setText(newItem.getItemName());

            ((NewItemTextViewHolder) holder).valueEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // item name update
                    newItem.setItemName(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });



            // ((NewItemTextViewHolder) holder).valueEditText.setText("Item Name Item Name Item Name Item Name");
        }else if (getItemViewType(position) == TYPE_LINK) {

            ((NewItemTextViewHolder) holder).titleTextView.setText("Item Link");
            ((NewItemTextViewHolder) holder).valueEditText.setText(newItem.getLink());

            ((NewItemTextViewHolder) holder).valueEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // item name update
                    newItem.setLink(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });



        }else if (getItemViewType(position) == TYPE_DESCRIPTION) {
            // Description
            // can make new method in DescriptionViewHolder to setup data instead of direct assign

            ((DescriptionViewHolder) holder).titleTextView.setText("Item Descritpion");
            ((DescriptionViewHolder) holder).descriptionEditText.setText(newItem.getItemDescription());

            ((DescriptionViewHolder) holder).descriptionEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    newItem.setItemDescription(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });



            // ((DescriptionViewHolder) holder).setUpDataForFields();

        }else if (getItemViewType(position) == TYPE_SIZE) {
            ((NewItemSizeViewHolder) holder).titleTextView.setText("Describe the item Size");
            ((NewItemSizeViewHolder) holder).subTitleTextView.setText("subtitle");

            if (newItem.getItemSize() != null) {
                ((NewItemSizeViewHolder) holder).setUpButtonSate(newItem.getItemSize());
            }else {
                ((NewItemSizeViewHolder) holder).setUpButtonSate(0);
            }

            ((NewItemSizeViewHolder) holder).button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((NewItemSizeViewHolder) holder).setUpButtonSate(1);
                    newItem.setItemSize(1);
                }
            });

            ((NewItemSizeViewHolder) holder).button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((NewItemSizeViewHolder) holder).setUpButtonSate(2);
                    newItem.setItemSize(2);
                }
            });

            ((NewItemSizeViewHolder) holder).button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((NewItemSizeViewHolder) holder).setUpButtonSate(3);
                    newItem.setItemSize(3);
                }
            });

        }else {
            ((NewItemPriceViewHolder) holder).titleTextView.setText("Item Price");
            // ((NewItemPriceViewHolder) holder).priceEditText.setText("$123");
            ((NewItemPriceViewHolder) holder).priceEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length() > 0) {
                        newItem.setPrice(BigDecimal.valueOf(Double.valueOf(charSequence.toString())));
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
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

    @Override
    public void didTapAddImage() {

        onnClickListener.onAddImageClick();
    }

    @Override
    public void didTapDeleteImage(Integer index) {
        Log.d(TAG, "didTapDeleteImage: " + index);

        ArrayList<String> images = newItem.getImages();
        if (images.remove(images.get(index))) {
            newItem.setImages(images);
        }
        notifyDataSetChanged();

        onnClickListener.onDeleteImageClick(index);
    }


    public interface ONNClickListener {
        void onAddImageClick();
        void onDeleteImageClick(Integer index);
    }
}
