package fifthelement.theelement.persistence.stubs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.SongPersistence;

public class SongPersistenceStub implements SongPersistence {

    private List<Song> songList;

    public SongPersistenceStub() {
        this.songList = new ArrayList<>();

        /*
            Tife just change the song path here, the SongService
            calls this constructor
         */
        Song song = new Song("This Is America", "android.resource://fifthelement.theelement/raw/childish_gambino_this_is_america");
        song.addAuthor(new Author("Childish Gambino"));
        this.storeSong(song);

        song = new Song("Classical Music", "android.resource://fifthelement.theelement/raw/classical_music");
        this.storeSong(song);

        song = new Song("Adventure of a Lifetime", "android.resource://fifthelement.theelement/raw/coldplay_adventure_of_a_lifetime");
        song.addAuthor(new Author("Coldplay"));
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
    public Song getSongByUUID(final UUID uuid) {
        for(Song s : this.songList)
            if(s.getUUID().compareTo(uuid) == 0)
                return s;
        return null;
    }

    @Override
    public boolean storeSong(Song song) {
        if(songExists(song.getUUID()))
            throw new ArrayStoreException();
        this.songList.add(song);
        return true;
    }

    @Override
    public boolean updateSong(Song song) {
        boolean found = false;
        if(song == null)
            throw new IllegalArgumentException("Cannot update a null song");
        for(int index = 0; index < songList.size(); index++)
            if(songList.get(index).getUUID().compareTo(song.getUUID()) == 0) {
                this.songList.set(index, song);
                found = true;
            }
        return found;
    }

    @Override
    public boolean deleteSong(UUID uuid) {
        boolean removed = false;
        if(uuid == null)
            throw new IllegalArgumentException("Cannot delete with a null UUID");
        for(int index = 0; index < songList.size(); index++) {
            if(songList.get(index).getUUID().compareTo(uuid) == 0) {
                this.songList.remove(index);
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public boolean songExists(UUID uuid) {
        boolean exists = false;
        for(Song s : this.songList)
            if(s.getUUID().compareTo(uuid) == 0) {
                exists = true;
                break;
            }
        return exists;
    }
}
