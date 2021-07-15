package com.guygool5.notesplusplus.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.guygool5.notesplusplus.R;
import com.guygool5.notesplusplus.databinding.ActivityMainBinding;
import com.guygool5.notesplusplus.handlers.NoteFileHandler;
import com.guygool5.notesplusplus.objects.notes.TextNote;
import com.guygool5.notesplusplus.utilities.logger.LogType;
import com.guygool5.notesplusplus.utilities.logger.Logger;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

//        TextNote textNote = new TextNote();
//        textNote.setTitle("TextNote1");
//        textNote.setText("TextNote1Text");
//        try {
//            Logger.log(LogType.NOTE_FILE,"Trying to save TextNote:",textNote.toString());
//            NoteFileHandler.saveNote(this,textNote);
//            Logger.log(LogType.NOTE_FILE,"Saved TextNote:",textNote.toString());
//        } catch (IOException e) {
//            Logger.log(LogType.NOTE_FILE,e.toString());
//        }



        Intent intent = new Intent(this, TextNoteActivity.class);
        intent.putExtra("UUID","c7388cb9-09f2-475a-81cf-8fb5fe92acb4");
        startActivity(intent);

    }

}