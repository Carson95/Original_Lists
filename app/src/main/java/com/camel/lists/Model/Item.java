package com.camel.lists.Model;


public class Item {

    private int id; //the id of the item in our database
    private String name; //the name of the item
    private String genre; //the genre
    private String creator; //the author/director/studio that created this item

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }





}

