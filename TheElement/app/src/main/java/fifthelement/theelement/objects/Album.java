package fifthelement.theelement.objects;

public class Album{
    private String albumName;
    private String authorName;

    public Album(String name){
        this.albumName = name;
        this.authorName = "";
    }


    public Album(String name, String authorName){
        this.albumName = name;
        this.authorName = authorName;
    }

    // getters
    public String getName() {
        return albumName;
    }

    public String getAuthorName() {
        return authorName;
    }

    // setters
    public void setName(String newName) { this.albumName = newName; }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Album && ((Album) object).getName().equals(this.getName())
                && ((Album) object).getName().equals(this.getName());
    }
}