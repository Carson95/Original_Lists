package com.camel.lists;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.camel.lists.Model.TableAssistant;

public class MainMenu extends AppCompatActivity {

    private boolean searchMode = false; //whether or not the user is in search mode


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    //When the add button is clicked
    public void clickToAdd(View view) {
        Intent intent = new Intent(MainMenu.this, AddItem.class);
        startActivity(intent);
    }

    //When books, movies, or games is clicked
    public void clickToView(View view) {
        ImageView image = (ImageView) view;
        String table = TableAssistant.getTableName(image);
        if (searchMode) { //if we are searching
                Intent intent = new Intent(MainMenu.this, Search.class);
                intent.putExtra("tableName", table);
                startActivity(intent);
        } else { //if not we go to the add interface
                Intent intent = new Intent(MainMenu.this, ViewList.class);
                intent.putExtra("tableName", table);
                intent.putExtra("search", false);
                startActivity(intent);
        }
    }

    //When search is clicked
    public void clickSearch(View view) {
        ImageButton searchOn = findViewById(R.id.btnSearchOn);
        ImageButton searchOff = findViewById(R.id.btnSearchOff);
        if (searchMode) {
            //title.setText("Search: off");
            searchOff.setVisibility(View.VISIBLE);
            searchOn.setVisibility(View.INVISIBLE);
        } else {
            //title.setText("Search: on");
            searchOff.setVisibility(View.INVISIBLE);
            searchOn.setVisibility(View.VISIBLE);
        }
        searchMode = !searchMode; //switch the search mode
    }

    //When about is clicked
    public void clickAbout(View view) {
        Intent intent = new Intent(MainMenu.this, About.class);
        startActivity(intent);
    }

}
