package com.guygool5.notesplusplus.adapters;

import com.guygool5.notesplusplus.models.NoteModel;
import com.guygool5.notesplusplus.objects.Note;

public class NoteAdapter {

    private final Note note;
    private final NoteModel noteModel;
    public NoteAdapter(Note note){
        this.note = note;
        this.noteModel = new NoteModel(note);
    }
    public NoteAdapter(){
        this(new Note());
    }

    public Note getNote(){
        return this.note;
    }

    public NoteModel getNoteModel(){
        return this.noteModel;
    }

    public void setTitle(String title){
        note.setTitle(title);
        noteModel.setTitle(title);
    }

    public String getTitle(){
        return note.getTitle();
    }

}
