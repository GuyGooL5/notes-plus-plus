package com.guygool5.notesplusplus.objects.notes;

import java.io.Serializable;

public class NoteInfo implements Serializable {
    private final String uuid;
    private final NoteType noteType;
    private String title;

    public NoteInfo(String uuid, NoteType noteType, String title) {
        this.uuid = uuid;
        this.noteType = noteType;
        this.title = title;
    }

    public <T extends Note> NoteInfo(T note) {
        this(note.getUuid(), note.getType(), note.getTitle());
    }

    public String getTitle() {
        return title;
    }

    public String getUuid() {
        return uuid;
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "NoteInfo{" +
                "uuid='" + uuid + '\'' +
                ", noteType=" + noteType +
                ", title='" + title + '\'' +
                '}';
    }
}
