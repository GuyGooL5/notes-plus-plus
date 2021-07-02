package com.guygool5.notesplusplus.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.guygool5.notesplusplus.R;
import com.guygool5.notesplusplus.adapters.NoteAdapter;
import com.guygool5.notesplusplus.databinding.ActivityNoteBinding;
import com.guygool5.notesplusplus.databinding.DialogNoteEditTitleBinding;

import java.util.Objects;

public class NoteActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNoteBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_note);
        NoteAdapter noteAdapter = new NoteAdapter();
        MaterialAlertDialogBuilder editTitleAlertDialogBuilder = new MaterialAlertDialogBuilder(this);

        View dialogView = View.inflate(editTitleAlertDialogBuilder.getContext(),R.layout.dialog_note_edit_title,null);
        DialogNoteEditTitleBinding dialogNoteEditTitleBinding = DataBindingUtil.bind(dialogView);
        assert dialogNoteEditTitleBinding != null;
        editTitleAlertDialogBuilder.setTitle(R.string.dialog_note_edit_title);
        editTitleAlertDialogBuilder.setView(dialogView);
        //dismiss the alert dialog
        editTitleAlertDialogBuilder.setNeutralButton(R.string.button_cancel, ((dialogInterface, whichButton) -> dialogInterface.cancel()));

        editTitleAlertDialogBuilder.setPositiveButton(R.string.button_ok, (dialogInterface, whichButton) -> {
            noteAdapter.setTitle(Objects.requireNonNull(dialogNoteEditTitleBinding.dialogNoteEditTitleEditTextId.getText()).toString());
            dialogInterface.cancel();
        });
        binding.setNoteModel(noteAdapter.getNoteModel());
        binding.setEditIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_edit_24, null));

        binding.noteButtonCancelId.setOnClickListener((v) -> finish());
        binding.noteIconButtonEditTitleId.setOnClickListener((v) ->
            editTitleAlertDialogBuilder.show());
        dialogNoteEditTitleBinding.dialogNoteEditTitleEditTextId.setText(noteAdapter.getTitle());
    }
}