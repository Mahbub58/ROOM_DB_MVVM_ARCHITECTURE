package com.example.room_db_mvvm_architecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;

public class AddNoteActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE=
            "com.exmple.rom.ExtarTitle";
    public static final String EXTRA_DESCRIPTION=
            "com.exmple.rom.Extardescription";
    public static final String EXTRA_PRIORITY=
            "com.exmple.rom.ExtarPriority";
    public static final String EXTRA_PRIMARY_KEY=
            "com.exmple.rom.ExtarPrimaryKe";

    EditText title,description;
    NumberPicker numberPicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title=findViewById(R.id.titel);
        description=findViewById(R.id.description);
        numberPicker=findViewById(R.id.number_picker_priority);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_cancel_24);

        Intent intent=getIntent();
        if(intent.hasExtra(EXTRA_PRIMARY_KEY)){
            setTitle("Update New Note");
            title.setText(intent.getStringExtra(EXTRA_TITLE));
            description.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
        }else{
            setTitle("ADD New Note");
        }


    }
  //  private static final int EXTRA_ID=2;
    private void saveNote() {
        String stitle=title.getText().toString();
        String sdescription=description.getText().toString();
        int spriority=numberPicker.getValue();

        if(stitle.trim().isEmpty()||sdescription.trim().isEmpty()){
            //emti fild
        }else{
            Intent data=new Intent();
            data.putExtra(EXTRA_TITLE,stitle);
            data.putExtra(EXTRA_DESCRIPTION,sdescription);
            data.putExtra(EXTRA_PRIORITY,spriority);

            int id=getIntent().getIntExtra(EXTRA_PRIMARY_KEY,-1);
            if(id!=-1){
                data.putExtra(EXTRA_PRIMARY_KEY,id);
            }

            setResult(RESULT_OK,data);
            finish();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}