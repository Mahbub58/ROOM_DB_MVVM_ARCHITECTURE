package com.example.room_db_mvvm_architecture;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>>allNote;

    public NoteRepository(Application application){
        NoteDatabase database=NoteDatabase.getInstance(application);
        noteDao=database.noteDao();
        allNote=noteDao.getAllNotes();
    }
    public void insert(Note note){
        new InsertNoteAsyncTAsk(noteDao).execute(note);
    }
    public void update(Note note){
        new UpdateNoteAsyncTAsk(noteDao).execute(note);
    }
    public void delete(Note note){
        new DeleteNoteAsyncTAsk(noteDao).execute(note);
    }
    public void deleteAllNotes(){
        new DeleteAllNoteAsyncTAsk(noteDao).execute();
    }
    public LiveData<List<Note>>getAllNote(){
        return allNote;
    }

    private static class InsertNoteAsyncTAsk extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        private InsertNoteAsyncTAsk(NoteDao noteDao){
            this.noteDao=noteDao;
        }


        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }

    }

    private static class UpdateNoteAsyncTAsk extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        private UpdateNoteAsyncTAsk(NoteDao noteDao){
            this.noteDao=noteDao;
        }


        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }

    }

    private static class DeleteNoteAsyncTAsk extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        private DeleteNoteAsyncTAsk(NoteDao noteDao){
            this.noteDao=noteDao;
        }


        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }

    }

    private static class DeleteAllNoteAsyncTAsk extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        private DeleteAllNoteAsyncTAsk(NoteDao noteDao){
            this.noteDao=noteDao;
        }


        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.deleteAllNotes();
            return null;
        }

    }
}
