package itg8.com.labtestingapp.db.dbo;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import itg8.com.labtestingapp.db.tables.City;

@Dao
public interface CityDao {

    @Query("select * from city where stateid=:id")
    LiveData<List<City>> getAllCity(int id);

    @Insert
    long[] insertCity(City... cities);

    @Query("delete from city")
    void clear();

    @Query("select * from city where id=:id")
    LiveData<City> getCity(int id);

    @Query("delete from city where id=:id")
    void delete(int id);
}
