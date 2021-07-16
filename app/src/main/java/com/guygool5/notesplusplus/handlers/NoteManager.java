package com.guygool5.notesplusplus.handlers;

import android.content.Context;

import com.guygool5.notesplusplus.objects.notes.Note;
import com.guygool5.notesplusplus.objects.notes.NoteInfo;
import com.guygool5.notesplusplus.objects.notes.TextNote;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * This class is a Singleton that manages all the notes in the app.<br/>
 * It holds a {@link Map} of all the notes and with their UUIDs as keys and can
 * add, remove, rearrange, save and load the notes.
 */
public class NoteManager {

    private static final String NOTE_LIST_DATA_NAME_TAG = "NOTE_LIST_DATA";

    private static WeakReference<NoteManager> single_instance = null;

    //This will be the application context
    private final Context context;

    //This is a UUID, NoteInfo map.
    private Map<String, NoteInfo> noteInfoMap;

    /**
     * This method will return an instance to this class' singleton
     *
     * @param context the context of the activity/application
     * @return a singleton of {@link NoteManager}
     */
    public static NoteManager getInstance(Context context) {
        if (single_instance==null || single_instance.get() == null)
            single_instance = new WeakReference<>(new NoteManager(context.getApplicationContext()));
        return single_instance.get();
    }


    /**
     * This is a silent constructor of the singleton
     *
     * @param context the application context.
     */
    private NoteManager(Context context) {
        this.context = context;
        if (noteInfoMap == null) {
            try {
                noteInfoMap = loadNoteInfoMap();
            } catch (IOException | ClassNotFoundException e) {
                noteInfoMap = new HashMap<>();
            }
        }
    }


    /**
     * This method will save the set of the notes to the storage for loading.
     *
     * @throws IOException if the writing failed.
     */
    private void save() throws IOException {
        try (FileOutputStream fOutStream = context.openFileOutput(NOTE_LIST_DATA_NAME_TAG, Context.MODE_PRIVATE)) {
            try (ObjectOutputStream oOutStream = new ObjectOutputStream(fOutStream)) {
                oOutStream.writeObject(noteInfoMap);
            }
        }
    }


    /**
     * This method will load the notes map from the storage
     *
     * @return a {@link HashMap} of the {@link NoteInfo} objects with their UUIDs' as {@link String} keys
     * @throws IOException            if the loading failed
     * @throws ClassNotFoundException if the serialized class mismatched.
     */
    private Map<String, NoteInfo> loadNoteInfoMap() throws IOException, ClassNotFoundException {
        try (FileInputStream fInStream = context.openFileInput(NOTE_LIST_DATA_NAME_TAG)) {
            try (ObjectInputStream oOutStream = new ObjectInputStream(fInStream)) {
                return (HashMap<String, NoteInfo>) oOutStream.readObject();
            }
        }
    }


    /**
     * This method adds a new note or updates an existing note in the list and on the disk
     *
     * @param note The note to add/update
     * @param <T>  is a type of {@link Note} and can be for example {@link TextNote}
     * @throws IOException if the note cannot be saved.
     */
    public <T extends Note> void saveNote(T note) throws IOException {
        try (FileOutputStream outFile = context.openFileOutput(note.getUuid(), MODE_PRIVATE)) {
            try (ObjectOutputStream outStream = new ObjectOutputStream(outFile)) {
                outStream.writeObject(note);
                NoteInfo noteInfo = new NoteInfo(note);
                noteInfoMap.put(noteInfo.getUuid(), noteInfo);
                save();
            }
        }
    }

    /**
     * This method will delete a note from the storage and from the map and also save the updated map
     *
     * @param noteInfo a note info object of the note to delete
     */
    public void deleteNote(NoteInfo noteInfo) {
        deleteNote(noteInfo.getUuid());
    }

    /**
     * This method will delete a note from the storage and from the map and also save the updated map
     *
     * @param uuid a UUID of the note to delete
     */
    public void deleteNote(String uuid) {
        context.deleteFile(uuid);
        noteInfoMap.remove(uuid);
    }

    /**
     * This method will load a note from the storage.
     *
     * @param noteInfo a note info object that has the UUID of the note to load.
     * @param <T>      is an extension of {@link Note} to cast on loading.
     * @return a cast of the Note object inheritor requested
     * @throws IOException            if the reading from filed failed
     * @throws ClassNotFoundException if the object mismatches the supplied type.
     */
    public <T extends Note> T loadNote(NoteInfo noteInfo) throws IOException, ClassNotFoundException {
        return loadNote(noteInfo.getUuid());
    }

    /**
     * This method will load a note from the storage.
     *
     * @param uuid a UUID of the note to load.
     * @param <T>  is an extension of {@link Note} to cast on loading.
     * @return a cast of the Note object inheritor requested
     * @throws IOException            if the reading from filed failed
     * @throws ClassNotFoundException if the object mismatches the supplied type.
     */
    public <T extends Note> T loadNote(String uuid) throws IOException, ClassNotFoundException {
        try (FileInputStream inFile = context.openFileInput(uuid)) {
            try (ObjectInputStream inStream = new ObjectInputStream(inFile)) {
                return (T) inStream.readObject();
            }
        }
    }

    public List<NoteInfo> getNoteInfoList(){
        return new ArrayList<>(noteInfoMap.values());
    }

}
