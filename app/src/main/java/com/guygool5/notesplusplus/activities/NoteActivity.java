package com.guygool5.notesplusplus.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.guygool5.notesplusplus.R;
import com.guygool5.notesplusplus.databinding.ActivityNoteBinding;
import com.guygool5.notesplusplus.models.NoteModel;
import com.guygool5.notesplusplus.utilities.logger.LogType;
import com.guygool5.notesplusplus.utilities.logger.Logger;

import java.util.Objects;

public class NoteActivity extends AppCompatActivity {

    private static void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNoteBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_note);
        NoteModel note = new NoteModel();
        note.setTitle("Hello There");

        binding.setNote(note);
        binding.setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_baseline_edit_24));

        binding.noteButtonCancelId.setOnClickListener((v) -> finish());
        binding.noteIconButtonEditTitleId.setOnClick((v) -> {
        Logger.log(LogType.NOTE_TEXT, "clicked on edit");
                    Logger.log(LogType.NOTE_TEXT, "Note's title is:", note.getTitle());
                    View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_note_edit_title, null, false);
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
                    materialAlertDialogBuilder.setTitle(R.string.dialog_note_edit_title);
                    materialAlertDialogBuilder.setView(dialogView);
                    ((TextInputEditText)dialogView.findViewById(R.id.dialog_note_edit_title_editText_id)).setText(note.getTitle());
                    materialAlertDialogBuilder.setNeutralButton(R.string.button_cancel, ((dialogInterface, i) -> dialogInterface.cancel()));
                    materialAlertDialogBuilder.setPositiveButton(R.string.button_ok, (dialogInterface, i) -> {
                        TextInputEditText textInputEditText = dialogView.findViewById(R.id.dialog_note_edit_title_editText_id);
                        note.setTitle(Objects.requireNonNull(textInputEditText.getText()).toString());
                        Logger.log(LogType.NOTE_TEXT, "Note's title now is: ", note.getTitle());
                        dialogInterface.cancel();
                    });
                    materialAlertDialogBuilder.show();
                }
        );

    }
}