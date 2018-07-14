package fifthelement.theelement.persistence.stubs;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Song;

@RunWith(JUnit4.class)
public class SongPersistenceTest {

    private SongPersistenceStub classUnderTest;
    private ArrayList<Song> songList;

    private static String songNameOne;
    private static String songNameTwo;
    private static String songNameThree;
    private static String songNameFour;

    @Before
    public void initClass() {
        songList = getSongList();
        classUnderTest = new SongPersistenceStub(songList);
    }

    @Test
    public void testGetAllSongs() {
        List<Song> songs = classUnderTest.getAllSongs();
        Assert.assertTrue("testGetAllSongs: song size != 3", songs.size() == 3);
    }

    @Test
    public void testValidGetSongById() {
        Song song = classUnderTest.getSongByName(songNameOne);
        Assert.assertTrue("testValidGetSongById: song id != 1",song.getName().equals(songNameOne));
        Assert.assertTrue("testValidGetSongById: song name != Test Song", "Test Song".equals(song.getName()));
    }

    @Test
    public void testInvalidGetSongById() {
        Song song = classUnderTest.getSongByName(songNameFour);
        Assert.assertTrue("testInvalidGetSongById: song != null",song == null);
    }

    @Test
    public void testValidStoreSong() {
        Song song = new Song("Inserted Song", "");
        classUnderTest.storeSong(song);

        List<Song> songs = classUnderTest.getAllSongs();
        Assert.assertTrue("testValidStoreSong: song size != 4", songs.size() == 4);

        song = classUnderTest.getSongByName("Inserted Song");
        Assert.assertTrue("testValidStoreSong: song id != 4",song.getName().equals("Inserted Song"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidSongStore() {
        Song song = new Song("Some Song", "");
        song.setName(songNameOne);
        classUnderTest.storeSong(song);
    }

    @Test
    public void testValidUpdateSong() {
        Song song = new Song("Changed Song Name", "");
        song.setName(songNameTwo);
        classUnderTest.updateSong(song);

        List<Song> songs = classUnderTest.getAllSongs();
        Assert.assertTrue("testValidUpdateSong: song size != 3", songs.size() == 3);

        song = classUnderTest.getSongByName(songNameTwo);
        Assert.assertTrue("testValidUpdateSong: song name != Changed Song Name", "Changed Song Name".equals(song.getName()));
    }

    @Test
    public void testValidUpdateSongNotExist() {
        Song song = new Song("This song does not exist", "");
        song.setName(songNameFour);
        classUnderTest.updateSong(song);

        List<Song> songs = classUnderTest.getAllSongs();
        Assert.assertTrue("testValidUpdateSongNotExist: song size != 3", songs.size() == 3);

        song = classUnderTest.getSongByName(songNameFour);
        Assert.assertTrue("testValidUpdateSongNotExist: song != null",song == null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidUpdateSong() {
        Song song = null;
        boolean result = classUnderTest.updateSong(song);
    }

    @Test
    public void testValidDeleteSong() {
        boolean result = classUnderTest.deleteSong(songNameOne);

        Assert.assertTrue("testValidDeleteSong: result = false", result);

        List<Song> songs = classUnderTest.getAllSongs();
        Assert.assertTrue("testValidDeleteSong: song size != 2", songs.size() == 2);

        Song song = classUnderTest.getSongByName(songNameOne);
        Assert.assertTrue("testValidDeleteSong: song != null",song == null);
    }

    @Test
    public void testNotFoundDeleteSong() {
        boolean result = classUnderTest.deleteSong(songNameFour);

        Assert.assertFalse("testNotFoundDeleteSong: result = true", result);

        List<Song> songs = classUnderTest.getAllSongs();
        Assert.assertTrue("testNotFoundDeleteSong: song size != 3", songs.size() == 3);

        Song song = classUnderTest.getSongByName(songNameFour);
        Assert.assertTrue("testNotFoundDeleteSong: song != null",song == null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDeleteSong() {
        Song s = null;
        boolean result = classUnderTest.deleteSong(s);
    }

    private static ArrayList<Song> getSongList() {
        Song songOne = new Song("Test Song", "");
        Song songTwo = new Song("Another Test Song", "");
        Song songThree = new Song("Some Song", "");

        songOne.setName(songNameOne);
        songTwo.setName(songNameTwo);
        songThree.setName(songNameThree);

        ArrayList<Song> songs = new ArrayList<>();
        songs.add(songOne);
        songs.add(songTwo);
        songs.add(songThree);
        return songs;
    }
}
