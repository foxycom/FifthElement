package fifthelement.theelement.business;

import java.util.ArrayList;

import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.PlaylistPersistence;

public class AccessPlaylist implements PlaylistPersistence {

    public boolean updatePlaylist(Playlist currentPlaylist, String newName) {
        currentPlaylist.setName(newName);
        return true;
    }

    public boolean insertSong(Playlist currentPlaylist, Song song) {
        currentPlaylist.addSong(song);
        return true;
    }

    public Song getRandomSong(Playlist currentPlaylist) {
        return null;
    }

    public boolean removeSong(Playlist currentPlaylist, int songId) {
        return false;
    }

    public void deletePlaylist(Playlist currentPlaylist) { }

    @Override
    public ArrayList<Playlist> getAllPlaylists() {
        return null;
    }

    @Override
    public ArrayList<Song> getAllSongs() {
        return null;
    }
}
