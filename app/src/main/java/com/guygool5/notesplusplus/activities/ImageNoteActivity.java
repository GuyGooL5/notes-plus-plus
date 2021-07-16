package com.guygool5.notesplusplus.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.guygool5.notesplusplus.R;
import com.guygool5.notesplusplus.databinding.ActivityImageNoteBinding;
import com.guygool5.notesplusplus.dialogs.TextFieldDialog;
import com.guygool5.notesplusplus.handlers.NoteManager;
import com.guygool5.notesplusplus.models.IconButtonModel;
import com.guygool5.notesplusplus.models.ImageNoteModel;
import com.guygool5.notesplusplus.utilities.logger.LogType;
import com.guygool5.notesplusplus.utilities.logger.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ImageNoteActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> takePictureLauncher;
    ActivityImageNoteBinding binding;
    ImageNoteModel imageNoteModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_note);

        String uuid = getIntent().getStringExtra("UUID");
        imageNoteModel = getModel(uuid);
        binding.setImageNoteModel(imageNoteModel);

        binding.imageNoteButtonCancelId.setOnClickListener(v -> finish());
        binding.imageNoteButtonSaveId.setOnClickListener(v -> saveNote());


        initEditTitleDialog();
        initSetImageButtons();

        takePictureLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Bitmap image = (Bitmap) data.getExtras().get("data");
                    imageNoteModel.setBitmap(image);
                }
            }
        });

        initTopAppBarSection();
    }

    private ImageNoteModel getModel(String uuid) {
        NoteManager noteManager = NoteManager.getInstance(this);
        if (uuid == null) return new ImageNoteModel();
        try {
            return new ImageNoteModel(noteManager.loadNote(uuid));
        } catch (IOException | ClassNotFoundException e) {
            Snackbar snackbar = Snackbar.make(binding.getRoot(), R.string.note_snackbar_failed_to_load, BaseTransientBottomBar.LENGTH_SHORT);
            snackbar.setAction(R.string.button_ok, v -> snackbar.dismiss()).show();
            noteManager.deleteNote(uuid);
            return new ImageNoteModel();
        }
    }


    private void initEditTitleDialog() {
        TextFieldDialog textFieldDialog = new TextFieldDialog(this).setTitle(R.string.dialog_note_edit_title);
        textFieldDialog.setOnSaveListener((dialogInterface, text) -> {
            imageNoteModel.setTitle(text);
            dialogInterface.cancel();
        });
        binding.setEditIconButtonModel(new IconButtonModel(
                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_edit_24, null),
                v -> textFieldDialog.open(imageNoteModel.getTitle())));
    }

    private void initSetImageButtons() {
        TextFieldDialog loadUrlDialog = new TextFieldDialog(this).setTitle(R.string.button_load_url);
        loadUrlDialog.setOnSaveListener((dialogInterface, urlString) -> {
            CompletableFuture<Bitmap> futureImage = CompletableFuture.supplyAsync(() -> {
                try {
                    URL url = new URL(urlString);
                    return BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    Snackbar.make(binding.getRoot(), "Invalid URL", Snackbar.LENGTH_SHORT).show();
                    return null;
                } catch (IOException e) {
                    Snackbar.make(binding.getRoot(), "Can't open the url as Image", Snackbar.LENGTH_SHORT).show();
                    return null;
                } catch (Exception e) {
                    //TODO: manage this.
                    Logger.log(LogType.IMAGE_NOTE, "from future:", e);
                }
                return null;
            });
            Bitmap image = null;
            try {
                image = futureImage.get();
            } catch (ExecutionException | InterruptedException e) {
                Snackbar.make(binding.getRoot(), "Failed to load image", Snackbar.LENGTH_SHORT).show();
            }
            Logger.log(LogType.IMAGE_NOTE, "The image reference:", image);
            if (image != null) {
                imageNoteModel.setBitmap(image);
                dialogInterface.cancel();
            }

        });

        binding.imageNoteButtonTakePictureId.setOnClickListener(v -> takePictureLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)));
        //TODO: Delete this.
        binding.imageNoteButtonLoadUrl.setOnClickListener(v -> loadUrlDialog.open("https://images.unsplash.com/photo-1612151855475-877969f4a6cc?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8aGQlMjBpbWFnZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&w=1000&q=8"));
        //binding.imageNoteButtonLoadUrl.setOnClickListener(v -> loadUrlDialog.open());
    }

    private void saveNote() {
        try {
            NoteManager.getInstance(this).saveNote(imageNoteModel.getImageNote());
            finish();
        } catch (IOException e) {
            //TODO: Add StringRes.
            Snackbar.make(binding.getRoot(), "Failed to save note", Snackbar.LENGTH_SHORT).setAction("Try Again", v -> saveNote()).show();
        }

    }

    private void initTopAppBarSection() {
        MaterialToolbar toolbar = binding.imageNoteToolbarId;
        toolbar.setOnClickListener(v -> finish());

        toolbar.getMenu().findItem(R.id.text_note_menu_share_id).setOnMenuItemClickListener(item -> {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, imageNoteModel.getTitle());
            String uri = MediaStore.Images.Media.insertImage(this.getContentResolver(), imageNoteModel.getBitmap(), "Image", "Sent by Notes++");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(uri));
            intent.setType("image/*");
            startActivity(Intent.createChooser(intent, "Share Image Note"));

            return false;
        });

        toolbar.getMenu().findItem(R.id.text_note_menu_delete_id).setOnMenuItemClickListener(item -> {

            //After the delete was pressed we'll prompt a confirmation dialog.
            MaterialAlertDialogBuilder deleteConfirmationDialogBuilder = new MaterialAlertDialogBuilder(this);
            deleteConfirmationDialogBuilder.setTitle("Delete Note");
            deleteConfirmationDialogBuilder.setMessage("This cannot be undone, are you sure you want to delete the note?");
            deleteConfirmationDialogBuilder.setNegativeButton(R.string.button_no, ((dialog, which) -> dialog.cancel()));
            deleteConfirmationDialogBuilder.setPositiveButton(R.string.button_yes, ((dialog, which) -> {
                NoteManager.getInstance(this).deleteNote(imageNoteModel.getImageNote().getUuid());
                finish();
            }));
            deleteConfirmationDialogBuilder.show();
            return false;
        });
    }


}