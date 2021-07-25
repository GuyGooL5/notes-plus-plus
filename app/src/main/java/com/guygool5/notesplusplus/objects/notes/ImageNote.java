package com.guygool5.notesplusplus.objects.notes;

import android.graphics.Bitmap;

import com.guygool5.notesplusplus.utilities.BitmapDataObject;

//This is an ImageNote class. It inherits from Note and has an additional Image field.
public class ImageNote extends Note {


    //This will be the image of the note.
    private BitmapDataObject bitmap;

    //Basic constructors.
    public ImageNote(){super();}

    public ImageNote(String title){ super(title);}

    public ImageNote(String title, BitmapDataObject bitmap){
        this(title);
        setBitmap(bitmap);
    }


    public BitmapDataObject getBitmap() {
        return bitmap;
    }

    public void setBitmap(BitmapDataObject bitmap) {
        this.bitmap = bitmap;
        updateModified();
    }


    @Override
    public NoteType getType() {
        return NoteType.IMAGE;
    }

}
