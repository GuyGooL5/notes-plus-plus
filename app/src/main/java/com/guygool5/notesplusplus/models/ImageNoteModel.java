package com.guygool5.notesplusplus.models;

import android.graphics.Bitmap;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.guygool5.notesplusplus.objects.notes.ImageNote;
import com.guygool5.notesplusplus.utilities.BitmapDataObject;
import com.guygool5.notesplusplus.utilities.logger.LogType;
import com.guygool5.notesplusplus.utilities.logger.Logger;

public class ImageNoteModel extends BaseObservable {

    private final ImageNote imageNote;


    public ImageNoteModel() {
        this(new ImageNote());
    }

    public ImageNoteModel(ImageNote imageNote) {
        this.imageNote = imageNote;
    }


    public void setTitle(String title) {
        this.imageNote.setTitle(title);
        notifyPropertyChanged(BR.title);
    }

    public void setBitmap(BitmapDataObject bitmap) {
        this.imageNote.setBitmap(bitmap);
        notifyPropertyChanged(BR.bitmap);
    }

    public ImageNote getImageNote() {
        return imageNote;
    }

    @Bindable
    public String getTitle() {
        return imageNote.getTitle();
    }

    @Bindable
    public BitmapDataObject getBitmap() {
        return imageNote.getBitmap();
    }

}
