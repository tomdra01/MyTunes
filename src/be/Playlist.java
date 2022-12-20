package be;

import javafx.beans.property.SimpleStringProperty;

public class Playlist  {
    private int id;
    private SimpleStringProperty name = new SimpleStringProperty();

    /**
     * Constructor for Playlist
     */
    public Playlist(int id, String name) {
        this.name.set(name);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * toString method
     */
    @Override
    public String toString() {
        return "Playlist{" +
                " id ='" + id + '\'' +
                ", name ='" + name + '\'' + '}';

    }
}