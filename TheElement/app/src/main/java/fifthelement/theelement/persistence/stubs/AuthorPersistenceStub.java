package fifthelement.theelement.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import fifthelement.theelement.objects.Author;
import fifthelement.theelement.persistence.AuthorPersistence;

public class AuthorPersistenceStub implements AuthorPersistence {

    private List<Author> authorList;

    public AuthorPersistenceStub() {
        this.authorList = new ArrayList<>();

        this.authorList.add(new Author("Bob Marley"));
        this.authorList.add(new Author("Led Zepplin"));
        this.authorList.add(new Author("Justin Bieber"));
        this.authorList.add(new Author("Jeremy The Goat"));

    }


    @Override
    public List<Author> getAllAuthors() {
        return authorList;
    }

    @Override
    public Author getAuthorByName(String authorName) throws IllegalArgumentException {
        if(authorName == null)
            throw new IllegalArgumentException("Cannot get author with a null String");
        for(Author a : this.authorList)
            if(a.getName().compareTo(authorName) == 0)
                return a;
        return null;
    }

    @Override
    public boolean storeAuthor(Author author) throws IllegalArgumentException {
        if(author == null)
            throw new IllegalArgumentException("Cannot insert a null author");
        if(authorExists(author.getName()))
            throw new IllegalArgumentException("Cant store an author with existing String");
        this.authorList.add(author);
        return true;
    }

    @Override
    public boolean updateAuthor(Author author) throws IllegalArgumentException {
        boolean removed = false;
        if(author == null)
            throw new IllegalArgumentException("Cannot update a null author");
        for(int index = 0; index < authorList.size(); index++) {
            if(authorList.get(index).getName().compareTo(author.getName()) == 0) {
                this.authorList.set(index, author);
                removed = true;
                break;
            }
        }
        return removed;
    }

    @Override
    public boolean deleteAuthor(Author author) throws IllegalArgumentException {
        if (author == null)
            throw new IllegalArgumentException("Cannot delete an author with a null author");
        return this.deleteAuthor(author.getName());
    }

    @Override
    public boolean deleteAuthor(String authorName) throws IllegalArgumentException {
        boolean removed = false;
        if(authorName == null)
            throw new IllegalArgumentException("Cannot delete an author with a null String");
        for(int index = 0; index < authorList.size(); index++) {
            if(authorList.get(index).getName().compareTo(authorName) == 0) {
                this.authorList.remove(index);
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public boolean authorExists(Author author) {
        if(author == null)
            throw new IllegalArgumentException("Cannot check exists with a null Author");
        return this.authorExists(author.getName());
    }

    @Override
    public boolean authorExists(String authorName) {
        if(authorName == null)
            throw new IllegalArgumentException("Cannot check exists with a null String");
        boolean exists = false;
        for(Author a : this.authorList)
            if(a.getName().compareTo(authorName) == 0) {
                exists = true;
                break;
            }
        return exists;
    }
}
