package com.guygool5.notesplusplus.models;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.guygool5.notesplusplus.BR;

public class IconButtonModel extends BaseObservable {
    private Drawable icon;
    private View.OnClickListener onClickListener=null;

    public IconButtonModel(Drawable icon) {
        this.icon = icon;
    }

    public IconButtonModel(Drawable icon, View.OnClickListener onClickListener) {
        this.icon = icon;
        this.onClickListener = onClickListener;
    }

    @Bindable
    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
        notifyPropertyChanged(BR.icon);
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
