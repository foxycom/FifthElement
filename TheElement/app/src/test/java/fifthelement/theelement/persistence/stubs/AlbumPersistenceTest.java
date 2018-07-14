package fifthelement.theelement.persistence.stubs;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Album;

@RunWith(JUnit4.class)
public class AlbumPersistenceTest {

    private AlbumPersistenceStub classUnderTest;
    private ArrayList<Album> albumList;

    private static String albumNameOne;
    private static String albumNameTwo;
    private static String albumNameThree;
    private static String albumNameFour;

    @Before
    public void initClass() {
        albumList = getAlbumList();
        classUnderTest = new AlbumPersistenceStub();
        albumNameOne = classUnderTest.getAllAlbums().get(0).getName();
        albumNameTwo = classUnderTest.getAllAlbums().get(1).getName();
        albumNameThree = classUnderTest.getAllAlbums().get(2).getName();
        albumNameFour = classUnderTest.getAllAlbums().get(3).getName();


    }

    @Test
    public void testGetAllAlbums() {
        List<Album> albums = classUnderTest.getAllAlbums();
        Assert.assertTrue("testGetAllAlbums: album size == 4, actual size: "+albums.size(), albums.size() == 4);
    }

    @Test
    public void testValidGetAlbumById() {
        Album album = classUnderTest.getAlbumByName(albumNameOne);
        Assert.assertTrue("testValidGetAlbumById: album id != 1",album.getName().equals(albumNameOne));
        Assert.assertTrue("testValidGetAlbumById: album name != Album1", "Album1".equals(album.getName()));
    }

    @Test
    public void testInvalidGetAlbumById() {
        Album album = classUnderTest.getAlbumByName("Random album");
        Assert.assertTrue("testInvalidGetAlbumById: album != null",album == null);
    }

    @Test
    public void testValidStoreAlbum() {
        Album album = new Album("Inserted Album");
        classUnderTest.storeAlbum(album);

        List<Album> albums = classUnderTest.getAllAlbums();
        Assert.assertTrue("testValidStoreAlbum: album size != 4, actual size: "+albums.size(), albums.size() == 5);

        album = classUnderTest.getAlbumByName("Inserted Album");
        Assert.assertTrue("testValidStoreAlbum: album id != 4",album.getName().equals("Inserted Album"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAlbumStore() {
        Album album = new Album("Some Album");
        album.setName(albumNameOne);
        classUnderTest.storeAlbum(album);
    }

    @Test
    public void testValidUpdateAlbum() {
        Album album = new Album(albumNameTwo);
        album.setAuthorName(albumNameTwo);
        classUnderTest.updateAlbum(album);

        List<Album> albums = classUnderTest.getAllAlbums();
        Assert.assertTrue("testValidUpdateAlbum: album size != 3, actual size: "+albums.size(), albums.size() == 4);

        album = classUnderTest.getAlbumByName("Changed Album Name");
        Assert.assertTrue("testValidUpdateAlbum: album name != Changed Album Name", albumNameTwo.equals(album.getAuthorName()));
    }

    @Test
    public void testValidUpdateAlbumNotExist() {
        Album album = new Album("This album does not exist");
        classUnderTest.updateAlbum(album);

        List<Album> albums = classUnderTest.getAllAlbums();
        Assert.assertTrue("testValidUpdateAlbumNotExist: album size != 3, actual size: "+albums.size(), albums.size() == 4);

        album = classUnderTest.getAlbumByName(albumNameFour);
        Assert.assertFalse("testValidUpdateAlbumNotExist: album != null",album == null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidUpdateAlbum() {
        Album album = null;
        if( classUnderTest.updateAlbum(album) ) {
            Album a = album;
        }
    }

    @Test
    public void testValidDeleteAlbum() {
        boolean result = classUnderTest.deleteAlbum(classUnderTest.getAlbumByName(albumNameOne));

        Assert.assertTrue("testValidDeleteAlbum: result = false", result);

        List<Album> albums = classUnderTest.getAllAlbums();
        Assert.assertTrue("testValidDeleteAlbum: album size != 2, actual size: "+albums.size(), albums.size() == 3);

        Album album = classUnderTest.getAlbumByName(albumNameOne);
        Assert.assertTrue("testValidDeleteAlbum: album != null",album == null);
    }

    @Test
    public void testNotFoundDeleteAlbum() {
        boolean result = classUnderTest.deleteAlbum(classUnderTest.getAlbumByName(albumNameFour));

        Assert.assertTrue("testNotFoundDeleteAlbum: result = true", result);

        List<Album> albums = classUnderTest.getAllAlbums();
        Assert.assertTrue("testNotFoundDeleteAlbum: album size != 3, actual size: "+albums.size(), albums.size() == 3);

        Album album = classUnderTest.getAlbumByName(albumNameFour);
        Assert.assertTrue("testNotFoundDeleteAlbum: album != null",album == null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDeleteAlbum() {
        Album a = null;
        boolean result = classUnderTest.deleteAlbum(a);
    }

    private static ArrayList<Album> getAlbumList() {

        Album albumOne = new Album("Test Album");
        Album albumTwo = new Album("Another Test Album");
        Album albumThree = new Album("Some Album");

        albumOne.setName(albumNameOne);
        albumTwo.setName(albumNameTwo);
        albumThree.setName(albumNameThree);

        ArrayList<Album> albums = new ArrayList<>();
        albums.add(albumOne);
        albums.add(albumTwo);
        albums.add(albumThree);
        return albums;
    }
}
