package fifthelement.theelement.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.application.Persistence;
import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.PlaylistPersistence;
import fifthelement.theelement.persistence.SongPersistence;
import fifthelement.theelement.persistence.hsqldb.PersistenceException;


public class PlaylistService {

    private PlaylistPersistence playlistPersistence;
    private SongPersistence songPersistence;

    public PlaylistService() {
        playlistPersistence = Persistence.getPlaylistPersistence();
        songPersistence = Persistence.getSongPersistence();
    }

    public PlaylistService(PlaylistPersistence playlistPersistence, SongPersistence songPersistence) {
        this.playlistPersistence = playlistPersistence;
        this.songPersistence = songPersistence;
    }

    public Playlist getPlaylistByUUID(UUID uuid) {
        return playlistPersistence.getPlaylistByUUID(uuid);
    }

    public List<Playlist> getAllPlaylists() throws PersistenceException {

        List<Playlist> playlists = playlistPersistence.getAllPlaylists();

        if(playlists != null) {
            for(Playlist playlist : playlists) {
                List<Song> songs = playlistPersistence.getAllSongsByPlaylist(playlist.getName());
                List<Song> updatedSongs = new ArrayList<>();
                if(songs != null) {
                    for(Song song : songs) {
                        song = songPersistence.getSongByUUID(song.getUUID()); //TODO: Change this to use song + album + artist name for lookup
                        updatedSongs.add(song);
                    }
                }
                playlist.setSongs(updatedSongs);
            }
        }

        return playlists;
    }


    public boolean insertPlaylist(Playlist playlist) throws ArrayStoreException, IllegalArgumentException {
        if(playlist == null)
            throw new IllegalArgumentException();
        return playlistPersistence.storePlaylist(playlist);
    }

    public boolean insertSongForPlaylist(Playlist playlist, Song song) throws IllegalArgumentException {
        if(playlist == null || song == null)
            throw new IllegalArgumentException();
        return playlistPersistence.storeSongForPlaylist(playlist, song);
    }

    public boolean updatePlaylist(Playlist playlist, String newName) throws  IllegalArgumentException {
        if(playlist == null)
            throw new IllegalArgumentException();
        return playlistPersistence.updatePlaylist(playlist, newName);
    }

    public boolean deletePlaylist(Playlist playlist) throws IllegalArgumentException {
        if(playlist == null)
            throw new IllegalArgumentException();
        return playlistPersistence.deletePlaylist(playlist.getName());
    }
}
