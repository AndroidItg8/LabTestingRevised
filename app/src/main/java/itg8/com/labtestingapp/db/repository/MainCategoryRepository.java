package itg8.com.labtestingapp.db.repository;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import itg8.com.labtestingapp.common.OnFinishInsertListener;
import itg8.com.labtestingapp.db.AppDatabase;
import itg8.com.labtestingapp.db.dbo.MainCategoryDao;
import itg8.com.labtestingapp.db.tables.MainCategory;

public class MainCategoryRepository extends AndroidViewModel {

    private static final String TAG = "MainCategoryRepository";
    MainCategoryDao dao;
    private LiveData<List<MainCategory>> mainCategories;

    public MainCategoryRepository(Application context) {
        super(context);
        AppDatabase database=AppDatabase.getDatabase(context);
        dao=database.getCategories();
        mainCategories=dao.getCategory();
    }

    public LiveData<List<MainCategory>> getMainCategory(){
        return mainCategories;
    }

    public void insert(List<MainCategory> mainCategories, OnFinishInsertListener onFinishInsertListener){

        Observable.fromIterable(clearAndSave(mainCategories)).flatMap(this::saveCategory)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer->{showLog(integer);},Throwable::printStackTrace,() -> onFinishInsertListener.onFinish());
    }

    private void showLog(Integer integer) {
        Log.d(TAG, "showLog: "+integer);
    }

    @NonNull
    private List<MainCategory> clearAndSave(List<MainCategory> mainCategories) {
        return mainCategories;
    }

    public Observable<Integer> saveCategory(MainCategory category){
        if(dao.getData(category.getId())!=null){
            dao.delete(category.getId());
        }
       return Observable.just((int)dao.saveCategory(category)[0]);
    }
}
