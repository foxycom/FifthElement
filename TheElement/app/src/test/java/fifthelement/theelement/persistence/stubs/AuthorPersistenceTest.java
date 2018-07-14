package fifthelement.theelement.persistence.stubs;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fifthelement.theelement.objects.Author;

@RunWith(JUnit4.class)
public class AuthorPersistenceTest {

    private AuthorPersistenceStub classUnderTest;
    private ArrayList<Author> authorList;

    private static String songNameOne;
    private static String songNameTwo;
    private static String songNameThree;
    private static String songNameFour;

    @Before
    public void initClass() {
        authorList = getAuthorList();
        classUnderTest = new AuthorPersistenceStub();
        songNameOne = classUnderTest.getAllAuthors().get(0).getName();
        songNameTwo = classUnderTest.getAllAuthors().get(1).getName();
        songNameThree = classUnderTest.getAllAuthors().get(2).getName();
        songNameFour = classUnderTest.getAllAuthors().get(3).getName();
    }

    @Test
    public void testGetAllAuthors() {
        List<Author> authors = classUnderTest.getAllAuthors();
        Assert.assertTrue("testGetAllAuthors: author size == 4, actual size: "+authors.size(), authors.size() == 4);
    }

    @Test
    public void testValidGetAuthorById() {
        Author author = classUnderTest.getAuthorByName(songNameOne);
        Assert.assertTrue("testValidGetAuthorById: author id != 1",author.getName().equals(songNameOne));
        Assert.assertTrue("testValidGetAuthorById: author name != Bob Marley", "Bob Marley".equals(author.getName()));
    }

    @Test
    public void testInvalidGetAuthorById() {
        Author author = classUnderTest.getAuthorByName("Random Author");
        Assert.assertTrue("testInvalidGetAuthorById: author != null",author == null);
    }

    @Test
    public void testValidStoreAuthor() {
        Author author = new Author("Inserted Author");
        String authorName = author.getName();
        classUnderTest.storeAuthor(author);

        List<Author> authors = classUnderTest.getAllAuthors();
        Assert.assertTrue("testValidStoreAuthor: author size != 4, actual size: "+authors.size(), authors.size() == 5);

        author = classUnderTest.getAuthorByName(authorName);
        Assert.assertTrue("testValidStoreAuthor: author id != 4",author.getName().equals(authorName));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAuthorStore() {
        Author author = new Author("Some Author");
        author.setName(songNameOne);
        classUnderTest.storeAuthor(author);
    }

    @Test
    public void testValidUpdateAuthor() {
        Author author = new Author("Changed Author Name");
        author.setName(songNameTwo);
        classUnderTest.updateAuthor(author);

        List<Author> authors = classUnderTest.getAllAuthors();
        Assert.assertTrue("testValidUpdateAuthor: author size != 3, actual size: "+authors.size(), authors.size() == 4);

        author = classUnderTest.getAuthorByName(songNameTwo);
        Assert.assertTrue("testValidUpdateAuthor: author name != Changed Author Name", "Changed Author Name".equals(author.getName()));
    }

    @Test
    public void testValidUpdateAuthorNotExist() {
        Author author = new Author("This author does not exist");
        author.setName(songNameFour);
        classUnderTest.updateAuthor(author);

        List<Author> authors = classUnderTest.getAllAuthors();
        Assert.assertTrue("testValidUpdateAuthorNotExist: author size != 3, actual size: "+authors.size(), authors.size() == 4);

        author = classUnderTest.getAuthorByName(songNameFour);
        Assert.assertFalse("testValidUpdateAuthorNotExist: author != null",author == null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidUpdateAuthor() {
        Author author = null;
        Boolean b = classUnderTest.updateAuthor(author);
    }

    @Test
    public void testValidDeleteAuthor() {
        boolean result = classUnderTest.deleteAuthor(classUnderTest.getAuthorByName(songNameOne));

        Assert.assertTrue("testValidDeleteAuthor: result = false", result);

        List<Author> authors = classUnderTest.getAllAuthors();
        Assert.assertTrue("testValidDeleteAuthor: author size != 2, actual size: "+authors.size(), authors.size() == 3);

        Author author = classUnderTest.getAuthorByName(songNameOne);
        Assert.assertTrue("testValidDeleteAuthor: author != null",author == null);
    }

    @Test
    public void testNotFoundDeleteAuthor() {
        boolean result = classUnderTest.deleteAuthor(classUnderTest.getAuthorByName(songNameFour));

        Assert.assertTrue("testNotFoundDeleteAuthor: result = true", result);

        List<Author> authors = classUnderTest.getAllAuthors();
        Assert.assertTrue("testNotFoundDeleteAuthor: author size != 3, actual size: "+authors.size(), authors.size() == 3);

        Author author = classUnderTest.getAuthorByName(songNameFour);
        Assert.assertTrue("testNotFoundDeleteAuthor: author != null",author == null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDeleteAuthor() {
        Author a = null;
        boolean result = classUnderTest.deleteAuthor(a);
    }

    private static ArrayList<Author> getAuthorList() {
        Author authorOne = new Author("Test Author");
        Author authorTwo = new Author("Another Test Author");
        Author authorThree = new Author("Some Author");

        authorOne.setName(songNameOne);
        authorTwo.setName(songNameTwo);
        authorThree.setName(songNameThree);

        ArrayList<Author> authors = new ArrayList<>();
        authors.add(authorOne);
        authors.add(authorTwo);
        authors.add(authorThree);
        return authors;
    }
}
