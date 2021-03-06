package com.example.dokkancardcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AddUnitActivity extends AppCompatActivity {

    private List<UnitItem> unitItemList;
    String[] unitNames;
    String[] unitPictures;
    String[] unitID;

    AutoCompleteTextView searchForUnit;
    AutoCompleteUnitAdapter autoCompleteUnitAdapter;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_unit);

        //Create String arrays from strings.xml
        unitID = getResources().getStringArray(R.array.cardID);
        unitNames = getResources().getStringArray(R.array.units);
        unitPictures = getResources().getStringArray(R.array.pictures);

        fillUnitList();

        //Link autoCompleteTextView and set adapter for the autoCompleteTextView
        searchForUnit = findViewById(R.id.searchForUnitText);
        autoCompleteUnitAdapter = new AutoCompleteUnitAdapter(this, unitItemList);
        searchForUnit.setAdapter(autoCompleteUnitAdapter);


        addButton = findViewById(R.id.addNewUnitButton);
        addButton.setOnClickListener(v -> insertUnit());
    }

    //Fill the list of for the autoCompleteTextView to show when searching for a unit
    public void fillUnitList() {
        unitItemList = new ArrayList<>();
        for (int i = 0; i < unitNames.length; i++) {
            unitItemList.add(new UnitItem(unitID[i], unitNames[i], unitPictures[i]));
        }
    }

    //Check the conditions for the unit searched and add the unit to a Room Database
    public void insertUnit() {
        String id = "";
        String name = searchForUnit.getText().toString();
        String picture = "";
        boolean goodName = false;

        if (name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Name Cannot be Empty", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < unitNames.length; i++) {
                if (name.equals(unitNames[i])) {
                    id = unitID[i];
                    picture = unitPictures[i];
                    goodName = true;
                }
            }
        }

        //The name entered was a valid name
        if (goodName) {
            if(!MainActivity.myDatabase.getItemInterface().exists(id)){
                MyDataList myDataList = new MyDataList();
                myDataList.setCardID(id);
                myDataList.setName(name);
                myDataList.setPicture(picture);
                myDataList.setCopies(0);
                MainActivity.myDatabase.getItemInterface().insert(myDataList);
                Toast.makeText(getApplicationContext(), "Unit was Added", Toast.LENGTH_LONG).show();
                searchForUnit.setText("");
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }else{
                Toast.makeText(getApplicationContext(), "You already added this unit", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "No Unit Has This Name", Toast.LENGTH_LONG).show();
        }
    }
}