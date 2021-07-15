package com.guygool5.notesplusplus.handlers;

import android.content.Context;

import androidx.annotation.NonNull;

import com.guygool5.notesplusplus.objects.notes.ImageNote;
import com.guygool5.notesplusplus.objects.notes.Note;
import com.guygool5.notesplusplus.objects.notes.NoteType;
import com.guygool5.notesplusplus.objects.notes.TextNote;
import com.guygool5.notesplusplus.utilities.logger.LogType;
import com.guygool5.notesplusplus.utilities.logger.Logger;

import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static android.content.Context.MODE_PRIVATE;

public class NoteFileHandler {
    public static <T> T loadNote(Context context, @NotNull String uuid) throws ClassNotFoundException, IOException {
        try (FileInputStream inFile = context.openFileInput(uuid)) {
            try (ObjectInputStream inStream = new ObjectInputStream(inFile)) {
                return (T)inStream.readObject();
            }
        }
    }

    public static <T extends Note> void saveNote(Context context, T note) throws IOException {
        try (FileOutputStream outFile = context.openFileOutput(note.getUuid(), MODE_PRIVATE)) {
            try (ObjectOutputStream outStream = new ObjectOutputStream(outFile)) {
                outStream.writeObject(note);
                SharedPreferencesHandler sharedPreferencesHandler = new SharedPreferencesHandler(context);
                sharedPreferencesHandler.addNoteUUID(note.getUuid(), note.getType());
            }
        }
    }

    public static void deleteNote(Context context, String uuid, NoteType type) {
        try {
            context.deleteFile(uuid);
        } catch (Exception e) {
            Logger.log(LogType.NOTE_FILE, "Exception:", e.toString());
        } finally {
            SharedPreferencesHandler sharedPreferencesHandler = new SharedPreferencesHandler(context);
            sharedPreferencesHandler.removeNoteUUID(uuid, type);
        }
    }

}
