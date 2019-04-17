package itg8.com.labtestingapp.db.tables;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Country  {

    @PrimaryKey(autoGenerate = false)
    public int id;

    private String sortname;
    private String name;

    public Country(int id, String sortname, String name) {
        this.id = id;
        this.sortname = sortname;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
