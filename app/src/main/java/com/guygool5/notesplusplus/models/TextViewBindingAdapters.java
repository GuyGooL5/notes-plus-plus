package com.guygool5.notesplusplus.models;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.BindingAdapter;

import com.google.android.material.textfield.TextInputEditText;
import com.guygool5.notesplusplus.utilities.logger.LogType;
import com.guygool5.notesplusplus.utilities.logger.Logger;

import java.util.Objects;

public class TextViewBindingAdapters {

    @BindingAdapter("isBold")
    public static void setBold(TextView view, boolean isBold) {
        view.setTypeface(null, isBold ? Typeface.BOLD : Typeface.NORMAL);
    }

    @BindingAdapter("bitmap")
    public static void setImageBitmap(ImageView view, Bitmap bitmap){
        Logger.log(LogType.IMAGE_NOTE,"Trying to set bitmap to",bitmap);
        view.setImageBitmap(bitmap);
    }
}
