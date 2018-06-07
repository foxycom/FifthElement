package main.java.fifthelement.theelement.persistence.stubs;
/* AuthorStorage (a stub)
 * extends BaseStorage
 * acts as a database. stores Authors
 */

import java.util.List;

import main.java.fifthelement.theelement.objects.Author;

public class AuthorStorage extends BaseStorage<Author> {

    public AuthorStorage() {
        super();
    }

    public AuthorStorage(List<Author> authors) {
        super(authors);
    }

}

