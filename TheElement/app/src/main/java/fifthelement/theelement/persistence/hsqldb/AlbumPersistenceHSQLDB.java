package fifthelement.theelement.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.application.Persistence;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.AlbumPersistence;

public class AlbumPersistenceHSQLDB implements AlbumPersistence {

    private Connection c;

    public AlbumPersistenceHSQLDB(final String dbPath) {
        try {
            this.c = DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath, "SA", "");
        }catch(final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private Album fromResultSet(final ResultSet rs) throws SQLException {
        final UUID albumUUID = UUID.fromString(rs.getString("albumUUID"));
        final String albumName = rs.getString("albumName");
        final String authorName = rs.getString("authorName");
        return new Album(albumName, authorName);
    }

    @Override
    public List<Album> getAllAlbums() throws PersistenceException {
        final List<Album> albums = new ArrayList<>();

        try {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM albums");
            while (rs.next())
            {
                final Album album = fromResultSet(rs);
                albums.add(album);
            }
            rs.close();
            st.close();

            return albums;

        }catch(final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public Album getAlbumByName(String albumName) throws PersistenceException, IllegalArgumentException {
        if(albumName == null)
            throw new IllegalArgumentException("Cannot get album with a null");
        try {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM albums WHERE albumName = ?");
            st.setString(1, albumName);

            final ResultSet rs = st.executeQuery();
            rs.next();
            final Album album = fromResultSet(rs);
            rs.close();
            st.close();

            return album;
        }catch(final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean storeAlbum(Album album) throws PersistenceException, IllegalArgumentException {
        if(album == null)
            throw new IllegalArgumentException("Cannot store an album with null Album");
        try {
            final PreparedStatement st = c.prepareStatement("INSERT INTO albums VALUES(?, ?)");
            st.setString(1, album.getName());
            if(album.getAuthorName() != null)
                st.setString(2, album.getAuthorName());
            else
                st.setString(2, null);

            st.executeUpdate();

            return true;
        }catch(final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean updateAlbum(Album album) throws PersistenceException, IllegalArgumentException {
        if(album == null)
            throw new IllegalArgumentException("Cannot update a null album");
        try {
            final PreparedStatement st = c.prepareStatement("UPDATE albums SET albumName = ? authorName = ? WHERE albumName = ?");
            st.setString(1, album.getName());
            st.setString(2, album.getAuthorName());
            st.setString(3, album.getName());

            st.executeUpdate();

            return true;
        }catch(final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean deleteAlbum(Album album) throws PersistenceException, IllegalArgumentException {
        if (album == null)
            throw new IllegalArgumentException("Cannot delete album with a null album");
        return deleteAlbum(album.getName());
    }

    @Override
    public boolean deleteAlbum(String albumName) throws PersistenceException, IllegalArgumentException {
        boolean removed = albumExists(albumName);
        if(albumName == null)
            throw new IllegalArgumentException("Cannot delete album with a null");
        try {
            final PreparedStatement st = c.prepareStatement("DELETE FROM albums WHERE albumName = ?");
            st.setString(1, albumName);
            st.executeUpdate();
            removed = removed != albumExists(albumName);
        }catch(final SQLException e) {
            throw new PersistenceException(e);
        }
        return removed;
    }

    @Override
    public boolean albumExists(Album album) throws PersistenceException {
        if(album == null)
            throw new IllegalArgumentException("Cannot check exists with a null Album");
        return albumExists(album.getName());
    }

    @Override
    public boolean albumExists(String albumName) throws PersistenceException {
        if(albumName == null)
            throw new IllegalArgumentException("Cannot check exists with a null");
        Album album = getAlbumByName(albumName);
        return album != null;
    }
}
