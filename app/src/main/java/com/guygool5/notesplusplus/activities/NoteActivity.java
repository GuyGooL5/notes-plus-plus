package com.guygool5.notesplusplus.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.guygool5.notesplusplus.R;
import com.guygool5.notesplusplus.adapters.NoteAdapter;
import com.guygool5.notesplusplus.databinding.ActivityNoteBinding;

import java.util.Objects;

public class NoteActivity extends AppCompatActivity {

    ActivityNoteBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_note);
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_note);
        noteAdapter = new NoteAdapter();
        binding.setNoteModel(noteAdapter.getNoteModel());
        binding.setIcon(getDrawable(R.drawable.ic_baseline_edit_24));

        binding.noteButtonCancelId.setOnClickListener((v) -> finish());
        binding.noteIconButtonEditTitleId.setOnClickListener((v) -> showChangeTitleDialog());

        //TODO:delete this.
    }

    private void showChangeTitleDialog() {

        //Inflate the layout to view inside the dialog.
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_note_edit_title, null, false);
        //Locate the Edit text view and populate with the title of the note.
        TextInputEditText dialogEditText = dialogView.findViewById(R.id.dialog_note_edit_title_editText_id);
        dialogEditText.setText(noteAdapter.getTitle());

        /*Create an Alert Dialog Builder with
            title, ok and cancel buttons, the view to be the dialogView object we inflated and
            creating listeners for the buttons.
         */
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        materialAlertDialogBuilder.setTitle(R.string.dialog_note_edit_title);
        materialAlertDialogBuilder.setView(dialogView);
        //dismiss the alert dialog
        materialAlertDialogBuilder.setNeutralButton(R.string.button_cancel, ((dialogInterface, whichButton) -> dialogInterface.cancel()));

        //update the title of the note object.
        materialAlertDialogBuilder.setPositiveButton(R.string.button_ok, (dialogInterface, whichButton) -> {
            noteAdapter.setTitle(Objects.requireNonNull(dialogEditText.getText(), "").toString());
            dialogInterface.cancel();
        });
        materialAlertDialogBuilder.show();
    }
}