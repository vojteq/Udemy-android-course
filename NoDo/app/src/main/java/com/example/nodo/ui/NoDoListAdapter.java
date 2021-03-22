package com.example.nodo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nodo.R;
import com.example.nodo.model.NoDo;

import java.util.List;


public class NoDoListAdapter extends RecyclerView.Adapter<NoDoListAdapter.NoDoViewHolder> {
    private final LayoutInflater noDoInflater;
    private Context context;
    private List<NoDo> noDoList;

    public NoDoListAdapter(Context context) {
        this.context = context;
        noDoInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NoDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = noDoInflater.inflate(R.layout.recycler_view_item, parent, false);
        return new NoDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoDoViewHolder holder, int position) {
        if (noDoList != null) {
            NoDo current = noDoList.get(position);
            holder.noDoTextView.setText(current.getNoDo());
        }
        else
            holder.noDoTextView.setText(R.string.no_notodo);
    }

    public void setNoDos(List<NoDo> noDos) {
        noDoList = noDos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return noDoList == null ? 0 : noDoList.size();
    }

    public class NoDoViewHolder extends RecyclerView.ViewHolder {
        public TextView noDoTextView;

        public NoDoViewHolder(@NonNull View itemView) {
            super(itemView);
            noDoTextView = itemView.findViewById(R.id.text_view);
        }
    }
}
