package com.camel.lists.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.camel.lists.Model.Item;

import java.util.LinkedList;
import java.util.List;


public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1; //the current version of our database
    private static final String DATABASE_NAME = "Lists"; //the name of our database
    private char escapeChar = '?'; //the char we add to our database to avoid crashes

    public Database(Context context)  {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    //SQL statements to create our tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        //the following tables are for the library
        String CREATE_TABLE_MOVIES = "CREATE TABLE movies ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, creator TEXT, genre TEXT)";
        String CREATE_TABLE_GAMES = "CREATE TABLE games ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, creator TEXT, genre TEXT)";
        String CREATE_TABLE_BOOKS = "CREATE TABLE books ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, creator TEXT, genre TEXT)";
        //execute sql to create out tables
        db.execSQL(CREATE_TABLE_BOOKS);
        db.execSQL(CREATE_TABLE_GAMES);
        db.execSQL(CREATE_TABLE_MOVIES);

    }

    //not used but can't remove
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS lists");
        this.onCreate(db);
    }

    //add an item to our database
    public void createItem(Item item, String table) {
        SQLiteDatabase db = this.getWritableDatabase(); //reference the database
        ContentValues values = new ContentValues();
        String name = item.getName().replace('\'', escapeChar);
        String creator = item.getCreator().replace('\'', escapeChar);
        String genre = item.getGenre().replace('\'', escapeChar);
        values.put("name", name);
        values.put("creator", creator);
        values.put("genre", genre);
        db.insert(table, null, values);
        db.close();
    }

    // Checks if an item exists before we add it to the database more than once
    public boolean doesExist(String TableName, String fieldValue) {
        SQLiteDatabase db = this.getReadableDatabase();
        String toSearch = fieldValue.replace('\'', escapeChar);
        Cursor cursor = db.rawQuery("SELECT * FROM " + TableName + " WHERE NAME = '" + toSearch + "'", null);

        if (cursor.getCount() > 0) {
            db.close();
            return true;
        } else {
            db.close();
            return false;
        }
    }


    //get an item by id
    public Item getItemByID(int id, String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " + table + " where id="+id+"", null );

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Item item = new Item();
        item.setId(Integer.parseInt(cursor.getString(0)));
        item.setName(cursor.getString(1));
        item.setCreator(cursor.getString(2));
        item.setGenre(cursor.getString(3));
        item.setName(item.getName().replace(escapeChar, '\''));
        item.setCreator(item.getCreator().replace(escapeChar, '\''));
        item.setGenre(item.getGenre().replace(escapeChar, '\''));
        return item;
    }

    /*return the results of a search
     * table - the table to search in
     * toSearch - what we are searching for
     * location - where to look (name, creator, genre)
     */
    public List<Item>searchResult(String table, String toSearch, String location) {
        List<Item> itemList = new LinkedList<>(); //the list where we will store everything that matches our search criteria
        toSearch = toSearch.replace('\'', escapeChar); //fixes a crash with the ' char
        String query = "SELECT * FROM " + table + " WHERE " + location + " LIKE '%" + toSearch + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        Item item = null;
        if (cursor.moveToFirst()) {
            do {
                item = new Item();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
                item.setCreator(cursor.getString(2));
                item.setGenre(cursor.getString(3));
                // Add item
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        return itemList;
    }

    //get all of an item from its table
    public List<Item> getAll(String table) {
        List<Item> itemList = new LinkedList<Item>();
        // select book query
        String query = "SELECT  * FROM " + table;
        // get reference of the BookDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        Item item = null;
        if (cursor.moveToFirst()) {
            do {
                item = new Item();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
                item.setCreator(cursor.getString(2));
                item.setGenre(cursor.getString(3));

                item.setName(item.getName().replace(escapeChar, '\''));
                item.setCreator(item.getCreator().replace(escapeChar, '\''));
                item.setGenre(item.getGenre().replace(escapeChar, '\''));

                // Add item
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        return itemList;
    }

    public int updateItem(Item item, String table) {

        // get reference of the BookDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        String name = item.getName().replace('\'',escapeChar);
        String creator = item.getCreator().replace('\'', escapeChar);
        values.put("name", name); // get title
        values.put("creator", creator); // get creator

        // update
        int i = db.update(table, values, "id" + " = ?", new String[] { String.valueOf(item.getId()) });

        db.close();
        return i;
    }

    // Deleting single book
    public void deleteItem(Item item, String table) {

        // get reference of the BookDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete book
        db.delete(table, "id" + " = ?", new String[] { String.valueOf(item.getId()) });
        db.close();
    }


}