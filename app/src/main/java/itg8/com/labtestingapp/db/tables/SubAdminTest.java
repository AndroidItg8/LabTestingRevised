package itg8.com.labtestingapp.db.tables;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity
//        (foreignKeys =
//        {@ForeignKey(entity = SubAdmin.class,parentColumns = "id",childColumns = "sub_admin_id",onDelete = CASCADE),
//        @ForeignKey(entity = Test.class,parentColumns = "id",childColumns = "test_id",onDelete = CASCADE)})
public class SubAdminTest {

    @PrimaryKey(autoGenerate = true)
    public long id;

    private int sub_admin_id;
    private int test_id;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSub_admin_id() {
        return sub_admin_id;
    }

    public void setSub_admin_id(int sub_admin_id) {
        this.sub_admin_id = sub_admin_id;
    }

    public int getTest_id() {
        return test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }
}
