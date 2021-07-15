package com.guygool5.notesplusplus.dialogs;

import android.content.Context;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.guygool5.notesplusplus.R;
import com.guygool5.notesplusplus.databinding.DialogNoteEditTitleBinding;

import java.util.Objects;

/**
 * This class will wrap a {@link MaterialAlertDialogBuilder} with a custom dialog layout with a text field
 * and save button. This class' methods return a reference to itself to chain commands.</br>
 * It has custom methods such as:<br/>
 * {@link TextFieldDialog#setTitle} to set the dialog's title.<br/>
 * {@link TextFieldDialog#setOnCloseListener} to listen to when the dialog closes.<br/>
 * {@link TextFieldDialog#setOnSaveListener} to listen to when the save button is being pressed.
 */
public class TextFieldDialog {

    /**
     * This interface is to implement an {@link OnSaveListener#onSave(String)} that passes as string the dialog's text field content.
     */
    @FunctionalInterface
    public interface OnSaveListener {
        void onSave(String text);
    }

    /**
     * This interface is to implement an {@link OnCloseListener#onClose()} that is invoked when the dialog is dismissed
     */
    @FunctionalInterface
    public interface OnCloseListener {
        void onClose();
    }


    private final MaterialAlertDialogBuilder textFieldDialogBuilder;
    private OnSaveListener onSaveListener = null;
    private OnCloseListener onCloseListener = null;

    /**
     * Creates a TextFieldDialog that wraps {@link MaterialAlertDialogBuilder} with
     * a custom text input layout and close/save listeners.
     *
     * @param context The context of the calling activity.
     */
    public TextFieldDialog(Context context) {

        textFieldDialogBuilder = new MaterialAlertDialogBuilder(context);
        textFieldDialogBuilder.setNeutralButton(R.string.button_cancel, (d, i) -> {
            d.cancel();
            if (onCloseListener != null) onCloseListener.onClose();
        });
    }

    /**
     * This method will set the title of the dialog to the given input
     * @param title The new title of the dialog
     * @return A self reference to the instance of this class.
     */
    public TextFieldDialog setTitle(@Nullable CharSequence title) {
        textFieldDialogBuilder.setTitle(title);
        return this;
    }

    /**
     * This method will set the title of the dialog to the given input
     * @param titleRes A string resource to set the title to.
     * @return A self reference to the instance of this class.
     */
    public TextFieldDialog setTitle(@StringRes int titleRes) {
        textFieldDialogBuilder.setTitle(titleRes);
        return this;
    }

    /**
     * This method will open the dialog and fill the title with a title
     * @param currentText This will fill the text field with a given text (Optional).
     */
    public void open(@Nullable String currentText) {
        View dialogView = LayoutInflater.from(textFieldDialogBuilder.getContext()).inflate(R.layout.dialog_note_edit_title, null);
        //Then we will use data binding on the inflated view.
        DialogNoteEditTitleBinding dialogNoteEditTitleBinding = DataBindingUtil.bind(dialogView);

        //Then we'll set the text to that of the TextNoteAdapter.
        if (dialogNoteEditTitleBinding != null) {
            dialogNoteEditTitleBinding.dialogNoteEditTitleEditTextId.setText(currentText);
        }

        //We'll update the view of the MaterialAlertDialogBuilder to the inflated view.
        textFieldDialogBuilder.setView(dialogView);

        //Finally we'll set an onClickListener of OK to update the title of the TextNoteAdapter and dismiss the dialog.
        textFieldDialogBuilder.setPositiveButton(R.string.button_ok, (dialogInterface, whichButton) -> {
            Editable text = Objects.requireNonNull(dialogNoteEditTitleBinding).dialogNoteEditTitleEditTextId.getText();
            String title = text != null ? text.toString() : null;
            if (onSaveListener != null) onSaveListener.onSave(title);
            dialogInterface.cancel();
            if (onCloseListener != null) onCloseListener.onClose();
        });

        //After the preparations we'll show the dialog.
        textFieldDialogBuilder.show();
    }


    /**
     * This method will set an event listener that passes a {@link String} of the text field on save.
     *
     * @param onSaveListener The event listener to invoke on save.
     * @return A self reference to the instance of this class.
     */
    public TextFieldDialog setOnSaveListener(@Nullable OnSaveListener onSaveListener) {
        this.onSaveListener = onSaveListener;
        return this;
    }

    /**
     * This method will set an event listener that is invoked on close.
     *
     * @param onCloseListener The event listener to invoke on close.
     * @return A self reference to the instance of this class.
     */
    public TextFieldDialog  setOnCloseListener(@Nullable OnCloseListener onCloseListener) {
        this.onCloseListener = onCloseListener;
        return this;
    }

}
