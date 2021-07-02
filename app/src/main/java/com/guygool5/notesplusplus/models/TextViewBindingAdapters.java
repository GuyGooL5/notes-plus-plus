package com.guygool5.notesplusplus.models;

import android.graphics.Typeface;
import android.widget.TextView;
import androidx.databinding.BindingAdapter;

public class TextViewBindingAdapters {

    @BindingAdapter("isBold")
    public static void setBold(TextView view, boolean isBold) {
        view.setTypeface(null, isBold ? Typeface.BOLD : Typeface.NORMAL);
    }

}
