package com.guygool5.notesplusplus.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.guygool5.notesplusplus.R;
import com.guygool5.notesplusplus.databinding.ActivityMainBinding;
import com.guygool5.notesplusplus.handlers.NoteManager;
import com.guygool5.notesplusplus.objects.notes.Note;
import com.guygool5.notesplusplus.objects.notes.NoteInfo;
import com.guygool5.notesplusplus.utilities.logger.LogType;
import com.guygool5.notesplusplus.utilities.logger.Logger;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        try {
            List<NoteInfo> noteInfoList = NoteManager.getInstance(this).getNoteInfoList();
            for (NoteInfo i : noteInfoList) {
                Logger.log(LogType.NOTE_FILE, i);
            }
        }catch (Exception e){
            Logger.log(LogType.EXCEPTION,e);
        }

        binding.mainButtonNewImageNoteId.setOnClickListener(v -> {

            Intent intent = new Intent(this, ImageNoteActivity.class);
            intent.putExtra("UUID", "c7388cb9-09f2-475a-81cf-8fb5fe92acb4");
            startActivity(intent);
        });
        binding.mainButtonNewTextNoteId.setOnClickListener(v -> {

            Intent intent = new Intent(this, TextNoteActivity.class);
            intent.putExtra("UUID", "4d554db0-abc2-4ff1-acb8-9884c0b52761");
            startActivity(intent);
        });

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


    }

}