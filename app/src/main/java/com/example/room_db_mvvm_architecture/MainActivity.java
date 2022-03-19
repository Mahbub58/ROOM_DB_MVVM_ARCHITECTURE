package com.example.room_db_mvvm_architecture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fab=findViewById(R.id.ftb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddNoteActivity.class);
                startActivityForResult(intent,1);
            }
        });




        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        NoteAdaptor adaptor=new NoteAdaptor();
        recyclerView.setAdapter(adaptor);

        //repository class
        noteViewModel=  ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //update recycler View Data
                adaptor.setNotes(notes);
            }
        });


        //swip to remove item
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
               noteViewModel.delete(adaptor.getNoteAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);


        // adaptor item click

        adaptor.setOnItemClickListner(new NoteAdaptor.onItemClickListner() {
            @Override
            public void onItemClickListner(Note note) {
                Intent intent=new Intent(MainActivity.this,AddNoteActivity.class);
                intent.putExtra(AddNoteActivity.EXTRA_TITLE,note.getTitle());
                intent.putExtra(AddNoteActivity.EXTRA_DESCRIPTION,note.getDescription());
                intent.putExtra(AddNoteActivity.EXTRA_PRIORITY,note.getPriority());
                intent.putExtra(AddNoteActivity.EXTRA_PRIMARY_KEY,note.getId());
                startActivityForResult(intent,2);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK){
            String Title=data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String Desc=data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            int prio=data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY,1);

            Note note=new Note(Title,Desc,prio);
            noteViewModel.insert(note);
        }else  if(requestCode==2 && resultCode==RESULT_OK){
            int id=data.getIntExtra(AddNoteActivity.EXTRA_PRIMARY_KEY,-1);
            if(id==1){
                //not update invalid id
            }

            String Title=data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String Desc=data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            int prio=data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY,1);
            Note note=new Note(Title,Desc,prio);
            note.setId(id);
            noteViewModel.update(note);
        }
        else{
            //cancel
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.delete_all:
                noteViewModel.deleteAllNotes();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }
}