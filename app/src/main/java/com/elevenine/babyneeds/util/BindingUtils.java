package com.elevenine.babyneeds.util;

import android.widget.EditText;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.elevenine.babyneeds.data.db.ItemEntity;

public class BindingUtils {
    @BindingAdapter("intToStringTextView")
    public static void convertIntToString(TextView view, ItemEntity itemEntity) {
        view.setText(String.valueOf(itemEntity.getItemQuantity()));
    }

    @BindingAdapter("intToStringEditText")
    public static void convertIntToString(EditText view, ItemEntity itemEntity) {
        view.setText(String.valueOf(itemEntity.getItemQuantity()));
    }
}
