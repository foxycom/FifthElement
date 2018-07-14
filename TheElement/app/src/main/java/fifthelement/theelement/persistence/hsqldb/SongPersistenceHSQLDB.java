package fifthelement.theelement.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fifthelement.theelement.application.Persistence;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.SongPersistence;

public class SongPersistenceHSQLDB implements SongPersistence {

    private Connection c;

    public SongPersistenceHSQLDB(final String dbPath) {
        try {
            this.c = DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath, "SA", "");
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    private Song fromResultSet(final ResultSet rs) throws SQLException {
        final String songName = rs.getString("songName");
        final String songPath = rs.getString("songPath");
        final String authorName = rs.getString("authorName");
        final String albumName = rs.getString("albumName");
        final String songGenre = rs.getString("songGenre");
        final double songRating = rs.getDouble("songRating");
        return new Song(songName, songPath, authorName, albumName, songGenre, songRating);
    }


    @Override
    public List<Song> getAllSongs() {

        final List<Song> songs = new ArrayList<>();

        try {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM songs");
            while (rs.next()) {
                final Song song = fromResultSet(rs);
                songs.add(song);
            }

            rs.close();
            st.close();

            return songs;
        }catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Song getSongByName(final String songName) throws PersistenceException, IllegalArgumentException {
        if( songName == null ) {
            throw new IllegalArgumentException("Cannot get song with a null");
        }
        try {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM songs WHERE songName = ?");
            st.setString(1, songName);

            final ResultSet rs = st.executeQuery();
            rs.next();
            final Song song = fromResultSet(rs);
            rs.close();
            st.close();

            return song;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    public List<Song> getSongsByAlbumName(final String albumName) throws PersistenceException, IllegalArgumentException {
        final List<Song> songs = new ArrayList<>();
        if( albumName == null ) {
            throw new IllegalArgumentException("Cannot get song with a null");
        }

        try {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM songs WHERE albumName = ?");
            st.setString(1, albumName);
            final ResultSet rs = st.executeQuery();
            while (rs.next())
            {
                final Song song = fromResultSet(rs);
                songs.add(song);
            }

            rs.close();
            st.close();

            return songs;
        }catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public List<Song> getSongsByAuthorName(final String authorName) throws PersistenceException, IllegalArgumentException{
        final List<Song> songs = new ArrayList<>();
        if( authorName == null ) {
            throw new IllegalArgumentException("Cannot get song with a null");
        }

        try {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM songs WHERE albumName = ?");
            st.setString(1, authorName);
            final ResultSet rs = st.executeQuery();
            while (rs.next())
            {
                final Song song = fromResultSet(rs);
                songs.add(song);
            }

            rs.close();
            st.close();

            return songs;
        }catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }


    @Override
    public boolean storeSong(Song song) throws PersistenceException, IllegalArgumentException {
        if( song == null ) {
            throw new IllegalArgumentException("Can't store a null");
        }
        try {
            final PreparedStatement st = c.prepareStatement("INSERT INTO songs VALUES(?, ?, ?, ?, ?, ?)");
            st.setString(1, song.getName());
            st.setString(2, song.getPath());
            if(song.getAuthorName() != null)
                st.setString(3, song.getAuthorName());
            else
                st.setString(3, null);
            if(song.getAlbumName() != null)
                st.setString(4, song.getAlbumName());
            else
                st.setString(4, null);
            st.setString(5, song.getGenre());
            st.setDouble(6, song.getRating());

            st.executeUpdate();

            return true;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public boolean updateSong(Song song) throws IllegalArgumentException, PersistenceException {
        if( song == null ) {
            throw new IllegalArgumentException("Can't store a null");
        }
        try {
            final PreparedStatement st = c.prepareStatement("UPDATE songs SET songName = ?, songPath = ?, authorName = ?, albumName = ?, songGenre = ?, songRating = ? WHERE songUUID = ?");
            st.setString(1, song.getName());
            st.setString(2, song.getPath());
            if(song.getAuthorName() != null){
                st.setString(3, song.getAuthorName());
            } else {
                st.setString(3,null);
            }

            if(song.getAlbumName() != null) {
                st.setString(4, song.getAlbumName());
            } else {
                st.setString(4, null);
            }

            st.setString(5, song.getGenre());

            st.setDouble(6, song.getRating());

            st.setString(7, song.getName());

            st.executeUpdate();

            return true;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean deleteSong(Song song) throws IllegalArgumentException, PersistenceException{
        if(song == null)
            throw new IllegalArgumentException();
        return deleteSong(song.getName());
    }

    @Override
    public boolean deleteSong(String songName) throws IllegalArgumentException, PersistenceException {
        boolean removed = songExists(songName);
        if(songName == null)
            throw new IllegalArgumentException("Cannot delete with a null");
        try {
            final PreparedStatement st = c.prepareStatement("DELETE FROM songs WHERE songName = ?");
            st.setString(1, songName);
            st.executeUpdate();
            removed = removed != songExists(songName);

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
        return  removed;
    }

    @Override
    public boolean songExists(Song song) throws PersistenceException, IllegalArgumentException {
        return songExists(song.getName());
    }

    @Override
    public boolean songExists(String songName) throws PersistenceException, IllegalArgumentException {
        Song song = getSongByName(songName);
        return song != null;
    }
}
