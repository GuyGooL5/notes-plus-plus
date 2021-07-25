package com.guygool5.notesplusplus.models;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.BindingAdapter;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.guygool5.notesplusplus.R;
import com.guygool5.notesplusplus.objects.notes.NoteType;
import com.guygool5.notesplusplus.utilities.logger.LogType;
import com.guygool5.notesplusplus.utilities.logger.Logger;

import java.util.Objects;

public class TextViewBindingAdapters {

    @BindingAdapter("isBold")
    public static void setBold(TextView view, boolean isBold) {
        view.setTypeface(null, isBold ? Typeface.BOLD : Typeface.NORMAL);
    }

    @BindingAdapter("bitmap")
    public static void setImageBitmap(ImageView view, Bitmap bitmap) {
        Logger.log(LogType.IMAGE_NOTE, "Trying to set bitmap to", bitmap);
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("noteType")
    public static void setNoteType(MaterialTextView textView, NoteType noteType) {
        Drawable icon;
        switch (noteType) {
            case TEXT:
                icon = ResourcesCompat.getDrawable(textView.getResources(), R.drawable.ic_baseline_text_snippet_24, null);
                break;
            case IMAGE:
                icon = ResourcesCompat.getDrawable(textView.getResources(), R.drawable.ic_baseline_image_24, null);
                break;
            default:
                return;
        }
        Logger.log(LogType.DEBUG,"Trying to set icon",icon);
        textView.setCompoundDrawablesWithIntrinsicBounds(icon,null,null,null);

    }
}
