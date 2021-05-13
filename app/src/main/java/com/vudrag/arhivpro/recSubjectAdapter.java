package com.vudrag.arhivpro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vudrag.arhivpro.ulan.Vocabulary;

import java.util.List;

public class recSubjectAdapter extends RecyclerView.Adapter<recSubjectAdapter.ViewHolder> {

    List<Vocabulary.Subject> subjects;
    private OnSelectListener onSelectListener;
    private int list;

    public recSubjectAdapter(List<Vocabulary.Subject> subjects, OnSelectListener onSelectListener, int list) {
        this.subjects = subjects;
        this.onSelectListener = onSelectListener;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView id, term, parent, biography;
        OnSelectListener onSelectListener;

        public ViewHolder(@NonNull View itemView, OnSelectListener onSelectListener) {
            super(itemView);
            id = itemView.findViewById(R.id.subject_id);
            term = itemView.findViewById(R.id.preferred_term);
            parent = itemView.findViewById(R.id.preferred_parent);
            biography = itemView.findViewById(R.id.preferred_biography);
            this.onSelectListener = onSelectListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onSelectListener.onSelect(getAdapterPosition(), list);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_row, parent, false);
        return new ViewHolder(v, onSelectListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText(subjects.get(position).getSubjectID());
        holder.term.setText(subjects.get(position).getPreferredTerm().getTextValue());
        holder.parent.setText(subjects.get(position).getPreferredParent());
        holder.biography.setText(subjects.get(position).getPreferredBiography());

    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public interface OnSelectListener{
        void onSelect(int position, int list);
    }
}
