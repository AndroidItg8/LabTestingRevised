package itg8.com.labtestingapp.db.dbo;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import itg8.com.labtestingapp.db.tables.Test;

@Dao
public interface TestDao {

    @Query("select * from test where categoryid=:id")
    LiveData<List<Test>> getTest(int id);

    @Insert
    void insert(Test... tests);

    @Query("delete from test")
    void clear();

    @Query("select * from test where id=:id")
    Test getData(int id);

    @Query("delete from test where id=:id")
    void delete(int id);
}
