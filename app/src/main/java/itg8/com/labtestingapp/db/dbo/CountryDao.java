package itg8.com.labtestingapp.db.dbo;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import itg8.com.labtestingapp.db.tables.City;
import itg8.com.labtestingapp.db.tables.Country;

@Dao
public interface CountryDao {
    @Query("select * from country")
    LiveData<List<Country>> getCountries();

    @Insert
    long[] insertCountries(Country... countries);

    @Query("delete from country")
    void clear();

    @Query("select * from country where id=:id")
    Country getData(int id);

    @Query("delete from country where id=:id")
    void delete(int id);
}
