package com.guygool5.notesplusplus.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.guygool5.notesplusplus.R;
import com.guygool5.notesplusplus.databinding.ActivityImageNoteBinding;
import com.guygool5.notesplusplus.dialogs.TextFieldDialog;
import com.guygool5.notesplusplus.handlers.NoteFileHandler;
import com.guygool5.notesplusplus.models.IconButtonModel;
import com.guygool5.notesplusplus.models.ImageNoteModel;
import com.guygool5.notesplusplus.objects.notes.ImageNote;
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


        initRootActivitySection();

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
        binding.imageNoteButtonSaveId.setOnClickListener(v -> {
            try {
                takePictureLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            } catch (Exception e) {
                Logger.log(LogType.EXCEPTION, e);
                popSnackbar("Can't find a camera app");
            }
        });
    }

    private ImageNoteModel getTextNoteModel(String uuid) {
        if (uuid == null) return new ImageNoteModel();

        try {
            return new ImageNoteModel(NoteFileHandler.loadNote(this, uuid));
        } catch (IOException | ClassNotFoundException e) {
            popSnackbar("Failed to load Image note");
        }
        return new ImageNoteModel();
    }

    private void initRootActivitySection() {
        String uuid = getIntent().getStringExtra("UUID");
        imageNoteModel = getTextNoteModel(uuid);
        binding.setImageNoteModel(imageNoteModel);

        //TODO: Add rest of functionality.

    }

    private void initEditTitleDialog() {
        TextFieldDialog textFieldDialog = new TextFieldDialog(this).setTitle(R.string.dialog_note_edit_title);
        textFieldDialog.setOnSaveListener((dialogInterface, text) -> {
            imageNoteModel.setTitle(text);
            dialogInterface.cancel();
        });
        binding.setEditIconButtonModel(new IconButtonModel(
                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_edit_24, null),
                //TODO: Delete this
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
                    popSnackbar("Invalid URL");
                    return null;
                } catch (IOException e) {
                    popSnackbar("Can't open the url as Image");
                    return null;
                } catch (Exception e) {
                    Logger.log(LogType.IMAGE_NOTE, "from future:", e);
                }
                return null;
            });
            Bitmap image = null;
            try {
                image = futureImage.get();
            } catch (ExecutionException | InterruptedException e) {
                popSnackbar("Failed to load image");
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

    private void popSnackbar(CharSequence charSequence) {
        Snackbar.make(this, binding.getRoot(), charSequence, Snackbar.LENGTH_SHORT).show();
    }


}