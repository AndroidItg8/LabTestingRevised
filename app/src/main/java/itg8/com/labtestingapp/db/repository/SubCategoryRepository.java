package itg8.com.labtestingapp.db.repository;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import itg8.com.labtestingapp.common.OnFinishInsertListener;
import itg8.com.labtestingapp.db.AppDatabase;
import itg8.com.labtestingapp.db.dbo.SubCategoryDao;
import itg8.com.labtestingapp.db.tables.State;
import itg8.com.labtestingapp.db.tables.SubCategory;

public class SubCategoryRepository extends AndroidViewModel {

    SubCategoryDao dao;

    public SubCategoryRepository(Application context) {
        super(context);
        AppDatabase database=AppDatabase.getDatabase(context);
        dao=database.getSubCategory();
    }


    public LiveData<List<SubCategory>> getSubCategory(int id){
        return dao.getSubCategory(id);
    }

    public void insert(List<SubCategory> list, OnFinishInsertListener onFinishInsertListener){
        Observable.fromIterable(clearAndSave(list)).flatMap(this::saveSubCategory)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {},Throwable::printStackTrace,() -> onFinishInsertListener.onFinish());
    }

    @NonNull
    private List<SubCategory> clearAndSave(List<SubCategory> mainCategories) {
//        dao.clear();
        return mainCategories;
    }

    private Observable<Integer> saveSubCategory(SubCategory subCategory) {
        if(dao.getData(subCategory.getId())!=null){
            dao.delete(subCategory.getId());
        }
      return Observable.just((int)dao.insertCategory(subCategory)[0]);
    }
}
