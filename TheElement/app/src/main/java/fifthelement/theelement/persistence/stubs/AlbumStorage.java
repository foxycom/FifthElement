package main.java.fifthelement.theelement.persistence.stubs;
/* AlbumStorage (a stub)
 * extends BaseStorage
 * acts as a database. stores Albums
 */

import java.util.List;

import main.java.fifthelement.theelement.objects.Album;

public class AlbumStorage extends BaseStorage<Album> {

    public AlbumStorage() {
        super();
    }

    public AlbumStorage(List<Album> albums) {
        super(albums);
    }

}
