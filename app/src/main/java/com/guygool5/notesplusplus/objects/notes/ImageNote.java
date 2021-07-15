package com.guygool5.notesplusplus.objects.notes;

import android.graphics.Bitmap;

//This is an ImageNote class. It inherits from Note and has an additional Image field.
public class ImageNote extends Note {


    //This will be the image of the note.
    private Bitmap bitmap;

    //Basic constructors.
    public ImageNote(){super();}

    public ImageNote(String title){ super(title);}

    public ImageNote(String title, Bitmap imageUri){
        this(title);
        setBitmap(imageUri);
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        updateModified();
    }


    @Override
    public NoteType getType() {
        return NoteType.IMAGE;
    }

    //TODO: try to make sense of this.
    @Override
    public String toString() {
        return "ImageNote{" +
                "image=" + bitmap.toString() +
                "} " + super.toString();
    }
}
