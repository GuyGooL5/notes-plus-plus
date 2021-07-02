package com.guygool5.notesplusplus.objects;


import java.io.Serializable;
import java.util.UUID;

public class Note implements Serializable {
    private String title = null;
    private final String uuid;

    public Note() {
        this.uuid = UUID.randomUUID().toString();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        if(title==null) return null;
        return title;
    }

    @Override
    public String toString() {
        return "NoteModel{" +
                "title='" + title + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
