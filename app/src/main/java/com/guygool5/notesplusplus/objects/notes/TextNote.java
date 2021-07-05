package com.guygool5.notesplusplus.objects.notes;

//This is a TextNote class. It inherits from Note and has an additional text field.
public class TextNote extends Note {

    //This will be a text field of the Text Note.
    private String text;

    //constructors are pretty self explanatory.
    public TextNote() {
        super();
    }

    public TextNote(String title, String text) {
        this(title);
        setText(text);
    }

    public TextNote(String title) {
        super(title);
    }

    @Override
    public NoteType getType() {
        return NoteType.TEXT;
    }

    public String getText() {
        return text;
    }

    //The setText method will also call update Modified to notify on changes.
    public void setText(String text) {
        this.text = text;
        updateModified();
    }

    @Override
    public String toString() {
        return getTitle() + "\n\n"
                + getText() + "\n\n"
                +"Created at: "+ getCreatedString()+"\n"
                +"Last modified at: "+getModifiedString()+"\n"
                +"---------------------\n"
                +"Created in Notes++\n"+
                "by Guy Tsitsiashvili.";
    }
}
