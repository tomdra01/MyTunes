package be;

import javafx.fxml.FXML;

public class Playlist  {
    private int id;
    private String name;

    public Playlist(){
        getName();
    }
    public Playlist(String name) {
        this.name = name;
    }

    public Playlist(int id, String name) {
        this(name);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                " name ='" + name + '\'' + '}';

    }
}

