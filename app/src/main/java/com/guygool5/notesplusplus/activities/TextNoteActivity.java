package com.guygool5.notesplusplus.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.guygool5.notesplusplus.R;
import com.guygool5.notesplusplus.databinding.ActivityTextNoteBinding;
import com.guygool5.notesplusplus.dialogs.TextFieldDialog;
import com.guygool5.notesplusplus.handlers.NoteManager;
import com.guygool5.notesplusplus.models.IconButtonModel;
import com.guygool5.notesplusplus.models.TextNoteModel;
import com.guygool5.notesplusplus.utilities.logger.LogType;
import com.guygool5.notesplusplus.utilities.logger.Logger;

import java.io.IOException;

//This activity will handle simple text notes, load, save and share them.
public class TextNoteActivity extends AppCompatActivity {

    TextNoteModel textNoteModel;
    ActivityTextNoteBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_text_note);

        String uuid = getIntent().getStringExtra("UUID");
        textNoteModel = initTextNoteModel(uuid);
        binding.setTextNoteModel(textNoteModel);

        binding.textNoteButtonCancelId.setOnClickListener((v) -> finish());
        binding.textNoteButtonSaveId.setOnClickListener(v -> saveNote());

        binding.textNoteDescriptionEditId.setText(textNoteModel.getTextNote().getText());
        binding.textNoteDescriptionEditId.setFocusable(true);


        initEditTitleSection();
        initRootActivitySection();
        initTopAppBarSection();

    }

    //This function will handle the loading/creation/error-handling of the TextNoteAdapter.
    private TextNoteModel initTextNoteModel(String uuid) {
        NoteManager noteManager = NoteManager.getInstance(this);
        if (uuid == null) return new TextNoteModel();
        try {
            return new TextNoteModel(noteManager.loadNote(uuid));
        } catch (IOException | ClassNotFoundException e) {
            Snackbar snackbar = Snackbar.make(binding.getRoot(),R.string.note_snackbar_failed_to_load, BaseTransientBottomBar.LENGTH_SHORT);
            snackbar.setAction(R.string.button_ok,v-> snackbar.dismiss()).show();
            noteManager.deleteNote(uuid);
         return new TextNoteModel();
        }
    }

    //This part will initialize the logic for the Title and edit title dialog ui elements.
    private void initEditTitleSection() {

        TextFieldDialog editTitleDialog = new TextFieldDialog(this)
                .setTitle(R.string.dialog_note_edit_title)
                .setOnSaveListener((dialogInterface, title) -> {
                    textNoteModel.setTitle(title);
                    dialogInterface.cancel();
                });

        IconButtonModel editIconButtonModel = new IconButtonModel(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_edit_24, null));
        editIconButtonModel.setOnClickListener(v -> editTitleDialog.open(textNoteModel.getTitle()));

        binding.setEditIconButtonModel(editIconButtonModel);
    }

    //This part will initialize the logic for the layout of the activity generally.
    private void initRootActivitySection() {


    }

    //This part will initialize the logic for the top app bar section.
    private void initTopAppBarSection() {
        MaterialToolbar toolbar = binding.textNoteToolbarId;
        //First we'll set the navigation icon button to finish the activity.
        toolbar.setNavigationOnClickListener(v -> finish());


        /*  This will handle sharing the note.
            Since it's a text note we'll share it simply as plain text.
            We'll first find the share menu item and create a click listener*/
        toolbar.getMenu().findItem(R.id.text_note_menu_share_id).setOnMenuItemClickListener(item -> {

            //Now will start an ACTION_SEND intent.
            Intent intent = new Intent(Intent.ACTION_SEND);
            //We'll pass the TextNote as string to it (it's already formatted).
            intent.putExtra(Intent.EXTRA_TEXT, textNoteModel.getTextNote().toString());
            //We're intending to send is text/plain meta-type.
            intent.setType("text/plain");

            //We'll try to start the intent.
            try {
                startActivity(intent);
            } catch (Exception e) {
                //If we fail to start it, it means no app can receive the data. We'll prompt in a Snackbar the error.
                //TODO: Add StringRes.
                Snackbar.make(binding.getRoot(), "Found no app we can share this with.", Snackbar.LENGTH_SHORT).show();
            }
            return false;
        });

        //This will handle deleting the note.
        toolbar.getMenu().findItem(R.id.text_note_menu_delete_id).setOnMenuItemClickListener(item -> {

            //After the delete was pressed we'll prompt a confirmation dialog.
            MaterialAlertDialogBuilder deleteConfirmationDialogBuilder = new MaterialAlertDialogBuilder(this);
            deleteConfirmationDialogBuilder.setTitle("Delete Note");
            deleteConfirmationDialogBuilder.setMessage("This cannot be undone, are you sure you want to delete the note?");
            deleteConfirmationDialogBuilder.setNegativeButton(R.string.button_no, ((dialog, which) -> dialog.cancel()));
            deleteConfirmationDialogBuilder.setPositiveButton(R.string.button_yes, ((dialog, which) -> {
                NoteManager.getInstance(this).deleteNote(textNoteModel.getTextNote().getUuid());
                finish();
            }));
            deleteConfirmationDialogBuilder.show();
            return false;
        });
    }

    //This method will try to save the note and if it fails it will prompt to the rootView a Snackbar with a 'try again' button that calls the function itself.
    private void saveNote() {
        try {
            NoteManager.getInstance(this).saveNote(textNoteModel.getTextNote());
            finish();
        } catch (IOException e) {
            //TODO: Add StringRes.
            Snackbar.make(binding.getRoot(), "Failed to save note", Snackbar.LENGTH_SHORT).setAction("Try Again", v -> saveNote()).show();
        }

    }
}