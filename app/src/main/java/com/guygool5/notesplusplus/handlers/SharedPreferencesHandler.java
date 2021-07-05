package com.guygool5.notesplusplus.handlers;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import com.guygool5.notesplusplus.objects.notes.NoteType;
import com.guygool5.notesplusplus.utilities.logger.LogType;
import com.guygool5.notesplusplus.utilities.logger.Logger;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SharedPreferencesHandler extends ContextWrapper {

    private static final String UUID_TAG = "UUID";
    //TODO: Add all note types to map.
    private static final Map<NoteType, String> noteTypeTagMap = new HashMap<NoteType, String>() {{
        put(NoteType.TEXT, "TEXT_NOTES");
        put(NoteType.IMAGE, "IMAGE_NOTES");
    }};
    private SharedPreferences sharedPrefs;

    public SharedPreferencesHandler(Context base) {
        super(base);
    }

    private static String getTag(NoteType type) {
        return noteTypeTagMap.get(type);
    }

    /**
     * @param type - The note type to get it's UUIDs
     * @return a JSONArray object of all UUIDs of the given note type.
     */
    private JSONArray getJsonUuids(NoteType type) {
        String tag = getTag(type);
        sharedPrefs = getSharedPreferences(UUID_TAG, MODE_PRIVATE);
        JSONArray arr = null;
        try {
            arr = new JSONArray(sharedPrefs.getString(tag, "[]"));
        } catch (JSONException e) {
            Logger.log(LogType.EXCEPTION_JSON, "At getJsonUuids:", e.toString());
        }
        return arr;
    }

    /**
     * This method will save a given set of UUIDs to the shared preferences under the given note type.
     *
     * @param uuidSet - A string set of all uuids to save
     * @param type    - The note type to save to in shared preferences
     */
    private void saveUUIDSet(@NotNull Set<String> uuidSet, NoteType type) {
        JSONArray array = new JSONArray();
        for (String i : uuidSet) array.put(i);
        sharedPrefs = getSharedPreferences(UUID_TAG, MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putString(getTag(type), array.toString());
        edit.apply();
    }

    /**
     * This method will add the given UUID to the shared preferences under the given note type
     *
     * @param uuid - The UUID of the note to save
     * @param type - The type of the note
     */
    public void addNoteUUID(String uuid, NoteType type) {
        Set<String> uuidSet = getUUIDs(type);
        uuidSet.add(uuid);
        saveUUIDSet(uuidSet, type);
    }

    /**
     * This method will remove a given note's UUID from the shared preferences under the given type.
     *
     * @param uuid - The UUID of the note to remove
     * @param type - The type of the note
     */
    public void removeNoteUUID(String uuid, NoteType type) {
        Set<String> uuidSet = getUUIDs(type);
        uuidSet.remove(uuid);
        saveUUIDSet(uuidSet, type);
    }


    /**
     * @return A set of all UUIDs of any note type
     */
    public Set<String> getAllUUIDs() {
        Set<String> uuidSet = new HashSet<>();
        for (NoteType type : NoteType.values()) {
            Set<String> uuidSubset = getUUIDs(type);
            uuidSet.addAll(uuidSubset);
        }
        Logger.log(LogType.SHARED_PREF, "All uuids", uuidSet.toString());
        return uuidSet;
    }


    /**
     * @param type - The type of the note to get it's UUIDs
     * @return A set of all UUIDs of the given type.
     */
    public Set<String> getUUIDs(NoteType type) {
        JSONArray uuidJsonArray = getJsonUuids(type);
        Set<String> uuidSet = new HashSet<>();
        if (uuidJsonArray != null) {
            for (int i = 0; i < uuidJsonArray.length(); i++) {
                try {
                    uuidSet.add(uuidJsonArray.getString(i));
                } catch (JSONException e) {
                    Logger.log(LogType.EXCEPTION_JSON, "At getUUIDs:", e.toString());
                }
            }
        }
        return uuidSet;
    }
}
