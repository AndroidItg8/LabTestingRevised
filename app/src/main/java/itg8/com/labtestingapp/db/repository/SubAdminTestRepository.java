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
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import itg8.com.labtestingapp.db.AppDatabase;
import itg8.com.labtestingapp.db.dbo.SubAdminTestDao;
import itg8.com.labtestingapp.db.tables.SubAdmin;
import itg8.com.labtestingapp.db.tables.SubAdminTest;

public class SubAdminTestRepository extends AndroidViewModel {

    SubAdminTestDao dao;
    private Disposable disposable;

    public SubAdminTestRepository(Application context) {
        super(context);
        AppDatabase database=AppDatabase.getDatabase(context);
        dao=database.getSubAdminTest();
    }

    public LiveData<List<SubAdmin>> getAllSubAdmin(List<Integer> allTests){
        return dao.getAvailableSubAdmins(allTests);
    }

    public void saveStates(List<SubAdminTest> states){
        Observable.just(states).flatMap(new Function<List<SubAdminTest>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(List<SubAdminTest> subAdminTests) throws Exception {
                        dao.clear();

                return Observable.just(0);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        disposable=Observable.fromIterable(clearAndSave(states))
                .flatMap(state -> Observable.just(saveState(state)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {},Throwable::printStackTrace,()->{});
    }
    @NonNull
    private List<SubAdminTest> clearAndSave(List<SubAdminTest> mainCategories) {
        return mainCategories;
    }

    private Integer saveState(SubAdminTest state) {

        return (int)dao.insert(state)[0];
    }


}
