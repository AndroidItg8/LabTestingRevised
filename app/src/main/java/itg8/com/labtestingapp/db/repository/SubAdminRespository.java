package itg8.com.labtestingapp.db.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itg8.com.labtestingapp.db.AppDatabase;
import itg8.com.labtestingapp.db.dbo.SubAdminDao;
import itg8.com.labtestingapp.db.tables.SubAdmin;

public class SubAdminRespository {

    SubAdminDao dao;
    LiveData<List<SubAdmin>> allSubAdmin;

    private Disposable disposable;

    public SubAdminRespository(Context context) {
        AppDatabase database=AppDatabase.getDatabase(context);
        dao=database.getSubAdmin();
        allSubAdmin=dao.getAllSubAdmin();
    }

    public LiveData<List<SubAdmin>> getAllSubAdmin() {
        return allSubAdmin;
    }

    public void saveSubAdmin(List<SubAdmin> states){

        disposable=Observable.fromIterable(clearAndSave(states))
                .flatMap(state -> Observable.just(saveState(state)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {},Throwable::printStackTrace,()->{});
    }
    @NonNull
    private List<SubAdmin> clearAndSave(List<SubAdmin> mainCategories) {
//        dao.clear();
        return mainCategories;
    }

    private Integer saveState(SubAdmin state) {
        if(dao.getData(state.getId())!=null){
            dao.delete(state.getId());
        }
        return (int)dao.insert(state)[0];
    }
}
