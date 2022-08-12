package com.example.counterapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactsViewAdapter extends RecyclerView.Adapter<ContactsViewAdapter.ViewHolder> {
    private ArrayList<SaveClass> saveClassO = new ArrayList<>();
    private OnNoteListener OnNoteListener;



    public ContactsViewAdapter(ArrayList<SaveClass> saveClass, OnNoteListener OnNoteListener) {
        this.saveClassO = saveClass;
        this.OnNoteListener=OnNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout, parent, false);
        ViewHolder holder = new ViewHolder(view,OnNoteListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.count.setText(saveClassO.get(position).getCountC());
        holder.countName.setText(saveClassO.get(position).getNameC());




    }

    @Override
    public int getItemCount() {
        return saveClassO.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView countName, count;
        ImageView delete;
        OnNoteListener OnNoteListener;



        public ViewHolder(@NonNull View itemView,OnNoteListener onNoteListener) {
            super(itemView);
            delete=itemView.findViewById(R.id.deleteBt);
            countName = itemView.findViewById(R.id.savedNameTxt);
            count = itemView.findViewById(R.id.savedCountTxt);
            delete.setOnClickListener(v -> {
                onNoteListener.deleteSavedCount(saveClassO.get(getAdapterPosition()));
                saveClassO.remove(getAdapterPosition());
                notifyDataSetChanged();

            });
            this.OnNoteListener=onNoteListener;
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            OnNoteListener.onNoteClick(saveClassO.get(getAdapterPosition()));
        }
    }
    public interface OnNoteListener{
        void onNoteClick(SaveClass saveClass);
        void deleteSavedCount(SaveClass saveClass);

    }

}
