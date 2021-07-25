package com.guygool5.notesplusplus.activities;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.guygool5.notesplusplus.R;
import com.guygool5.notesplusplus.adapters.NoteAdapter;
import com.guygool5.notesplusplus.databinding.ActivityMainBinding;
import com.guygool5.notesplusplus.databinding.DialogAboutBinding;
import com.guygool5.notesplusplus.handlers.NoteManager;
import com.guygool5.notesplusplus.objects.notes.NoteInfo;
import com.guygool5.notesplusplus.utilities.logger.LogType;
import com.guygool5.notesplusplus.utilities.logger.Logger;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

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

            View dialogView = (LayoutInflater.from(this).inflate(R.layout.dialog_about, null, false));
            DialogAboutBinding aboutBinding = DataBindingUtil.bind(dialogView);
            MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(this);
            dialogBuilder.setTitle(R.string.text_about);
            dialogBuilder.setView(dialogView);
            Objects.requireNonNull(aboutBinding);

            //Mail Intent
            aboutBinding.dialogAboutEmail.setOnClickListener(w->{
                Intent mailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
                mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {aboutBinding.dialogAboutEmail.getText().toString()});
                startActivity(mailIntent);
            });

            //View Intent
            aboutBinding.dialogAboutGithub.setOnClickListener(w->{
                Intent webIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(aboutBinding.dialogAboutGithub.getText().toString()));
                startActivity(webIntent);
            });

            //Dial Intent
            aboutBinding.dialogAboutPhone.setOnClickListener(w->{
                Intent dialIntent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+aboutBinding.dialogAboutPhone.getText().toString()));
                startActivity(dialIntent);
            });

            //Web search Intent
            aboutBinding.dialogAboutWebSearchId.setOnClickListener(w->{
                Intent webSearchIntent = new Intent(Intent.ACTION_WEB_SEARCH);
                webSearchIntent.putExtra(SearchManager.QUERY,"Guy Tsitsiashvili");
                startActivity(webSearchIntent);
            });

            //Calendar Intent
            aboutBinding.dialogAboutCalendarId.setOnClickListener(w->{
                Intent calendarIntent = new Intent(Intent.ACTION_INSERT);
                Calendar begin = Calendar.getInstance();
                begin.add(Calendar.DATE,7);
                calendarIntent
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,begin.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE,"Call Guy")
                        .putExtra(CalendarContract.Events.DESCRIPTION,"Call guy from the application Notes++")
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                        .putExtra(Intent.EXTRA_EMAIL,aboutBinding.dialogAboutEmail.getText().toString());
                startActivity(calendarIntent);
            });

            //Alarm Intent
            aboutBinding.dialogAboutAlarmId.setOnClickListener(w->{
                Intent actionAlarm = new Intent(AlarmClock.ACTION_SET_TIMER);
                actionAlarm.putExtra(AlarmClock.EXTRA_LENGTH,3600);
                actionAlarm.putExtra(AlarmClock.EXTRA_MESSAGE,"Call Guy");
                startActivity(actionAlarm);
            });

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