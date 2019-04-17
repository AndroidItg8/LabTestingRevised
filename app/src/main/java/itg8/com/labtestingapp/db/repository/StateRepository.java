package itg8.com.labtestingapp.db.repository;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itg8.com.labtestingapp.common.OnFinishInsertListener;
import itg8.com.labtestingapp.db.AppDatabase;
import itg8.com.labtestingapp.db.dbo.StateDao;
import itg8.com.labtestingapp.db.tables.MainCategory;
import itg8.com.labtestingapp.db.tables.State;

public class StateRepository extends AndroidViewModel {

    StateDao dao;
    LiveData<List<State>> allState;
    private Disposable disposable;

    public StateRepository(Application context) {
        super(context);
        AppDatabase database=AppDatabase.getDatabase(context);
        dao=database.getStates();
    }

    public LiveData<List<State>> getAllState(int countyID) {
        allState=dao.getAllState(countyID);
        return allState;
    }

    public void saveStates(List<State> states, OnFinishInsertListener onFinishInsertListener){

        disposable=Observable.fromIterable(clearAndSave(states))
                .flatMap(state -> Observable.just(saveState(state)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {},Throwable::printStackTrace,()->{onFinishInsertListener.onFinish();});
    }
    @NonNull
    private List<State> clearAndSave(List<State> mainCategories) {
//        dao.clear();
        return mainCategories;
    }

    private Integer saveState(State state) {
        if(dao.getData(state.getId())!=null){
            dao.delete(state.getId());
        }
        return (int)dao.saveState(state)[0];
    }
}
