package fifthelement.theelement.objects;

public class Song implements Comparable<Song>{
    private String songName;
    private String path;
    private String genre;
    private String authorName;
    private String albumName;
    private double rating;

    public Song(String name, String path){
        this.songName = name;
        this.path = path;
        this.genre = "";
        authorName = "";
        albumName = "";
        rating = 0;
    }

    public Song(String name, String path, String authorName, String albumName, String genre){
        this.songName = name;
        this.path = path;
        this.authorName = authorName;
        this.albumName = albumName;
        this.genre = genre;
        rating = 0;
    }

    public Song(String name, String path, String authorName, String albumName, String genre, double rating) {
        this.songName = name;
        this.path = path;
        this.authorName = authorName;
        this.albumName = albumName;
        this.genre = genre;
        this.rating = rating;
    }
    // getters
    public String getName(){
        return songName;
    }

    public String getPath() {
        return path;
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthorName(){
        return authorName;
    }

    public String getAlbumName(){
        return albumName;
    }

    public double getRating() { return rating; }

    // setters
    public void setName(String newName){
        this.songName = newName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAuthorName(String authorName){
        this.authorName = authorName;
    }

    public void setAlbumName(String albumName){
        this.albumName = albumName;
    }

    public void setRating(double rating){ this.rating = rating; }

    @Override
    public int compareTo(Song song){
        return songName.compareTo(song.getName());
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Song && ((Song)object).getName().equals(this.getName())
                && ((Song)object).getAlbumName().equals(this.getAlbumName()) && ((Song)object).getAuthorName().equals(this.getAuthorName());
    }
}
