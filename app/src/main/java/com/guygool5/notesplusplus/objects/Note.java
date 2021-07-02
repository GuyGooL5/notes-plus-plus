package com.guygool5.notesplusplus.objects;


import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.UUID;

public class Note implements Serializable {
    private String title = null;
    private final String uuid;

    public Note() {

        this.uuid = UUID.randomUUID().toString();
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @Nullable
    public String getTitle() {
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
