package itg8.com.labtestingapp.db.dbo;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import itg8.com.labtestingapp.db.tables.State;

@Dao
public interface StateDao {

    @Query("select * from state where countryid=:id")
    LiveData<List<State>> getAllState(int id);

    @Insert
    long[] saveState(State... states);

    @Query("delete from state")
    void clear();


    @Query("select * from state where id=:id")
    State getData(int id);

    @Query("delete from state where id=:id")
    void delete(int id);
}
