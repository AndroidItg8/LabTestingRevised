package itg8.com.labtestingapp.db.tables;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;

@Entity
public class SubCategory  extends BaseObservable {
//    "id": "5",
//            "name": "Concrete ",
//            "parent_id": "1",
//            "proid": "0"

    @PrimaryKey(autoGenerate = false)
    public int id;

    private String name;
    private int maincatid;
    private String image;
    private int proid;

    public SubCategory() {
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

    public int getMaincatid() {
        return maincatid;
    }

    public void setMaincatid(int maincatid) {
        this.maincatid = maincatid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getProid() {
        return proid;
    }

    public void setProid(int proid) {
        this.proid = proid;
    }
}
