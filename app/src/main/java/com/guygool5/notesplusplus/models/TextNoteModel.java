package com.guygool5.notesplusplus.models;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.guygool5.notesplusplus.BR;
import com.guygool5.notesplusplus.objects.notes.Note;
import com.guygool5.notesplusplus.objects.notes.TextNote;


public class TextNoteModel extends BaseObservable {


    private String title;


    private String text;

    public TextNoteModel(TextNote textNote) {
        setTitle(textNote.getTitle());
        setText(textNote.getText());
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }
    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }


    @Nullable @Bindable
    public String getTitle() {
        return title;
    }

    @Nullable @Bindable
    public String getText() { return text; }



}
