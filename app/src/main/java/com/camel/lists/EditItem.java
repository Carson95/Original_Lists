package com.camel.lists;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.camel.lists.Database.Database;
import com.camel.lists.Model.Item;

public class EditItem extends AppCompatActivity {

    private String table;
    private int id;
    Database db;
    Item item;
    EditText txtCreator;
    EditText txtGenre;
    EditText txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Intent intent = getIntent();
        table = intent.getStringExtra("tableName");
        id = intent.getIntExtra("id", 0);

        db = new Database(this);
        item = db.getItemByID(id, table);

        txtName = findViewById(R.id.txtItemName);
        txtGenre = findViewById(R.id.txtItemGenre);
        txtCreator = findViewById(R.id.txtItemCreator);

        txtName.setText(item.getName());
        txtGenre.setText(item.getGenre());
        txtCreator.setText(item.getCreator());


    }


    //when the user clicks to save
    public void clickSave(View view) {
        item.setGenre(txtGenre.getText().toString());
        item.setName(txtName.getText().toString());
        item.setCreator(txtCreator.getText().toString()); //set the item object to it's new values
        db.updateItem(item, table); //update the item in the database
        goBack(); //return to the list
    }

    //when the user clicks to delete
    public void clickDelete(View view) {
        db.deleteItem(item, table); //remove the item
        goBack(); //go back to the list
    }

    //takes user back to view list
    private void goBack() {
        Intent intent = new Intent(EditItem.this, MainMenu.class);
        startActivity(intent);
        finish();
    }


}
