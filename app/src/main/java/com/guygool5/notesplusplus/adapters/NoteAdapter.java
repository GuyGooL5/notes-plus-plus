package com.guygool5.notesplusplus.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.guygool5.notesplusplus.R;
import com.guygool5.notesplusplus.activities.ImageNoteActivity;
import com.guygool5.notesplusplus.activities.TextNoteActivity;
import com.guygool5.notesplusplus.databinding.NoteInfoItemBinding;
import com.guygool5.notesplusplus.objects.notes.NoteInfo;
import com.guygool5.notesplusplus.objects.notes.TextNote;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<NoteInfo> noteInfo;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        NoteInfoItemBinding binding;

        public ViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
            view.setOnClickListener(v -> {
                Intent intent;
                NoteInfo noteInfo = binding.getModel();
                if (noteInfo == null) return;
                switch (noteInfo.getNoteType()) {
                    case TEXT:
                        intent = new Intent(v.getContext(), TextNoteActivity.class);
                        break;
                    case IMAGE:
                        intent = new Intent(v.getContext(), ImageNoteActivity.class);
                        break;
                    default:
                        return;
                }
                intent.putExtra("UUID",noteInfo.getUuid());
                v.getContext().startActivity(intent);
            });
        }
    }

    public NoteAdapter(List<NoteInfo> noteInfo) {
        this.noteInfo = noteInfo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_info_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setModel(noteInfo.get(position));
    }

    @Override
    public int getItemCount() {
        return noteInfo.size();
    }


}
