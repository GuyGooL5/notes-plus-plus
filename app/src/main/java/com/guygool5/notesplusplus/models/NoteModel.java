package com.guygool5.notesplusplus.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.guygool5.notesplusplus.BR;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class NoteModel extends BaseObservable implements Serializable  {


    private String title;
    private final String uuid;

    private final Set<NoteDataModel> noteDataModelSet = new HashSet<>();


    public NoteModel() {
        this.uuid = UUID.randomUUID().toString();
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }
    @Bindable
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
