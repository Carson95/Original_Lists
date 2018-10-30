package com.camel.lists;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.camel.lists.Database.Database;
import com.camel.lists.Model.Item;
import com.camel.lists.Model.TableAssistant;

public class AddItem extends AppCompatActivity {

    String table;
    EditText txtName;
    EditText txtCreator;
    EditText txtGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        txtName = findViewById(R.id.txtItemName);
        txtCreator = findViewById(R.id.txtItemCreator);
        txtGenre = findViewById(R.id.txtItemGenre);

    }

    //needs validation
    public void clickAdd(View view) {
        ImageView image = (ImageView) view;
        String table = TableAssistant.getTableName(image);
        Database db = new Database(getApplicationContext());
        Item item = new Item();
        item.setName(txtName.getText().toString());
        item.setGenre(txtGenre.getText().toString());
        item.setCreator(txtCreator.getText().toString());
        if (db.doesExist(table, txtName.getText().toString())) {
            Toast.makeText(this, "Item already exists!", Toast.LENGTH_SHORT).show();
        } else {
            db.createItem(item, table);
            Toast.makeText(this, "Item added!", Toast.LENGTH_SHORT).show();
            txtName.setText("");
            txtCreator.setText("");
            txtGenre.setText("");
            txtName.requestFocus();
        }
    }


    //when the user clicks to go back
    public void clickBack(View view) {
        Intent intent = new Intent(AddItem.this, MainMenu.class);
        startActivity(intent);
    }

}
