package itg8.com.labtestingapp.db.dbo;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import itg8.com.labtestingapp.db.tables.SubAdmin;

@Dao
public interface SubAdminDao {

    @Query("select * from subadmin")
    LiveData<List<SubAdmin>> getAllSubAdmin();

    @Insert
    long[] insert(SubAdmin... subAdmins);


    @Query("delete from subadmin")
    void clear();

    @Query("select * from subadmin where id=:id")
    SubAdmin getData(int id);

    @Query("delete from subadmin where id=:id")
    void delete(int id);
}
