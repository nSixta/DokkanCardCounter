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

    public static MyDatabase myDatabase;
    private List<UnitItem> unitItemList;
    String[] unitNames;
    String[] unitPictures;
//    private static final String[] unitNames = {"Majin Buu (Gotenks)",
//            "Goku & Vegeta",
//            "Cell (Perfect Form)",
//            "Goku and Vegeta (Angel)",
//            "Vegito",
//            "Gogeta",
//            "Super Saiyan Gohan (Youth)",
//            "Super Saiyan 4 Goku",
//            "Super Saiyan 4 Vegeta",
//            "Gohan (Teen) & Goten (Kid)",
//            "Goku (Angel) & Vegeta (Angel)",
//            "SSGSS Goku & SSGSS Vegeta"};


//    private static final String[] unitPictures = {"https://static.wikia.nocookie.net/dbz-dokkanbattle/images/9/9c/Card_1020350_thumb.png/revision/latest?cb=20200829043721"
//            , "https://static.wikia.nocookie.net/dbz-dokkanbattle/images/5/58/Card_1012880_thumb.png/revision/latest?cb=20180708215123"
//            , "https://static.wikia.nocookie.net/dbz-dokkanbattle/images/2/20/Card_1017880_thumb.png/revision/latest?cb=20190829035025"
//            , "https://static.wikia.nocookie.net/dbz-dokkanbattle/images/4/43/Card_1020440_thumb.png/revision/latest?cb=20200829043246"
//            , "https://static.wikia.nocookie.net/dbz-dokkanbattle/images/6/64/Card_1018630_thumb.png/revision/latest?cb=20200708043139"
//            , "https://static.wikia.nocookie.net/dbz-dokkanbattle/images/7/72/Card_1018600_thumb.png/revision/latest?cb=20200708043244"
//            , "https://static.wikia.nocookie.net/dbz-dokkanbattle/images/8/8b/Card_1017610_thumb.png/revision/latest?cb=20190829035023"
//            , "https://static.wikia.nocookie.net/dbz-dokkanbattle/images/0/0c/Card_1015740_thumb.png/revision/latest?cb=20190705085758"
//            , "https://static.wikia.nocookie.net/dbz-dokkanbattle/images/2/2f/Card_1015760_thumb.png/revision/latest?cb=20190705090214"
//            , "https://static.wikia.nocookie.net/dbz-dokkanbattle/images/d/d1/Card_1016790_thumb.png/revision/latest?cb=20190507104938"
//            , "https://static.wikia.nocookie.net/dbz-dokkanbattle/images/0/0f/Card_1012910_thumb.png/revision/latest?cb=20180708215348"
//            , "https://static.wikia.nocookie.net/dbz-dokkanbattle/images/9/99/Card_1019970_thumb.png/revision/latest?cb=20201203070514"};

    AutoCompleteTextView searchForUnit;
    AutoCompleteUnitAdapter autoCompleteUnitAdapter;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_unit);
        unitNames = getResources().getStringArray(R.array.units);
        unitPictures = getResources().getStringArray(R.array.pictures);

        fillUnitList();

        searchForUnit = findViewById(R.id.searchForUnitText);
        autoCompleteUnitAdapter = new AutoCompleteUnitAdapter(this, unitItemList);
        searchForUnit.setAdapter(autoCompleteUnitAdapter);
        addButton = findViewById(R.id.addNewUnitButton);
        addButton.setOnClickListener(v -> insertUnit());
    }

    public void fillUnitList() {
        unitItemList = new ArrayList<>();
        for (int i = 0; i < unitNames.length; i++) {
            unitItemList.add(new UnitItem(unitNames[i], unitPictures[i]));
        }
    }

    public void insertUnit() {
        String name = searchForUnit.getText().toString();
        String picture = "";
        boolean goodName = false;

        if (name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Name Cannot be Empty", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < unitNames.length; i++) {
                if (name.equals(unitNames[i])) {
                    picture = unitPictures[i];
                    goodName = true;
                }
            }
        }

        if (goodName) {
            MyDataList myDataList = new MyDataList();
            myDataList.setName(name);
            myDataList.setPicture(picture);
            myDataList.setCopies(0);
            MainActivity.myDatabase.getItemInterface().insert(myDataList);
            Toast.makeText(getApplicationContext(), "Unit was Added", Toast.LENGTH_LONG).show();
            searchForUnit.setText("");
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        else{
            Toast.makeText(getApplicationContext(), "No Unit Has This Name", Toast.LENGTH_LONG).show();
        }
    }
}