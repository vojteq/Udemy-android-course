package com.example.notebookapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notebookapp.R;
import com.example.notebookapp.model.Note;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private Context context;
    private List<Note> notes;

    public RecyclerViewAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.note_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Note note = notes.get(position);
        viewHolder.productNameTextView.setText(note.getProductName());
        viewHolder.quantityTextView.setText(String.valueOf(note.getQuantity()));
        viewHolder.dateTextView.setText(note.getStringDate());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView productNameTextView;
        public TextView quantityTextView;
        public TextView dateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.delete_image_view:
                    break;
                case R.id.edit_image_View:
                    break;

            }
        }
    }
}
