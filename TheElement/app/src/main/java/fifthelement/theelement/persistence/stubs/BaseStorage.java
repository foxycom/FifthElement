package main.java.fifthelement.theelement.persistence.stubs;
/* Stub. acts as a database
 * Handles basic storing
 */

import java.util.ArrayList;
import java.util.List;

import main.java.fifthelement.theelement.objects.MusicItem;

public abstract class BaseStorage<T extends MusicItem> {
    private List<T> storage;

    public BaseStorage() {
        this.storage = new ArrayList<T>();
    }

    public BaseStorage(List<T> dataList) {
        this.storage = dataList;
    }

    public List<T> getAllStoredData() {
        return storage;
    }

    // uses some cool magik to search through the List
    // nvm. Unless we up our support to API 24 then we can't do magik
    public T getByID(String ID) {
        //return storage.stream().filter(data -> ID.equals(data.getID())).findAny().orElse(null);
        T foundData = null;
        for (T data : storage) {
            if (data.getID().equals(ID)) {
                foundData = data;
                break;
            }
        }
        return foundData;
    }

    public boolean store(T data) {
        return storage.add(data);
    }

    // IF data doesn't exist then nothing is done
    // use store() to store data
    public boolean update(T data) {
        int indexOfData = storage.indexOf(getByID(data.getID()));
        if (indexOfData != -1) {
            storage.remove(indexOfData);
            return storage.add(data);
        }
        return false;
    }

    public boolean delete(T data) {
        return storage.remove(data);
    }

}

