package fifthelement.theelement.persistence.stubs;

import java.util.ArrayList;

import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.PlaylistPersistence;

public class PlaylistPersistenceStub implements PlaylistPersistence {

    private Playlist playlist;

    public PlaylistPersistenceStub(int id, String name) {

        String[] songName = {"Nice For What", "God's Plan", "This Is America", "Yes Indeed", "No Tears Left To Cry"};
        String[] authorNames = {"Drake", "Drake", "Childish Gambino", "Lil Baby & Drake", "Ariana Grande" };
        ArrayList<Song> songs = new ArrayList<Song>();
        for(int i = 0; i < 12; i++) {
            Song song = new Song(i, songName[i%5]);
            song.addAlbum(new Album(i, songName[i%5]));
            song.addAuthor(new Author(i, authorNames[i%5]));
            songs.add(song);
        }

        this.playlist = new Playlist(1, "Numero uno list");
        this.playlist.setSongs(songs);
    }

    @Override
    public ArrayList<Playlist> getAllPlaylists() {
        return null;
    }

    @Override
    public void deletePlaylist(Playlist currentPlaylist) { }

    @Override
    public boolean removeSong(Playlist currentPlaylist, int songId) {
        currentPlaylist.getSongs().remove(0);
        return true;
    }

    @Override
    public Song getRandomSong(Playlist currentPlaylist) {
        return currentPlaylist.getSongs().get(0);
    }

    @Override
    public boolean insertSong(Playlist currentPlaylist, Song song) {
        currentPlaylist.addSong(song);
        return true;
    }

    @Override
    public ArrayList<Song> getAllSongs() {
        return null;
    }
}
