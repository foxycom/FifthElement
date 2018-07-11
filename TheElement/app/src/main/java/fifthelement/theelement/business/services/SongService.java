package fifthelement.theelement.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fifthelement.theelement.application.Persistence;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.exceptions.SongAlreadyExistsException;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.AlbumPersistence;
import fifthelement.theelement.persistence.AuthorPersistence;
import fifthelement.theelement.persistence.PlaylistPersistence;
import fifthelement.theelement.persistence.SongPersistence;
import fifthelement.theelement.persistence.hsqldb.PersistenceException;

public class SongService {

    private SongPersistence songPersistence;
    private AlbumPersistence albumPersistence;
    private AuthorPersistence authorPersistence;
    private PlaylistPersistence playlistPersistence;
    private AuthorService authorService;
    private AlbumService albumService;

    public SongService() {
        songPersistence = Persistence.getSongPersistence();
        albumPersistence = Persistence.getAlbumPersistence();
        authorPersistence = Persistence.getAuthorPersistence();
        playlistPersistence = Persistence.getPlaylistPersistence();
        authorService = Services.getAuthorService();
        albumService = Services.getAlbumService();
    }

    public SongService(SongPersistence songPersistence, AlbumPersistence albumPersistence, AuthorPersistence authorPersistence, PlaylistPersistence playlistPersistence) {
        this.songPersistence = songPersistence;
        this.albumPersistence = albumPersistence;
        this.authorPersistence = authorPersistence;
        this.playlistPersistence = playlistPersistence;
        this.albumService = new AlbumService(albumPersistence, songPersistence);
        this.authorService = new AuthorService(authorPersistence);

    }

    public Song getSongByUUID(UUID uuid) {
        return songPersistence.getSongByUUID(uuid);
    }

    public List<Song> getSongs() throws PersistenceException {
        List<Song> songs = songPersistence.getAllSongs();

        if(songs != null) {
            for(Song song : songs) {
                if(song.getAuthorName() != null)
                    song.setAuthorName(authorPersistence.getAuthorByUUID(song.getAuthorName())); //TODO: Change these to get by names
                if(song.getAlbumName() != null) {
                    song.setAlbumName(albumPersistence.getAlbumByUUID(song.getAlbumName(), song.getAuthorName())); //TODO: Change these to get by names
                }
            }
        }

        return songs;
    }

    public void createSong(String realPath, String songName, String songArtist, String songAlbum, String songGenre) throws PersistenceException, IllegalArgumentException, SongAlreadyExistsException {
        if(songName == null || realPath == null)
            throw new IllegalArgumentException();

        Author author = null;
        Album album = null;
        Song song = new Song(songName, realPath);
        if(songArtist != null) {
            song.setAuthorName(songArtist);
            //TODO: Check to see if equivalent author exists in DB, if not, insert new one!
            author = new Author(songArtist);
            authorService.insertAuthor(author);
        }

        if(songAlbum != null) {
            song.setAlbumName(songAlbum);
            //TODO: Check to see if equivalent album exists in DB, if not, insert new one!
            album = new Album(songAlbum, songArtist);
            albumService.insertAlbum(album);
        }

        if(songGenre != null)
            song.setGenre(songGenre);
        insertSong(song);
    }

    public boolean insertSong(Song song) throws PersistenceException, IllegalArgumentException, SongAlreadyExistsException {
        if(song == null)
            throw new IllegalArgumentException();
        if(pathExists(song.getPath()))
            throw new SongAlreadyExistsException(song.getPath());
        return songPersistence.storeSong(song);
    }

    public boolean updateSong(Song song) throws PersistenceException, IllegalArgumentException {
        if(song == null)
            throw new IllegalArgumentException();

        return songPersistence.updateSong(song);
    }

    public boolean updateSongWithParameters(Song song, String songName, String author, String album, String genre) {

        song.setName(songName);

        song.setAuthorName(author);
        Author newAuthor = new Author(author);
        if(!author.equals("")) { //TODO: Have persistence check to see if author with name exists, if not, insert new!
            authorService.insertAuthor(newAuthor);
        }

        song.setAlbumName(album);
        Album newAlbum = new Album(album, author);
        if(!album.equals("")) { // TODO: Have persistence check to see if album with name + author exists, if not, insert new one!
            albumService.insertAlbum(newAlbum);
        }

        song.setGenre(genre);

        return updateSong(song);
    }

    public boolean updateSongWithRating(Song song, double rating){
        song.setRating(rating);
        return updateSong(song);
    }


    public boolean deleteSong(Song songToRemove) throws PersistenceException, IllegalArgumentException {
        if(songToRemove == null)
            throw new IllegalArgumentException();
        else{
            // deletes songs from existing PlayList if it's there
            // implementation for this hasn't been fully decided. this is a STUB
            //for( PlayList p : playlistPersistence.getAllPlayLists() ) {
            //    if( p.contains(song) ) {
            //        p.removeSong(song);
            //        playlistPersistence.updatePlayList(p);
            //    }
            //}
            return songPersistence.deleteSong(songToRemove);
        }
    }

    // Method checks if any song already has the same path
    // and returns true if a songs exists with the same path
    public boolean pathExists(String path) {
        List<Song> songs = getSongs();
        boolean toReturn = false;

        for(Song song : songs) {
            if(song.getPath().equals(path)) {
                toReturn = true;
                break;
            }
        }
        return toReturn;
    }

    public List<Song> search(String query) {
        List<Song> allSongs = getSongs();
        ArrayList<Song> matchesList = new ArrayList<>();
        Matcher matcher;

        if (validateRegex(query)){
            String regex = query;
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

            for (Song s: allSongs) {
                matcher = pattern.matcher(s.getName());
                if ( matcher.find()){
                    matchesList.add(s);
                }
            }
        }
        return matchesList;
    }

    // Making sure the regex pattern doesn't contain special
    // characters, which screws up regex compiling
    private boolean validateRegex(String regexPattern){
        boolean result = false;
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher matcher = special.matcher(regexPattern);
        if (matcher.find() == false)
            result = true;
        return result;
    }
}
