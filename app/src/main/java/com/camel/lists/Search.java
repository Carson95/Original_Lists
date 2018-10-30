package com.camel.lists;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Search extends AppCompatActivity {

    EditText toSearchText;
    String table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toSearchText = findViewById(R.id.txtSearchCriteria);
        Intent intent = getIntent();
        table = intent.getStringExtra("tableName");
    }

    //when back is clicked
    public void clickBack(View view) {
        Intent intent = new Intent(Search.this, MainMenu.class);
        startActivity(intent);
    }

    //when a search button is clicked
    public void clickSearchButton(View view) {
        ImageButton clicked = (ImageButton) view;
        String searchLocation = "name";
        int tag = Integer.parseInt(clicked.getTag().toString());
        switch (tag) {
            case 0:
                searchLocation = "name";
            break;
            case 1:
                searchLocation = "creator";
            break;
            case 2:
                searchLocation = "genre";
            break;
        }
        Intent intent = new Intent(Search.this, ViewList.class);
        intent.putExtra("search", true);
        intent.putExtra("tableName", table);
        intent.putExtra("location", searchLocation);
        intent.putExtra("toSearch", toSearchText.getText().toString());
        startActivity(intent);
    }
}
