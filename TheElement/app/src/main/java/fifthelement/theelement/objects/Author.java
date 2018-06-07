package main.java.fifthelement.theelement.objects;

public class Author extends MusicItem {
// TODO: implement proper stuff here

    private String id;
    private String name;


    public Author(String id, String name) {

        this.id = id;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String getID() {
        return id;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setID(String newId) {
        this.id  = newId;
    }



}