
package main.java.fifthelement.theelement.objects;

import main.java.fifthelement.theelement.persistence.stubs.AuthorStorage;
import main.java.fifthelement.theelement.persistence.stubs.SongStorage;
// TODO: implement proper stuff here

public class Album{
    private String albumId;
    private String albumName;
    private AuthorStorage authors;
    private SongStorage songs;

    public Album(String id, String name) {
        this.albumId = id;
        this.albumName = name;
        authors = new AuthorStorage();
        songs = new SongStorage();
    }

    public String getName(){
        return albumName;
    }

    public String getId(){
        return albumId;
    }

    public AuthorStorage getAuthors() {
        return authors;
    }

    public SongStorage getsongs() {
        return songs;
    }

    public void setName(String newName) {
        this.albumName = newName;
    }

    public void setId(String newId) {
        this.albumId = newId;
    }

    public void setAuthors(AuthorStorage authorList) {
        this.authors =  authorList;
    }
    //OR

    public void addAuthor(Song newAuthor){//to add a single author
        this.authors.store(newAuthor);
    }

    public void setSongs(SongStoragesongList) {
        this.songs = songList;
    }
    //OR

    public void addSong(Song newSong){
        this.songs.add(newSong);
    }


}