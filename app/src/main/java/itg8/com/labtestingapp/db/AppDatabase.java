package itg8.com.labtestingapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import itg8.com.labtestingapp.common.CommonMethod;
import itg8.com.labtestingapp.db.dbo.CityDao;
import itg8.com.labtestingapp.db.dbo.CountryDao;
import itg8.com.labtestingapp.db.dbo.MainCategoryDao;
import itg8.com.labtestingapp.db.dbo.StateDao;
import itg8.com.labtestingapp.db.dbo.SubAdminDao;
import itg8.com.labtestingapp.db.dbo.SubAdminTestDao;
import itg8.com.labtestingapp.db.dbo.SubCategoryDao;
import itg8.com.labtestingapp.db.dbo.TestDao;
import itg8.com.labtestingapp.db.tables.City;
import itg8.com.labtestingapp.db.tables.Country;
import itg8.com.labtestingapp.db.tables.MainCategory;
import itg8.com.labtestingapp.db.tables.State;
import itg8.com.labtestingapp.db.tables.SubAdmin;
import itg8.com.labtestingapp.db.tables.SubAdminTest;
import itg8.com.labtestingapp.db.tables.SubCategory;
import itg8.com.labtestingapp.db.tables.Test;

@Database(entities = {Country.class,
                                    State.class,
                                    City.class,
                                    MainCategory.class,
                                    SubCategory.class,
                                    Test.class,
                                    SubAdmin.class,
                                    SubAdminTest.class},version = 2,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context){
        if(INSTANCE==null){
            synchronized (AppDatabase.class) {
                INSTANCE = Room
                        .databaseBuilder(context.getApplicationContext(), AppDatabase.class, CommonMethod.APP_DB)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }

        return INSTANCE;
    }

    public abstract CountryDao getCountries();

    public abstract StateDao getStates();

    public abstract CityDao getCities();

    public abstract MainCategoryDao getCategories();

    public abstract SubCategoryDao getSubCategory();

    public abstract TestDao getTest();

    public abstract SubAdminDao getSubAdmin();

    public abstract SubAdminTestDao getSubAdminTest();

}
