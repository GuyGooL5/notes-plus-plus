package com.guygool5.notesplusplus.adapters;

import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.databinding.library.baseAdapters.BR;

import com.guygool5.notesplusplus.models.TextNoteModel;
import com.guygool5.notesplusplus.objects.notes.TextNote;
import com.guygool5.notesplusplus.utilities.logger.LogType;
import com.guygool5.notesplusplus.utilities.logger.Logger;

public class TextNoteAdapter {

    private final TextNote textNote;
    private final TextNoteModel textNoteModel;

    public TextNoteAdapter(TextNote note) {
        this.textNote = note;
        this.textNoteModel = new TextNoteModel(note);
        textNoteModel.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                TextNoteModel observableTextNoteModel = (TextNoteModel) sender;
                Logger.log(LogType.TEXT_NOTE, "Observerd Change in TextNoteModel");
                if (propertyId == BR.title) {
                    String title = observableTextNoteModel.getTitle();
                    Logger.log(LogType.TEXT_NOTE, "Title has been changed to", title);
                    textNote.setTitle(title);

                }
                if (propertyId == BR.text) {
                    String text = observableTextNoteModel.getText();
                    Logger.log(LogType.TEXT_NOTE, "Text has been changed to", text);
                    textNote.setText(text);

                }
            }
        });
    }

    public TextNoteAdapter() {
        this(new TextNote());
    }

    public TextNote getTextNote() {
        return this.textNote;
    }

    public TextNoteModel getTextNoteModel() {
        return this.textNoteModel;
    }

    public void setTitle(@Nullable String title) {
        textNote.setTitle(title);
        textNoteModel.setTitle(title);
    }

    public String getTitle() {
        return textNote.getTitle();
    }

}
