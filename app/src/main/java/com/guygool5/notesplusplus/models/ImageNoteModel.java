package com.guygool5.notesplusplus.models;

import android.graphics.Bitmap;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.guygool5.notesplusplus.objects.notes.ImageNote;

public class ImageNoteModel extends BaseObservable {

    private final ImageNote imageNote;


    public ImageNoteModel(ImageNote imageNote) {
        this.imageNote = imageNote;
    }


    public void setTitle(String title) {
        this.imageNote.setTitle(title);
        notifyPropertyChanged(BR.title);
    }

    public void setBitmap(Bitmap bitmap) {
        this.imageNote.setBitmap(bitmap);
        notifyPropertyChanged(BR.bitmap);
    }

    public ImageNote getImageNote(){
        return imageNote;
    }

    @Bindable
    public String getTitle() {
        return imageNote.getTitle();
    }

    @Bindable
    public Bitmap getBitmap() {
        return imageNote.getBitmap();
    }

}
