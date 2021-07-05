package com.guygool5.notesplusplus.objects.notes;


import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.UUID;


//Note is a base class for every note type down the line.
public abstract class Note implements Serializable {

    //Every note should have a title.
    private String title = null;

    //Every note should have a unique *final* UUID.
    private final String uuid;

    //Every note should have a *final* created time and an updating modified time.
    private final Calendar created;
    private Calendar modified;

    //This constructor will initialize the UUID, created and modified.
    public Note() {
        this.uuid = UUID.randomUUID().toString();
        this.created = Calendar.getInstance();
        this.modified = (Calendar) this.created.clone();
    }

    //This will be as the base constructor but also initialize the title.
    public Note(String title) {
        this();
        setTitle(title);
    }

    //This abstract Method will be implemented by each inheriting note with it's type.
    public abstract NoteType getType();

    //This will be get the UUID from the note.
    public String getUuid() {
        return uuid;
    }

    //This setter will set the Title and also update the modified time.
    public void setTitle(@Nullable String title) {
        this.title = title;
        updateModified();
    }


    //This will get the title (can be null).
    @Nullable
    public String getTitle() {
        return title;
    }

    //This will get the time the note was created.
    public Calendar getCreated() {
        return created;
    }

    public String getCreatedString() {
        return created.getTime().toString();
    }

    //This will get the time the note was modified.
    public Calendar getModified() {
        return modified;
    }

    public String getModifiedString(){
        return modified.getTime().toString();
    }

    @Override
    public String toString() {
        return "Note{" +
                "title='" + title + '\'' +
                ", uuid='" + uuid + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }

    //This protected method will be used on each setter of the inheriting classes.
    protected void updateModified() {
        this.modified = Calendar.getInstance();
    }

}
//This will be the base class of every note.
