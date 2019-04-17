package itg8.com.labtestingapp.db.dbo;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import itg8.com.labtestingapp.db.tables.MainCategory;

@Dao
public interface MainCategoryDao {

    @Query("select * from maincategory")
    LiveData<List<MainCategory>> getCategory();

    @Insert
    long[] saveCategory(MainCategory... mainCategories);

    @Query("delete from maincategory")
    void clear();

    @Query("select * from maincategory where id=:id")
    MainCategory getData(int id);

    @Query("delete from maincategory where id=:id")
    void delete(int id);
}
