package com.guygool5.notesplusplus.handlers;

import android.content.Context;

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
    private static Object loadNoteGeneric(Context context,@NotNull String uuid ) throws ClassNotFoundException, IOException {
        try (FileInputStream inFile = context.openFileInput(uuid)) {
            try (ObjectInputStream inStream = new ObjectInputStream(inFile)) {
                return inStream.readObject();
            }
        }
    }

    public static TextNote loadNote(Context context,@NotNull String uuid) throws IOException, ClassNotFoundException {
        return (TextNote)loadNoteGeneric(context,uuid);
    }

    //TODO: Add loadImageNote.

    private static <T extends Note> void saveNoteGeneric(Context context, T note) throws IOException {
        try(FileOutputStream outFile = context.openFileOutput(note.getUuid(),MODE_PRIVATE)){
            try(ObjectOutputStream outStream = new ObjectOutputStream(outFile)){
                outStream.writeObject(note);
                SharedPreferencesHandler sharedPreferencesHandler = new SharedPreferencesHandler(context);
                sharedPreferencesHandler.addNoteUUID(note.getUuid(),note.getType());
            }
        }
    }

    public static void saveNote(Context context,TextNote note) throws IOException {
        NoteFileHandler.saveNoteGeneric(context,note);
    }
    //TODO: Add saveNote with ImageNote.

    public static void deleteNote(  Context context,String uuid, NoteType type) {
        try {
            context.deleteFile(uuid);
        }catch (Exception e){
            Logger.log(LogType.NOTE_FILE,"Exception:",e.toString());
        }finally {
            SharedPreferencesHandler sharedPreferencesHandler= new SharedPreferencesHandler(context);
            sharedPreferencesHandler.removeNoteUUID(uuid,type);
        }
    }

}