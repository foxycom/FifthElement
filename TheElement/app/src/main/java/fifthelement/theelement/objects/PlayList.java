package fifthelement.theelement.objects;

import java.util.ArrayList;

public class Playlist {
    private int listId;
    private String listName;
    private ArrayList<Song> songs;

    public Playlist(int id, String name) {
        this.listId = id;
        this.listName = name;
        this.songs = new ArrayList<Song>();
    }

    public String getName() {
        return listName;
    }

    public int getId() {
        return listId;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setName(String newName) {
        this.listName = newName;
    }

    public void setId(int newId) {
        this.listId = newId;
    }

    public void setSongs(ArrayList<Song> songList) {
        this.songs = songList;
    }
    //OR

    // Adding single songs
    public void addSong(Song newSong) {
        this.songs.add(newSong);
    }
}