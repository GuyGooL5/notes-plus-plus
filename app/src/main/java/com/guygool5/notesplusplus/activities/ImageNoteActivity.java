package com.guygool5.notesplusplus.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.guygool5.notesplusplus.R;
import com.guygool5.notesplusplus.databinding.ActivityImageNoteBinding;
import com.guygool5.notesplusplus.utilities.logger.LogType;
import com.guygool5.notesplusplus.utilities.logger.Logger;

public class ImageNoteActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> takePictureLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityImageNoteBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_image_note);

        takePictureLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Bitmap image = (Bitmap) data.getExtras().get("data");
                    binding.imageNoteImageId.setImageBitmap(image);
                }
            }
        });

        binding.imageNoteButtonSaveId.setOnClickListener(v -> {
            try {
                takePictureLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            } catch (Exception e) {
                Logger.log(LogType.EXCEPTION,e);
                Snackbar.make(this, binding.getRoot(), "Can't find a camera app", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}