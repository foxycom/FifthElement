package fifthelement.theelement.business.services;

import java.util.List;
import java.util.UUID;

import fifthelement.theelement.application.Persistence;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.persistence.AlbumPersistence;
import fifthelement.theelement.persistence.SongPersistence;


public class AlbumService {

    private AlbumPersistence albumPersistence;

    public AlbumService() {
        albumPersistence = Persistence.getAlbumPersistence();
    }

    public AlbumService(AlbumPersistence albumPersistence, SongPersistence songPersistence) {
        this.albumPersistence = albumPersistence;
    }

    public Album getAlbumByUUID(UUID uuid) {
        return albumPersistence.getAlbumByUUID(uuid);
    }

    public List<Album> getAlbums() {
        return albumPersistence.getAllAlbums();
    }

    public boolean insertAlbum(Album album) throws ArrayStoreException, IllegalArgumentException {
        if(album == null)
            throw new IllegalArgumentException();
        return albumPersistence.storeAlbum(album);
    }

    public boolean updateAlbum(Album album) throws IllegalArgumentException {
        if(album == null)
            throw new IllegalArgumentException();
        return albumPersistence.updateAlbum(album);
    }

    public boolean deleteAlbum(Album album) throws IllegalArgumentException {
        if(album == null)
            throw new IllegalArgumentException();
        return albumPersistence.deleteAlbum(album);
    }

}
