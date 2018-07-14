package fifthelement.theelement.objects;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String listName;
    private List<Song> songList;

    public Playlist(String name) {
        this.listName = name;
        this.songList = new ArrayList<>();
    }

    public Playlist(String name, List<Song> songList) {
        this.listName = name;
        this.songList = songList;
    }

    // getters
    public String getName() {
        return listName;
    }

    public List<Song> getSongs() {
        return songList;
    }

    // setters
    public void setName(String newName) {
        this.listName = newName;
    }

    public void setSongs(List<Song> songList) {
        this.songList = songList;
    }

    public void addSong(Song newSong) {
        this.songList.add(newSong);
    }

    public void removeSong(Song song) {
        this.songList.remove(song);
    }

    public boolean contains(Song song) {
        return songList.contains(song);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Playlist && ((Playlist)object).getName().equals(this.getName());
    }
}