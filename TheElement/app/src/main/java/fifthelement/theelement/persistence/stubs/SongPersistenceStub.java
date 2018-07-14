package fifthelement.theelement.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.SongPersistence;

public class SongPersistenceStub implements SongPersistence {

    private List<Song> songList;

    public SongPersistenceStub() {
        this.songList = new ArrayList<>();

        Song song = new Song("This Is America", "android.resource://fifthelement.theelement/raw/childish_gambino_this_is_america");
        song.setAuthorName("Childish Gambino");
        this.storeSong(song);

        song = new Song("Classical Music", "android.resource://fifthelement.theelement/raw/classical_music");
        this.storeSong(song);

        song = new Song("Adventure of a Lifetime", "android.resource://fifthelement.theelement/raw/coldplay_adventure_of_a_lifetime");
        song.setAuthorName("Coldplay");
        this.storeSong(song);

        song = new Song("Hall of Fame", "android.resource://fifthelement.theelement/raw/hall_of_fame");
        this.storeSong(song);
    }

    public SongPersistenceStub(List<Song> songList) {
        this.songList = songList;
    }

    @Override
    public List<Song> getAllSongs() {
        return songList;
    }

    @Override
    public Song getSongByName(final String authorName) throws IllegalArgumentException {
        if(authorName == null)
            throw new IllegalArgumentException("Cannot get song with a null String");
        for(Song s : this.songList)
            if(s.getName().compareTo(authorName) == 0)
                return s;
        return null;
    }

    @Override
    public boolean storeSong(Song song) throws IllegalArgumentException {
        if(song == null)
            throw new IllegalArgumentException("Cant store a song with null Song");
        if(songExists(song.getName()))
            throw new IllegalArgumentException("Cant store a song with existing String");
        this.songList.add(song);
        return true;
    }

    @Override
    public boolean updateSong(Song song) throws IllegalArgumentException {
        boolean found = false;
        if(song == null)
            throw new IllegalArgumentException("Cannot update a null song");
        for(int index = 0; index < songList.size(); index++)
            if(songList.get(index).getName().compareTo(song.getName()) == 0) {
                this.songList.set(index, song);
                found = true;
            }
        return found;
    }

    @Override
    public boolean deleteSong(Song song) throws IllegalArgumentException {
        if(song == null)
            throw new IllegalArgumentException("Cannot delete song with a null Song");
        return deleteSong(song.getName());
    }

    @Override
    public boolean deleteSong(String authorName) throws IllegalArgumentException {
        boolean removed = false;
        if(authorName == null)
            throw new IllegalArgumentException("Cannot delete song with a null String");
        for(int index = 0; index < songList.size(); index++) {
            if(songList.get(index).getName().compareTo(authorName) == 0) {
                this.songList.remove(index);
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public boolean songExists(Song song) throws IllegalArgumentException {
        if(song == null)
            throw new IllegalArgumentException("Cannot check exists with a null Song");
        return songExists(song.getName());
    }

    @Override
    public boolean songExists(String authorName) throws IllegalArgumentException {
        if(authorName == null)
            throw new IllegalArgumentException("Cannot check exists with a null String");
        boolean exists = false;
        for(Song s : this.songList)
            if(s.getName().compareTo(authorName) == 0) {
                exists = true;
                break;
            }
        return exists;
    }

    @Override
    public List<Song> getSongsByAlbumName(String albumName) throws IllegalArgumentException {
        if(albumName == null)
            throw new IllegalArgumentException("Cannot get song with a null album String");
        return null;
    }

    @Override
    public List<Song> getSongsByAuthorName(String authorName) throws IllegalArgumentException {
        if(authorName == null)
            throw new IllegalArgumentException("Cannot get song with a null album String");
        return null;
    }
}
