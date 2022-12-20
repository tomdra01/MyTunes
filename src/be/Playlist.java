package be;

public class Playlist  {
    private int id;
    private String name;

    /**
     * Constructor for Playlist
     */
    public Playlist(int id, String name) {
        this.name = name;
        this.id = id;
    }

    /**
     * Getter for Id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for Name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for Name
     */
    public void setName(String name) {
        this.name = name;
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