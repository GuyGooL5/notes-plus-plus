package com.guygool5.notesplusplus.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.guygool5.notesplusplus.R;
import com.guygool5.notesplusplus.adapters.NoteAdapter;
import com.guygool5.notesplusplus.databinding.ActivityMainBinding;
import com.guygool5.notesplusplus.handlers.NoteManager;
import com.guygool5.notesplusplus.objects.notes.NoteInfo;
import com.guygool5.notesplusplus.utilities.logger.LogType;
import com.guygool5.notesplusplus.utilities.logger.Logger;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        updateRecycler();
        binding.mainRecyclerViewId.setLayoutManager(new LinearLayoutManager(this));

        binding.mainFabAddNoteId.setOnClickListener(v -> {
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);
            alertDialogBuilder.setTitle("New Note");
            alertDialogBuilder.setMessage("Selected which kind of a note you want to create.");
            alertDialogBuilder.setPositiveButton("TEXT", (d, w) -> startActivity(new Intent(this, TextNoteActivity.class)));
            alertDialogBuilder.setNeutralButton("IMAGE", (d, w) -> startActivity(new Intent(this, ImageNoteActivity.class)));
            alertDialogBuilder.show();
        });

//        binding.mainButtonNewImageNoteId.setOnClickListener(v -> {
//
//            Intent intent = new Intent(this, ImageNoteActivity.class);
//            intent.putExtra("UUID", "c7388cb9-09f2-475a-81cf-8fb5fe92acb4");
//            startActivity(intent);
//        });
//        binding.mainButtonNewTextNoteId.setOnClickListener(v -> {
//
//            Intent intent = new Intent(this, TextNoteActivity.class);
//            intent.putExtra("UUID", "4d554db0-abc2-4ff1-acb8-9884c0b52761");
//            startActivity(intent);
//        });

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

    @Override
    protected void onResume() {
        super.onResume();
        updateRecycler();
    }

    private void updateRecycler(){
        binding.mainRecyclerViewId.setAdapter(new NoteAdapter(NoteManager.getInstance(this).getNoteInfoList()));

    }

}