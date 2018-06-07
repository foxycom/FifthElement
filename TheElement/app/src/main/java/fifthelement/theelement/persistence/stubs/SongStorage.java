package main.java.fifthelement.theelement.persistence.stubs;
/* SongStorage (a stub)
 * extends BaseStorage
 * acts as a database. stores Songs
 */

import java.util.List;

import main.java.fifthelement.theelement.objects.Song;

public class SongStorage extends BaseStorage<Song> {

    public SongStorage() {
        super();
    }

    public SongStorage(List<Song> songs) {
        super(songs);
    }

}

