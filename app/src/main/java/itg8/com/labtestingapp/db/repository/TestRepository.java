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
import itg8.com.labtestingapp.db.dbo.TestDao;
import itg8.com.labtestingapp.db.tables.Test;

public class TestRepository extends AndroidViewModel {

    TestDao dao;

    public TestRepository(Application context) {
        super(context);
        AppDatabase database=AppDatabase.getDatabase(context);
        dao=database.getTest();
    }

    public LiveData<List<Test>> getTest(int id){
        return dao.getTest(id);
    }

    public void insert(List<Test> tests, OnFinishInsertListener onFinishInsertListener){
        Observable
                .fromIterable(clearAndSave(tests))
                .flatMap(this::saveTest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {},Throwable::printStackTrace,() -> onFinishInsertListener.onFinish());
    }

    @NonNull
    private List<Test> clearAndSave(List<Test> mainCategories) {
//        dao.clear();
        return mainCategories;
    }

    private Observable<Integer> saveTest(Test test) {
        if(dao.getData(test.getId())!=null){
            dao.delete(test.getId());
        }
        dao.insert(test);
        return Observable.just(1);
    }
}
