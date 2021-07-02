package com.guygool5.notesplusplus.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.guygool5.notesplusplus.BR;
import com.guygool5.notesplusplus.objects.Note;


public class NoteModel extends BaseObservable {


    private String title;

    public NoteModel(Note note) {
        String title = note.getTitle();
        if (title == null) setTitle("");
        else setTitle(note.getTitle());
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

}
