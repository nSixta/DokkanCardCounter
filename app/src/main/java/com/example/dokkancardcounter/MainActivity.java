package com.example.dokkancardcounter;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static MyDatabase myDatabase;

    Button addAUnitButton;

    RecyclerView recyclerView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize room database
        myDatabase = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "infodb").allowMainThreadQueries().build();

        addAUnitButton = findViewById(R.id.addAUnitButton);
        addAUnitButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddUnitActivity.class)));

        //Attach recyclerview
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }

    //Get the data from the room database and display each row in the recyclerview
    private void getData() {
        class GetData extends AsyncTask<Void, Void, List<MyDataList>> {

            @Override
            protected List<MyDataList> doInBackground(Void... voids) {
                List<MyDataList> myDataLists = MainActivity.myDatabase.getItemInterface().getItems();
                return myDataLists;
            }

            @Override
            protected void onPostExecute(List<MyDataList> myDataList) {
                adapter = new MyAdapter(MainActivity.this, myDataList);
                recyclerView.setAdapter(adapter);
                super.onPostExecute(myDataList);
            }
        }
        GetData getData = new GetData();
        getData.execute();
    }
}