package fifthelement.theelement.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Author {
    private String name;

    public Author(String name) {
        this.name = name;
    }

    public Author(String name, List<Song> songList, List<Album> albumList) {
        this.name = name;
    }

    // getters
    public String getName() {
        return name;
    }

    // setters
    public void setName(String newName) {
        this.name = newName;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Author && ((Author) object).getName().equals(this.getName());
    }
}