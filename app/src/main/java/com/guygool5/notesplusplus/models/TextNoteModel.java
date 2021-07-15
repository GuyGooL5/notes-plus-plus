package com.guygool5.notesplusplus.models;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.guygool5.notesplusplus.BR;
import com.guygool5.notesplusplus.objects.notes.Note;
import com.guygool5.notesplusplus.objects.notes.TextNote;


public class TextNoteModel extends BaseObservable {


    private final TextNote textNote;

    public TextNoteModel(){
        this.textNote=new TextNote();
    }

    public TextNoteModel(TextNote textNote) {
        this.textNote = textNote;
    }

    public void setTitle(@Nullable String title) {
        textNote.setTitle(title);
        notifyPropertyChanged(BR.title);
    }
    public void setText(String text) {
        textNote.setText(text);
        notifyPropertyChanged(BR.text);
    }

    public TextNote getTextNote(){
        return textNote;
    }

    @Nullable @Bindable
    public String getTitle() {
        return textNote.getTitle();
    }

    @Nullable @Bindable
    public String getText() { return textNote.getText(); }



}
