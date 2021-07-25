package com.guygool5.notesplusplus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

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

        binding.noteToolbarId.getMenu().findItem(R.id.main_menu_about_id).setOnMenuItemClickListener(v -> {

            MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(this);
            dialogBuilder.setTitle(R.string.text_about);
            dialogBuilder.setView(LayoutInflater.from(this).inflate(R.layout.dialog_about, null, false));
            dialogBuilder.show();
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateRecycler();
    }

    private void updateRecycler() {
        binding.mainRecyclerViewId.setAdapter(new NoteAdapter(NoteManager.getInstance(this).getNoteInfoList()));

    }

}