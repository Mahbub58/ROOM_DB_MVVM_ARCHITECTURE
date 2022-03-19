package com.example.room_db_mvvm_architecture;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdaptor extends RecyclerView.Adapter<NoteAdaptor.NoteViewHolder> {

    List<Note>notes=new ArrayList<>() ;
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.title.setText(notes.get(position).getTitle());
        holder.description.setText(notes.get(position).getDescription());
        holder.priority.setText(String.valueOf(notes.get(position).getPriority()));
    }

    public void setNotes(List<Note>notes){
        this.notes=notes;
        notifyDataSetChanged();
    }
    public Note getNoteAt(int position){
        return notes.get(position);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView title,description,priority;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.titel);
            description=itemView.findViewById(R.id.description);
            priority=itemView.findViewById(R.id.textViewPrioety);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   int position=getAdapterPosition();
                   if(listner!=null&& position!= RecyclerView.NO_POSITION)
                    listner.onItemClickListner(notes.get(position));

                }
            });
        }
    }

    public interface onItemClickListner{
        void onItemClickListner(Note note);
    }
    private onItemClickListner listner;
    public void setOnItemClickListner(onItemClickListner listner){
        this.listner=listner;
    }
}
