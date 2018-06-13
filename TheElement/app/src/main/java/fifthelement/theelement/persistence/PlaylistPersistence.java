package fifthelement.theelement.persistence;

import java.util.ArrayList;

import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.objects.Song;

public interface PlaylistPersistence {
    //get random song
    Song getRandomSong(Playlist currentPlaylist);

    //insert song
    boolean insertSong(Playlist currentPlaylist, Song song);

    //remove song
    boolean removeSong(Playlist currentPlaylist, int songId);

    ArrayList<Playlist> getAllPlaylists();

    ArrayList<Song> getAllSongs();

    //delete playlist
    void deletePlaylist(Playlist currentPlaylist);
}
