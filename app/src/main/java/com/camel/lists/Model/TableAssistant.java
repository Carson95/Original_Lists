package com.camel.lists.Model;


import android.widget.ImageView;

public class TableAssistant {

    //get which table we are attempting to access
    public static String getTableName(ImageView image) {
        int tag = Integer.parseInt(image.getTag().toString()); //get which button was click (0 = movies 1 = games 2 = books)
        String table = "";
        switch (tag) {
            case 0:
                table = "movies";
                break;
            case 1:
                table = "games";
                break;
            case 2:
                table = "books";
                break;
        }
        return table;
    }
}
