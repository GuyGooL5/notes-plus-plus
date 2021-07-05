package com.guygool5.notesplusplus.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.guygool5.notesplusplus.R;
import com.guygool5.notesplusplus.adapters.TextNoteAdapter;
import com.guygool5.notesplusplus.databinding.ActivityTextNoteBinding;
import com.guygool5.notesplusplus.databinding.DialogNoteEditTitleBinding;
import com.guygool5.notesplusplus.handlers.NoteFileHandler;
import com.guygool5.notesplusplus.models.IconButtonModel;
import com.guygool5.notesplusplus.objects.notes.NoteType;
import com.guygool5.notesplusplus.utilities.logger.LogType;
import com.guygool5.notesplusplus.utilities.logger.Logger;

import java.io.IOException;
import java.util.Objects;

//This activity will handle simple text notes, load, save and share them.
public class TextNoteActivity extends AppCompatActivity {

    TextNoteAdapter textNoteAdapter;
    ActivityTextNoteBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //IMPORTANT!!! Do not change the order of invocation!!!

        //We'll use binding to create the activity to dynamically handle data.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_text_note);

        //First we'll try to get a UUID passed by the intent.
        String uuid = getIntent().getStringExtra("UUID");

        textNoteAdapter = initTextNoteAdapter(uuid);

        initEditTitleSection();
        initRootActivitySection();
        initTopAppBarSection();

    }

    //This function will handle the loading/creation/error-handling of the TextNoteAdapter.
    private TextNoteAdapter initTextNoteAdapter(String uuid) {
        //We'll see if a uuid was present on the intent, if not we will create a new TextNoteAdapter to handle the activity.
        if (uuid == null) return new TextNoteAdapter();

        //Otherwise we'll try to load it from the disk.
        try {
            //If we've successfully loaded the note we'll return it.
            return new TextNoteAdapter(NoteFileHandler.loadNote(this, uuid));

            //TODO: Delete this logger.
            //Logger.log(LogType.NOTE_FILE, "Loaded TextNote:", textNoteAdapter.getTextNote().toString());

        } catch (IOException | ClassNotFoundException e) {
            //If the loading failed, we'll prompt to the screen an error message.
            //TODO: Add StringRes.
            Snackbar.make(binding.getRoot(), "Failed to load note. Creating new.", Snackbar.LENGTH_SHORT).show();

            //It also means the UUID is somehow broken, we'll delete it.
            //TODO: Try to handle IOException differently.
            NoteFileHandler.deleteNote(this, uuid, NoteType.TEXT);

            //TODO: Delete this logger.
            //Logger.log(LogType.NOTE_FILE, "Failed to load TextNote:", uuid, "Exception:", e.toString());

            //Then we will simply create a new TextNoteAdapter to handle the activity.
            return new TextNoteAdapter();

        }

    }

    //This part will initialize the logic for the Title and edit title dialog ui elements.
    private void initEditTitleSection() {
        //This will handle the Title edit dialog.
        //We'll create a MaterialAlertDialogBuilder with a custom view and binding to change the title of the TextNoteAdapter.

        MaterialAlertDialogBuilder editTitleAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        editTitleAlertDialogBuilder.setTitle(R.string.dialog_note_edit_title);

        //This button will dismiss the dialog.
        editTitleAlertDialogBuilder.setNeutralButton(R.string.button_cancel, ((dialogInterface, whichButton) -> dialogInterface.cancel()));

        //This IconButtonModel will bind the icon and onClickListener.
        IconButtonModel editIconButtonModel = new IconButtonModel(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_edit_24, null));
        binding.setEditIconButtonModel(editIconButtonModel);
        //The onClickListener will recreate the Edit Title dialog with extra logic in updateEditTitleDialog() method.
        editIconButtonModel.setOnClickListener((v) -> {
            updateEditTitleDialog(editTitleAlertDialogBuilder);
        });

    }

    //This part will initialize the logic for the layout of the activity generally.
    private void initRootActivitySection() {
        //Now we'll set the TextNoteModel to the one the adapter handles.
        binding.setTextNoteModel(textNoteAdapter.getTextNoteModel());

        //We'll also set the text of the note in the UI to the one in the adapter and then focus on it.
        binding.noteDescriptionEditId.setText(textNoteAdapter.getTextNote().getText());
        binding.noteDescriptionEditId.setFocusable(true);

        //Finally we'll define how to handle the cancel and save buttons.
        //Cancel simply finishes the activity without changes.
        binding.noteButtonCancelId.setOnClickListener((v) -> finish());

        //The save button will call the saveNote() method.
        binding.noteButtonAddId.setOnClickListener(v -> saveNote(binding.getRoot()));


    }

    //This part will initialize the logic for the top app bar section.
    private void initTopAppBarSection() {
        MaterialToolbar toolbar = binding.noteToolbarId;
        //First we'll set the navigation icon button to finish the activity.
        toolbar.setNavigationOnClickListener(v -> finish());


        /*  This will handle sharing the note.
            Since it's a text note we'll share it simply as plain text.
            We'll first find the share menu item and create a click listener*/
        toolbar.getMenu().findItem(R.id.text_note_menu_share_id).setOnMenuItemClickListener(item -> {

            //Now will start an ACTION_SEND intent.
            Intent intent = new Intent(Intent.ACTION_SEND);
            //We'll pass the TextNote as string to it (it's already formatted).
            intent.putExtra(Intent.EXTRA_TEXT, textNoteAdapter.getTextNote().toString());
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
            deleteConfirmationDialogBuilder.setNegativeButton(R.string.button_no,((dialog, which) -> dialog.cancel()));
            deleteConfirmationDialogBuilder.setPositiveButton(R.string.button_yes,((dialog, which) -> {
                NoteFileHandler.deleteNote(this, textNoteAdapter.getTextNote().getUuid(), textNoteAdapter.getTextNote().getType());
                finish();
            }));
            deleteConfirmationDialogBuilder.show();
            return false;
        });
    }

    //This method will try to save the note and if it fails it will prompt to the rootView a Snackbar with a 'try again' button that calls the function itself.
    private void saveNote(View rootView) {
        try {
            //If the save is successful we'll close the activity.
            NoteFileHandler.saveNote(this, textNoteAdapter.getTextNote());
            Logger.log(LogType.NOTE_FILE, "Saved ", textNoteAdapter.getTextNote().toString());
            finish();
        } catch (IOException e) {
            //Otherwise we will prompt a Snackbar to try again. it will call this function recursively.
            //TODO: Add StringRes.
            Snackbar.make(rootView, "Failed to save note", Snackbar.LENGTH_SHORT).setAction("Try Again", v -> saveNote(rootView)).show();
            Logger.log(LogType.NOTE_FILE, "Failed to save", textNoteAdapter.getTextNote().toString(), "Exception:", e.toString());
        }

    }

    //This method will recreate the dialog with updated data each time.
    private void updateEditTitleDialog(MaterialAlertDialogBuilder editTitleAlertDialogBuilder) {
        //First we'll inflate the view of the text field.
        View dialogView = LayoutInflater.from(editTitleAlertDialogBuilder.getContext()).inflate(R.layout.dialog_note_edit_title, null);
        //Then we will use data binding on the inflated view.
        DialogNoteEditTitleBinding dialogNoteEditTitleBinding = DataBindingUtil.bind(dialogView);

        //Then we'll set the text to that of the TextNoteAdapter.
        dialogNoteEditTitleBinding.dialogNoteEditTitleEditTextId.setText(textNoteAdapter.getTitle());

        //We'll update the view of the MaterialAlertDialogBuilder to the inflated view.
        editTitleAlertDialogBuilder.setView(dialogView);

        //Finally we'll set an onClickListener of OK to update the title of the TextNoteAdapter and dismiss the dialog.
        editTitleAlertDialogBuilder.setPositiveButton(R.string.button_ok, (dialogInterface, whichButton) -> {
            textNoteAdapter.setTitle(Objects.requireNonNull(dialogNoteEditTitleBinding.dialogNoteEditTitleEditTextId.getText()).toString());
            dialogInterface.cancel();
        });

        //After the preparations we'll show the dialog.
        editTitleAlertDialogBuilder.show();

    }
}