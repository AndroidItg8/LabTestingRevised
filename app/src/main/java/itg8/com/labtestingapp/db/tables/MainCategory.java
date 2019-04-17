package itg8.com.labtestingapp.db.tables;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import itg8.com.labtestingapp.BR;

@Entity
public class MainCategory extends BaseObservable {
//     "id": "1",
//             "parent_id": null,
//             "name": "M.T",
//             "image": "M.T23729.png",
//             "shortby": "1"

    @PrimaryKey(autoGenerate = false)
    public int id;
    private String name;
    private String image;
    private int shortby;

    public MainCategory() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getShortby() {
        return shortby;
    }

    public void setShortby(int shortby) {
        this.shortby = shortby;
    }
}
