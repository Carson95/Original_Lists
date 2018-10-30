package com.camel.lists;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.camel.lists.Database.Database;
import com.camel.lists.Model.Item;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewList extends AppCompatActivity {

    String tableName;
    List<Item> itemList; //the list of items we will pull from our database
    ArrayAdapter<String> adapter; //arrayAdapter object for our list
    private boolean search = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        Intent intent = getIntent();
        tableName = intent.getStringExtra("tableName"); //get which table to view here
        search = intent.getBooleanExtra("search", false);

        //get intents for searching will go here
        String toSearch = intent.getStringExtra("toSearch"); //what we are searching for
        String location = intent.getStringExtra("location"); //where we are searching for it

        ListView list = findViewById(R.id.listViewItems); //our list view we will be adding items too

        Database db = new Database(getApplicationContext());

        //if not searching get everything
        if (!search) {
            itemList = db.getAll(tableName);
        } else {
            itemList = db.searchResult(tableName, toSearch, location);
        }

        String[] items = new String[itemList.size()]; //a string array with all of the strings in our list for sorting

        //sort our list to they go into the array in alphabetical order
        Collections.sort(itemList, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        //move all the items from the list into our String array, this way we can put them in the list
        for(int i = 0; i < itemList.size(); i++){
            Item item = itemList.get(i);
            items[i] = item.getName();
        }
        //our on click listener for all the objects in our list
        list.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemID = itemList.get(position).getId();
                Intent intent = new Intent(ViewList.this, EditItem.class);
                intent.putExtra("tableName", tableName);
                intent.putExtra("id", itemID);
                startActivity(intent);
                //finish();
                //int itemID = list.get(position).getId();
                //Toast.makeText(getApplicationContext(), "creator: " + itemID, Toast.LENGTH_SHORT).show();
            }
        });


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        list.setAdapter(adapter);


        //Toast.makeText(this, tableName, Toast.LENGTH_SHORT).show(); //debug statement
    }
}
