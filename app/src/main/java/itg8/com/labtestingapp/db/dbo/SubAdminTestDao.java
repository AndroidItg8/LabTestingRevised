package itg8.com.labtestingapp.db.dbo;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import itg8.com.labtestingapp.db.tables.SubAdmin;
import itg8.com.labtestingapp.db.tables.SubAdminTest;

@Dao
public interface SubAdminTestDao {

    @Query("select * from subadmintest")
    LiveData<List<SubAdminTest>> getAllSubAdmin();

    @Insert
    long[] insert(SubAdminTest... subadmintest);


    @Query("delete from subadmintest")
    void clear();

    @Query("select sa.* from subadmin sa inner join test t where t.id in (:ids)")
    LiveData<List<SubAdmin>> getAvailableSubAdmins(List<Integer> ids);

    @Query("select * from subadmintest where id=:id")
    SubAdminTest getData(long id);

    @Query("delete from subadmintest where id=:id")
    void delete(long id);
}
