package itg8.com.labtestingapp.db.dbo;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import itg8.com.labtestingapp.db.tables.SubCategory;

@Dao
public interface SubCategoryDao {

    @Query("select * from subcategory where maincatid=:id")
    LiveData<List<SubCategory>> getSubCategory(int id);

    @Insert
    long[] insertCategory(SubCategory... subCategories);

    @Query("delete from subcategory")
    void clear();

    @Query("select * from subcategory where id=:id")
    SubCategory getData(int id);

    @Query("delete from subcategory where id=:id")
    void delete(int id);
}
